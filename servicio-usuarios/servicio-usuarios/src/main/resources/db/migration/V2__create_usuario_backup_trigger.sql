CREATE TABLE IF NOT EXISTS usuario_backup (
    id BIGINT,
    username VARCHAR(100),
    nombre VARCHAR(100),
    email VARCHAR(150),
    fecha_backup TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TRIGGER IF EXISTS despues_insert_usuario;

CREATE TRIGGER despues_insert_usuario
AFTER INSERT ON usuario
FOR EACH ROW
INSERT INTO usuario_backup (
    id,
    username,
    nombre,
    email
)
VALUES (
    NEW.id,
    NEW.username,
    NEW.nombre,
    NEW.email
);