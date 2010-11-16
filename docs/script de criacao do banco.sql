SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE  TABLE IF NOT EXISTS `futurebeer`.`mesa` (
  `mesa_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `numero` INT(11) NOT NULL ,
  `status` INT(11) NOT NULL ,
  PRIMARY KEY (`mesa_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE  TABLE IF NOT EXISTS `futurebeer`.`mesa_ocupacao` (
  `mesa_ocupacao_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `abertura` TIMESTAMP NULL DEFAULT NULL ,
  `fechamento` TIMESTAMP NULL DEFAULT NULL ,
  `total` DECIMAL NULL DEFAULT NULL ,
  `mesa_id` INT(11) NOT NULL ,
  PRIMARY KEY (`mesa_ocupacao_id`) ,
  INDEX `mesa_ocupacao_mesa_fk` (`mesa_id` ASC) ,
  CONSTRAINT `mesa_ocupacao_mesa_fk`
    FOREIGN KEY (`mesa_id` )
    REFERENCES `futurebeer`.`mesa` (`mesa_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE  TABLE IF NOT EXISTS `futurebeer`.`pedido` (
  `pedido_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `mesa_ocupacao_id` INT(11) NOT NULL ,
  PRIMARY KEY (`pedido_id`) ,
  INDEX `pedido_mesa_ocupacao_fk` (`mesa_ocupacao_id` ASC) ,
  CONSTRAINT `pedido_mesa_ocupacao_fk`
    FOREIGN KEY (`mesa_ocupacao_id` )
    REFERENCES `futurebeer`.`mesa_ocupacao` (`mesa_ocupacao_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE  TABLE IF NOT EXISTS `futurebeer`.`item_pedido` (
  `item_pedido_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `qtdade` INT(11) NOT NULL ,
  `pedido_id` INT(11) NOT NULL ,
  `produto_id` INT(11) NOT NULL ,
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
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;

CREATE  TABLE IF NOT EXISTS `futurebeer`.`produto` (
  `produto_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `descricao` VARCHAR(30) NOT NULL ,
  `tipo` INT(11) NOT NULL ,
  `valor` DECIMAL NOT NULL ,
  PRIMARY KEY (`produto_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_swedish_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
