-- proyecto2_cheatsheet.sql
-- Comandos útiles para el esquema `proyecto2`
-- Copia y pega lo que necesites. Ajusta nombres si cambias el esquema/tablas.
-- -----------------------------------------------------------------------------
-- CONTEXTO
-- -----------------------------------------------------------------------------
-- Establecer esquema activo (cámbialo si usas otro nombre)
USE `proyecto2`;

-- Ver esquema activo
SELECT DATABASE();

-- Listar bases de datos y tablas
SHOW DATABASES;
SHOW TABLES;

-- Ver estructura de una tabla
DESCRIBE `Paciente`;
SHOW CREATE TABLE `Receta`\G

-- -----------------------------------------------------------------------------
-- CREAR / SELECCIONAR BASES DE DATOS
-- -----------------------------------------------------------------------------
-- Crear base de datos con charset/collation
CREATE DATABASE IF NOT EXISTS `proyecto2`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

-- Elegir base de datos
USE `proyecto2`;

-- -----------------------------------------------------------------------------
-- BORRAR BASE DE DATOS
-- -----------------------------------------------------------------------------
-- Salte del esquema antes de borrarlo (opcional)
USE `mysql`;

-- Eliminar toda la base de datos (¡irreversible!)
DROP DATABASE IF EXISTS `proyecto2`;

-- -----------------------------------------------------------------------------
-- TABLAS: BORRAR, VACIAR, ELIMINAR FILAS
-- -----------------------------------------------------------------------------
USE `proyecto2`;

-- 1) Borrar UNA tabla (estructura + datos)
DROP TABLE IF EXISTS `Prescripcion`;

-- 2) Vaciar TODAS las filas de una tabla (mantiene estructura)
TRUNCATE TABLE `Prescripcion`;   -- reinicia AUTO_INCREMENT y hace commit implícito

-- 3) Eliminar filas por condición
DELETE FROM `Receta` WHERE `estado` = 'Anulada';

-- 4) Eliminar TODAS las filas (cuidado)
DELETE FROM `Receta`;

-- 5) Eliminar con JOIN (ej.: borrar recetas de un paciente)
DELETE r
FROM `Receta` r
JOIN `Paciente` p ON p.id = r.paciente
WHERE p.id = 'P001';

-- 6) Operaciones grandes con transacción (puedes revertir con ROLLBACK)
START TRANSACTION;
  DELETE FROM `Receta` WHERE `estado` = 'Anulada';
COMMIT;
-- ROLLBACK;  -- úsalo si aún no hiciste COMMIT

-- 7) SQL_SAFE_UPDATES (útil en Workbench cuando impide DELETE/UPDATE)
SET SQL_SAFE_UPDATES = 0;  -- desactiva protección (úsa con cuidado)
SET SQL_SAFE_UPDATES = 1;  -- vuelve a activar

-- -----------------------------------------------------------------------------
-- FOREIGN KEYS Y ÓRDENES DE BORRADO
-- -----------------------------------------------------------------------------
-- A veces conviene desactivar checks (en importaciones/recreaciones masivas)
SET FOREIGN_KEY_CHECKS = 0;
-- ... operaciones de borrado/creación ...
SET FOREIGN_KEY_CHECKS = 1;

-- Borrado ordenado manual (hijas -> padres), según tu esquema:
-- Hijas: Prescripcion -> Receta -> (Medico, Paciente) -> Usuario (para médicos)
DELETE FROM `Prescripcion`;
DELETE FROM `Receta`;
-- Si vas a borrar pacientes/médicos, primero asegúrate de que no haya recetas que los apunten.
DELETE FROM `Paciente` WHERE id='P001';
DELETE FROM `Medico`   WHERE id='M001';
-- `Medico`.`id` referencia a `Usuario`.`id`, por lo que puedes borrar primero en Receta,
-- luego en Medico, y finalmente en Usuario:
DELETE FROM `Usuario`  WHERE id='M001';

-- -----------------------------------------------------------------------------
-- INSERCIÓN RÁPIDA DE DATOS DE PRUEBA
-- -----------------------------------------------------------------------------
INSERT INTO `Usuario` (`id`, `clave`) VALUES ('M001','claveSegura1'), ('M002','claveSegura2');

INSERT INTO `Medico` (`nombre`, `especialidad`, `id`) VALUES
  ('Dra. María Solano', 'Pediatría',        'M001'),
  ('Dr. Javier Rojas',  'Medicina General', 'M002');

INSERT INTO `Paciente` (`id`, `nombre`, `telefono`) VALUES
  ('P001','Ana López','8888-0001'),
  ('P002','Carlos Méndez','8888-0002');

INSERT INTO `Medicamento` (`codigo`, `nombre`, `presentacion`) VALUES
  ('MED001','Ibuprofeno','Tabletas 200 mg'),
  ('MED002','Amoxicilina','Cápsulas 500 mg');

-- Crear una receta y una prescripción asociada
INSERT INTO `Receta` (`fechaDeRetiro`, `estado`, `paciente`, `medico`)
VALUES (DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'Confeccionada', 'P001', 'M001');
SET @receta_id = LAST_INSERT_ID();

INSERT INTO `Prescripcion` (`indicaciones`,`duracion`,`cantidad`,`medicamento`,`receta`)
VALUES ('1 tableta cada 8 horas', 5, 15, 'MED001', @receta_id);

-- -----------------------------------------------------------------------------
-- CONSULTAS FRECUENTES
-- -----------------------------------------------------------------------------
-- Listar recetas con paciente y médico
SELECT r.id, r.estado, r.fechaDeRetiro, p.nombre AS paciente, m.nombre AS medico
FROM `Receta` r
JOIN `Paciente` p ON p.id = r.paciente
JOIN `Medico`   m ON m.id = r.medico
ORDER BY r.id DESC;

-- Prescripciones con medicamento y receta
SELECT pr.id, pr.indicaciones, pr.duracion, pr.cantidad,
       md.nombre AS medicamento, r.id AS receta_id
FROM `Prescripcion` pr
JOIN `Medicamento` md ON md.codigo = pr.medicamento
JOIN `Receta`     r  ON r.id = pr.receta
ORDER BY pr.id DESC;

-- Conteos rápidos
SELECT 'Usuario' AS tabla, COUNT(*) AS filas FROM `Usuario`
UNION ALL SELECT 'Medico', COUNT(*) FROM `Medico`
UNION ALL SELECT 'Paciente', COUNT(*) FROM `Paciente`
UNION ALL SELECT 'Medicamento', COUNT(*) FROM `Medicamento`
UNION ALL SELECT 'Receta', COUNT(*) FROM `Receta`
UNION ALL SELECT 'Prescripcion', COUNT(*) FROM `Prescripcion`;

-- -----------------------------------------------------------------------------
-- ALTERACIONES COMUNES
-- -----------------------------------------------------------------------------
-- Añadir columna
ALTER TABLE `Paciente` ADD COLUMN `email` VARCHAR(100) NULL AFTER `telefono`;

-- Quitar columna
ALTER TABLE `Paciente` DROP COLUMN `email`;

-- Cambiar tipo/tamaño
ALTER TABLE `Medicamento` MODIFY `presentacion` VARCHAR(80) NULL;

-- Añadir índice
CREATE INDEX `idx_paciente_nombre` ON `Paciente` (`nombre`);

-- Borrar índice
DROP INDEX `idx_paciente_nombre` ON `Paciente`;

-- Añadir FK (ejemplo didáctico)
-- ALTER TABLE `Receta`
--   ADD CONSTRAINT `fk_Receta_Paciente1`
--   FOREIGN KEY (`paciente`) REFERENCES `Paciente`(`id`)
--   ON DELETE NO ACTION ON UPDATE NO ACTION;

-- -----------------------------------------------------------------------------
-- IMPORTAR / EJECUTAR ARCHIVOS
-- -----------------------------------------------------------------------------
-- Ejecutar otro archivo .sql desde el cliente MySQL:
-- SOURCE C:/ruta/a/tu_archivo.sql;

-- -----------------------------------------------------------------------------
-- BACKUP (NOTA: se ejecuta en la terminal del SO, no dentro de MySQL)
-- -----------------------------------------------------------------------------
-- mysqldump -u TU_USUARIO -p --databases proyecto2 > C:\ruta\proyecto2_backup.sql
-- Restaurar:
-- mysql -u TU_USUARIO -p < C:\ruta\proyecto2_backup.sql

-- Fin del archivo.
