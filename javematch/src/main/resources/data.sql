-- Insertar algunos planes de ejemplo
INSERT INTO plan (plan_id, nombre) VALUES (1, 'Bronze');
INSERT INTO plan (plan_id, nombre) VALUES (2, 'Silver');
INSERT INTO plan (plan_id, nombre) VALUES (3, 'Gold');

-- Insertar algunos intereses de ejemplo
INSERT INTO interes (interes_id, nombre) VALUES (1, 'Deportes');
INSERT INTO interes (interes_id, nombre) VALUES (2, 'Música');
INSERT INTO interes (interes_id, nombre) VALUES (3, 'Videojuegos');
INSERT INTO interes (interes_id, nombre) VALUES (4, 'Baile');
INSERT INTO interes (interes_id, nombre) VALUES (5, 'Lectura');

--Insertar algunos usuarios de ejemplo
-- Inserciones en la tabla usuario
INSERT INTO usuario (user_id, nombre, correo, plan_id) VALUES (1, 'Juan Perez', 'juan.perez@javeriana.edu.co', 1);
INSERT INTO usuario (user_id, nombre, correo, plan_id) VALUES (2, 'Maria Gomez', 'maria.gomez@javeriana.edu.co', 2);
INSERT INTO usuario (user_id, nombre, correo, plan_id) VALUES (3, 'Carlos Ruiz', 'carlos.ruiz@javeriana.edu.co', 3);
INSERT INTO usuario (user_id, nombre, correo, plan_id) VALUES (4, 'Ana Martinez', 'ana.martinez@javeriana.edu.co', 1);
INSERT INTO usuario (user_id, nombre, correo, plan_id) VALUES (5, 'Pedro Sanchez', 'pedro.sanchez@javeriana.edu.co', 2);

-- Relacionar usuarios con intereses

-- Juan Perez tiene intereses en Deportes y Música
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (1, 1);  -- Deportes
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (1, 2);  -- Música

-- Maria Gomez tiene intereses en Deportes, Música y Videojuegos
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (2, 1);  -- Deportes
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (2, 2);  -- Música
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (2, 3);  -- Videojuegos

-- Carlos Ruiz tiene interés en Baile y Lectura
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (3, 4);  -- Baile
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (3, 5);  -- Lectura

-- Ana Martinez tiene intereses en Música y Videojuegos
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (4, 2);  -- Música
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (4, 3);  -- Videojuegos

-- Pedro Sanchez tiene intereses en Deportes y Lectura
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (5, 1);  -- Deportes
INSERT INTO usuario_interes (usuario_id, interes_id) VALUES (5, 5);  -- Lectura

INSERT INTO user_match (user1_user_id, user2_user_id, fecha_match) 
VALUES (1, 2, CURRENT_DATE);  -- Relacionar los usuarios con la fecha del matc

-- Insertar una videollamada asociada con el match
INSERT INTO videollamada (fecha_videollamada, estado, user_match_id) 
VALUES (CURRENT_TIMESTAMP, 'Iniciada', 1);  -- Asociar la videollamada con el match creado (match_id = 1)
