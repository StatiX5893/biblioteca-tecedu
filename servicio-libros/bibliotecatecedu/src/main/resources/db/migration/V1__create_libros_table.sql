CREATE TABLE IF NOT EXISTS libros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(100),
    titulo VARCHAR(150),
    editorial VARCHAR(150),
    fecha_publicacion INT NOT NULL,
    autor VARCHAR(150)
);