CREATE DATABASE destino_certo;

USE destino_certo;

CREATE TABLE IF NOT EXISTS `destino_certo`.`cliente` (
  `id_cliente` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(200) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `telefone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_cliente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `destino_certo`.`reserva` (
  `id_reserva` INT NOT NULL AUTO_INCREMENT,
  `origem` VARCHAR(100) NOT NULL,
  `destino` VARCHAR(100) NOT NULL,
  `data_partida` DATE NOT NULL,
  `data_chegada` DATE NOT NULL,
  `id_cliente` INT NOT NULL,
  PRIMARY KEY (`id_reserva`),
    FOREIGN KEY (`id_cliente`)
    REFERENCES `destino_certo`.`cliente` (`id_cliente`)
);

CREATE TABLE IF NOT EXISTS `destino_certo`.`contato` (
  `id_contato` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `mensagem` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id_contato`)
);


CREATE TABLE IF NOT EXISTS `destino_certo`.`passagem` (
  `id_passagem` INT NOT NULL AUTO_INCREMENT,
  `preco` DOUBLE NOT NULL,
  `id_reserva` INT NOT NULL,
  PRIMARY KEY (`id_passagem`),
    FOREIGN KEY (`id_reserva`)
    REFERENCES `destino_certo`.`reserva` (`id_reserva`)
);

ALTER TABLE passagem
ADD COLUMN data_emissao DATE,
ADD COLUMN assento INT;

ALTER TABLE cliente
ADD CONSTRAINT email_unico UNIQUE (email);

INSERT INTO cliente(nome, email, senha, telefone) VALUES ('Antonela Messi', 'anto.com', '1234', '9 7854-2578');

SELECT * FROM passagem WHERE id_reserva IN (SELECT id_reserva FROM reserva WHERE id_cliente = 3);

SELECT p.*, r.data_partida, r.data_chegada, c.nome AS nome_cliente
FROM passagem p
INNER JOIN reserva r ON p.id_reserva = r.id_reserva
INNER JOIN cliente c ON r.id_cliente = c.id_cliente;

UPDATE cliente SET nome = "Lionel Andres Messi", email = "messi.barca", senha = "1030", telefone = "(55) 98795-4785" WHERE id_cliente = 4;

DELETE FROM cliente WHERE id_cliente = 1;