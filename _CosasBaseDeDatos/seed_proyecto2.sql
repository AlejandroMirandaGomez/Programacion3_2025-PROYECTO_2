-- proyecto2_seed_minimal.sql
-- Inserta 2 filas por cada tabla del esquema `Proyecto2`.
-- Nota: reutilizamos los mismos 2 usuarios como Médicos, Farmaceutas y Administradores
-- para cumplir "2 datos por tabla" sin añadir usuarios extra.
-- Ajusta los valores si ya existen filas con los mismos IDs.

USE `Proyecto2`;

START TRANSACTION;

-- 1) USUARIO (2)
INSERT INTO `Usuario` (`id`, `clave`, `rol`) VALUES
  ('U1', 'claveU1', NULL),
  ('U2', 'claveU2', NULL)
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- 2) MEDICO (2)  -> requiere Usuario.id
INSERT INTO `Medico` (`nombre`, `especialidad`, `id`) VALUES
  ('Dra. Laura Gómez', 'Medicina General', 'U1'),
  ('Dr. Andrés Vega',  'Pediatría',        'U2')
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- 3) PACIENTE (2)
INSERT INTO `Paciente` (`id`, `nombre`, `telefono`, `fechaNacimiento`) VALUES
  ('P001', 'Ana López',    '8888-0001', '1992-03-14'),
  ('P002', 'Carlos Méndez','8888-0002', '1989-07-22')
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- 4) MEDICAMENTO (2)
INSERT INTO `Medicamento` (`codigo`, `nombre`, `presentacion`) VALUES
  ('MED001', 'Ibuprofeno',  'Tabletas 200 mg'),
  ('MED002', 'Amoxicilina', 'Cápsulas 500 mg')
ON DUPLICATE KEY UPDATE codigo = VALUES(codigo);

-- 5) RECETA (2)  -> requiere Paciente.id y Medico.id
INSERT INTO `Receta` (`fechaDeRetiro`, `estado`, `paciente`, `medico`)
VALUES (DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'Lista', 'P001', 'U1');
SET @r1 = LAST_INSERT_ID();

INSERT INTO `Receta` (`fechaDeRetiro`, `estado`, `paciente`, `medico`)
VALUES (DATE_ADD(CURDATE(), INTERVAL 5 DAY), 'Lista', 'P002', 'U2');
SET @r2 = LAST_INSERT_ID();

-- 6) PRESCRIPCION (2) -> requiere Receta.id y Medicamento.codigo
-- OJO: `Prescripcion`.`id` NO es AUTO_INCREMENT, así que asignamos IDs únicos manualmente.
INSERT INTO `Prescripcion` (`id`, `indicaciones`, `duracion`, `cantidad`, `receta`, `medicamento`) VALUES
  (1001, '1 tableta cada 8 horas después de comidas', 5, 15, @r1, 'MED001'),
  (1002, '1 cápsula cada 12 horas',                    7, 14, @r2, 'MED002')
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- 7) FARMACEUTA (2) -> requiere Usuario.id
INSERT INTO `Farmaceuta` (`nombre`, `id`) VALUES
  ('María Soto', 'U1'),
  ('Pedro Ruiz', 'U2')
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- 8) ADMINISTRADOR (2) -> requiere Usuario.id
INSERT INTO `Administrador` (`id`) VALUES
  ('U1'),
  ('U2')
ON DUPLICATE KEY UPDATE id = VALUES(id);

COMMIT;

-- Verificación rápida (opcional)
SELECT 'Usuario' AS tabla, COUNT(*) AS filas FROM `Usuario`
UNION ALL SELECT 'Medico', COUNT(*) FROM `Medico`
UNION ALL SELECT 'Paciente', COUNT(*) FROM `Paciente`
UNION ALL SELECT 'Medicamento', COUNT(*) FROM `Medicamento`
UNION ALL SELECT 'Receta', COUNT(*) FROM `Receta`
UNION ALL SELECT 'Prescripcion', COUNT(*) FROM `Prescripcion`
UNION ALL SELECT 'Farmaceuta', COUNT(*) FROM `Farmaceuta`
UNION ALL SELECT 'Administrador', COUNT(*) FROM `Administrador`;
