CREATE TABLE IF NOT EXISTS prestamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_libro BIGINT NOT NULL,
    run_solicitante VARCHAR(50),
    fecha_solicitud DATE,
    fecha_entrega DATE,
    cantidad_dias INT,
    multas INT
);