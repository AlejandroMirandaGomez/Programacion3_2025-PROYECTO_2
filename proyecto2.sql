-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Proyecto2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Proyecto2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Proyecto2` DEFAULT CHARACTER SET utf8 ;
USE `Proyecto2` ;

-- -----------------------------------------------------
-- Table `Proyecto2`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Proyecto2`.`Usuario` (
  `id` VARCHAR(45) NOT NULL,
  `clave` VARCHAR(45) NULL,
  `rol` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Proyecto2`.`Medico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Proyecto2`.`Medico` (
  `nombre` VARCHAR(45) NULL,
  `especialidad` VARCHAR(45) NULL,
  `id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Medico_Usuario1`
    FOREIGN KEY (`id`)
    REFERENCES `Proyecto2`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Proyecto2`.`Paciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Proyecto2`.`Paciente` (
  `id` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `telefono` VARCHAR(45) NULL,
  `fechaNacimiento` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Proyecto2`.`Receta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Proyecto2`.`Receta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fechaDeRetiro` DATE NULL,
  `estado` VARCHAR(20) NULL,
  `paciente` VARCHAR(45) NOT NULL,
  `medico` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Receta_Paciente1_idx` (`paciente` ASC) VISIBLE,
  INDEX `fk_Receta_Medico1_idx` (`medico` ASC) VISIBLE,
  CONSTRAINT `fk_Receta_Paciente1`
    FOREIGN KEY (`paciente`)
    REFERENCES `Proyecto2`.`Paciente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Receta_Medico1`
    FOREIGN KEY (`medico`)
    REFERENCES `Proyecto2`.`Medico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Proyecto2`.`Medicamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Proyecto2`.`Medicamento` (
  `codigo` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `presentacion` VARCHAR(45) NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Proyecto2`.`Prescripcion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Proyecto2`.`Prescripcion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `indicaciones` VARCHAR(256) NULL,
  `duracion` INT NULL,
  `cantidad` INT NULL,
  `receta` INT NOT NULL,
  `medicamento` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Preescripciones_Receta1_idx` (`receta` ASC) VISIBLE,
  INDEX `fk_Prescripcion_Medicamento1_idx` (`medicamento` ASC) VISIBLE,
  CONSTRAINT `fk_Preescripciones_Receta1`
    FOREIGN KEY (`receta`)
    REFERENCES `Proyecto2`.`Receta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescripcion_Medicamento1`
    FOREIGN KEY (`medicamento`)
    REFERENCES `Proyecto2`.`Medicamento` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Proyecto2`.`Farmaceuta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Proyecto2`.`Farmaceuta` (
  `nombre` VARCHAR(45) NULL,
  `id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Medico_Usuario10`
    FOREIGN KEY (`id`)
    REFERENCES `Proyecto2`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Proyecto2`.`Administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Proyecto2`.`Administrador` (
  `id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Medico_Usuario100`
    FOREIGN KEY (`id`)
    REFERENCES `Proyecto2`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

use database Proyecto2;

insert into usuario
(id, clave, rol)
values(1,1,'ADM');

insert into usuario
(id, clave, rol)
values(2,2,'MED');

insert into Medico
(id, nombre, especialidad)
values("2", "Keylor Medico", "Psicologia");