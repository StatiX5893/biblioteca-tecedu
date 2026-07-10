CREATE TABLE IF NOT EXISTS prestamos_backup (
    id BIGINT,
    id_libro BIGINT,
    run_solicitante VARCHAR(50),
    fecha_solicitud DATE,
    fecha_entrega DATE,
    cantidad_dias INT,
    multas INT,
    fecha_backup TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TRIGGER IF EXISTS despues_insert_prestamo;

CREATE TRIGGER despues_insert_prestamo
AFTER INSERT ON prestamos
FOR EACH ROW
INSERT INTO prestamos_backup (
    id,
    id_libro,
    run_solicitante,
    fecha_solicitud,
    fecha_entrega,
    cantidad_dias,
    multas
)
VALUES (
    NEW.id,
    NEW.id_libro,
    NEW.run_solicitante,
    NEW.fecha_solicitud,
    NEW.fecha_entrega,
    NEW.cantidad_dias,
    NEW.multas
);