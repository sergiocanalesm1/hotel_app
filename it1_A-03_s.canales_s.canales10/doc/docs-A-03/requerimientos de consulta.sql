--RFC1
select numerohabitacionacargar, sum(valor)
from consumo
where fecharegistro >= ? AND fechaRegistro <= ?
group by numerohabitacionacargar;
--RFC2
select *
from (
    SELECT ser.id, ser.nombre, count(*)
    FROM consumo cons, servicio ser
    WHERE cons.idserviciocargado = ser.id
    Group by ser.id, ser.nombre
    ORDER BY count(*) desc
    )
where rownum <= 10;


--RFC3
SELECT usu.numerohabitacion, count(*) / (
    SELECT count(*)
    FROM usuario usu, reserva res
    WHERE usu.numerodocumento = res.idusuario AND res.tipohabitacion is not null
    ) 
FROM usuario usu, reserva res
WHERE usu.numerodocumento = res.idusuario AND res.tipohabitacion is not null
GROUP BY usu.numerohabitacion;

--RFC4
--precio en cierto rango
SELECT *
FROM servicios
WHERE costo >= ? AND costo <= ?;
--fecha de consumo en un rango de tiempo
SELECT *
FROM servicios ser, consumo cons
WHERE cons.idserviciocargado = ser.id AND cons.fecharegistro >= ? AND cons.fecharegistro <= ?;
--registros consumidos mas de x veces en un rango de fechas
SELECT ser.id,ser.nombre,count(*)
FROM servicios ser, consumo cons
WHERE cons.idserviciocargado = ser.id AND cons.fecharegistro >= ? AND cons.fecharegistro <= ? AND count(*) > ?
GROUP BY ser.id, ser.nombre;


--RFC5
SELECT us.nombre, sum(valor)
FROM consumo cons, usuario us
WHERE us.numerodocumento = ? AND cons.fecharegistro >= us.fechallegada AND cons.fecharegistro <= fechasalida AND cons.fecharegistro >= ? AND cons.fecharegistro <= ?;


