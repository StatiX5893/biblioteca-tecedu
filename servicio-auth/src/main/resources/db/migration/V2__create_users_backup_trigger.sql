CREATE TABLE IF NOT EXISTS users_backup (
    id BIGINT,
    username VARCHAR(100),
    password VARCHAR(255),
    role VARCHAR(50),
    fecha_backup TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TRIGGER IF EXISTS despues_insert_user;

CREATE TRIGGER despues_insert_user
AFTER INSERT ON users
FOR EACH ROW
INSERT INTO users_backup (
    id,
    username,
    password,
    role
)
VALUES (
    NEW.id,
    NEW.username,
    NEW.password,
    NEW.role
);