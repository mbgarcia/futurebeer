SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `futurebeer` DEFAULT CHARACTER SET latin1 ;
USE `futurebeer` ;

-- -----------------------------------------------------
-- Table `futurebeer`.`mesa`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `futurebeer`.`mesa` (
  `mesa_id` INT NOT NULL AUTO_INCREMENT ,
  `numero` VARCHAR(5) NOT NULL ,
  `status` INT NOT NULL ,
  `extra` INT(1) NOT NULL ,
  `ativa` INT(1) NOT NULL ,
  PRIMARY KEY (`mesa_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurebeer`.`mesa_ocupacao`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `futurebeer`.`mesa_ocupacao` (
  `mesa_ocupacao_id` INT NOT NULL AUTO_INCREMENT ,
  `abertura` TIMESTAMP NULL ,
  `fechamento` TIMESTAMP NULL ,
  `total` DECIMAL NULL ,
  `mesa_id` INT NOT NULL ,
  PRIMARY KEY (`mesa_ocupacao_id`) ,
  INDEX `mesa_ocupacao_mesa_fk` (`mesa_id` ASC) ,
  CONSTRAINT `mesa_ocupacao_mesa_fk`
    FOREIGN KEY (`mesa_id` )
    REFERENCES `futurebeer`.`mesa` (`mesa_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurebeer`.`pedido`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `futurebeer`.`pedido` (
  `pedido_id` INT NOT NULL AUTO_INCREMENT ,
  `mesa_ocupacao_id` INT NOT NULL ,
  PRIMARY KEY (`pedido_id`) ,
  INDEX `pedido_mesa_ocupacao_fk` (`mesa_ocupacao_id` ASC) ,
  CONSTRAINT `pedido_mesa_ocupacao_fk`
    FOREIGN KEY (`mesa_ocupacao_id` )
    REFERENCES `futurebeer`.`mesa_ocupacao` (`mesa_ocupacao_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurebeer`.`produto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `futurebeer`.`produto` (
  `produto_id` INT NOT NULL AUTO_INCREMENT ,
  `descricao` VARCHAR(30) NOT NULL ,
  `tipo` INT NOT NULL ,
  `valor` DECIMAL(5,2) NOT NULL ,
  `ativo` INT NOT NULL ,
  PRIMARY KEY (`produto_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `futurebeer`.`item_pedido`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `futurebeer`.`item_pedido` (
  `item_pedido_id` INT NOT NULL AUTO_INCREMENT ,
  `qtdade` INT NOT NULL ,
  `pedido_id` INT NOT NULL ,
  `produto_id` INT NOT NULL ,
  `excluido` INT(1) NULL ,
  PRIMARY KEY (`item_pedido_id`) ,
  INDEX `item_pedido_pedido_fk` (`pedido_id` ASC) ,
  INDEX `item_pedido_produto_fk` (`produto_id` ASC) ,
  CONSTRAINT `item_pedido_pedido_fk`
    FOREIGN KEY (`pedido_id` )
    REFERENCES `futurebeer`.`pedido` (`pedido_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `item_pedido_produto_fk`
    FOREIGN KEY (`produto_id` )
    REFERENCES `futurebeer`.`produto` (`produto_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
