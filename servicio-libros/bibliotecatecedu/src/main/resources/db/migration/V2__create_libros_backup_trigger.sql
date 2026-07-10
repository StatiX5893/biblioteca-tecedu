CREATE TABLE IF NOT EXISTS libros_backup (
    id BIGINT,
    isbn VARCHAR(100),
    titulo VARCHAR(150),
    editorial VARCHAR(150),
    fecha_publicacion INT,
    autor VARCHAR(150),
    fecha_backup TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TRIGGER IF EXISTS despues_insert_libro;

CREATE TRIGGER despues_insert_libro
AFTER INSERT ON libros
FOR EACH ROW
INSERT INTO libros_backup (
    id,
    isbn,
    titulo,
    editorial,
    fecha_publicacion,
    autor
)
VALUES (
    NEW.id,
    NEW.isbn,
    NEW.titulo,
    NEW.editorial,
    NEW.fecha_publicacion,
    NEW.autor
);