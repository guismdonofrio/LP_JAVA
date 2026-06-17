CREATE DATABASE IF NOT EXISTS projetinho;

USE projetinho;

CREATE TABLE IF NOT EXISTS barraca (
    id       INT          NOT NULL AUTO_INCREMENT,
    nome     VARCHAR(100) NOT NULL,
    produto  VARCHAR(100) NOT NULL,
    estoque  INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS produto_barraca (
    id         INT            NOT NULL AUTO_INCREMENT,
    id_barraca INT            NOT NULL,
    nome       VARCHAR(100)   NOT NULL,
    preco      DECIMAL(10,2)  NOT NULL DEFAULT 0.00,
    estoque    INT            NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (id_barraca) REFERENCES barraca(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS venda_barraca (
    id          INT            NOT NULL AUTO_INCREMENT,
    id_barraca  INT            NOT NULL,
    produto     VARCHAR(100)   NOT NULL,
    quantidade  INT            NOT NULL,
    valor_total DECIMAL(10,2)  NOT NULL,
    data        DATE           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_barraca) REFERENCES barraca(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS farmacia (
    id       INT          NOT NULL AUTO_INCREMENT,
    nome     VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    estoque  INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS medicamento (
    id          INT            NOT NULL AUTO_INCREMENT,
    id_farmacia INT            NOT NULL,
    nome        VARCHAR(150)   NOT NULL,
    fabricante  VARCHAR(100)   NOT NULL,
    dosagem     VARCHAR(50)    NOT NULL,
    estoque     INT            NOT NULL DEFAULT 0,
    preco       DECIMAL(10,2)  NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id),
    FOREIGN KEY (id_farmacia) REFERENCES farmacia(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS venda_farmacia (
    id          INT            NOT NULL AUTO_INCREMENT,
    id_farmacia INT            NOT NULL,
    medicamento VARCHAR(150)   NOT NULL,
    quantidade  INT            NOT NULL,
    valor_total DECIMAL(10,2)  NOT NULL,
    data        DATE           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_farmacia) REFERENCES farmacia(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS salao (
    id           INT          NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(100) NOT NULL,
    funcionarios INT          NOT NULL DEFAULT 0,
    servico      VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS funcionario (
    id        INT            NOT NULL AUTO_INCREMENT,
    id_salao  INT            NOT NULL,
    nome      VARCHAR(100)   NOT NULL,
    cargo     VARCHAR(100)   NOT NULL,
    salario   DECIMAL(10,2)  NOT NULL DEFAULT 0.00,
    telefone  VARCHAR(20)    NOT NULL DEFAULT '',
    PRIMARY KEY (id),
    FOREIGN KEY (id_salao) REFERENCES salao(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS agendamento (
    id           INT          NOT NULL AUTO_INCREMENT,
    id_salao     INT          NOT NULL,
    nome_cliente VARCHAR(100) NOT NULL,
    servico      VARCHAR(100) NOT NULL,
    data         DATE         NOT NULL,
    horario      VARCHAR(5)   NOT NULL,   -- ex: "14:30"
    status       VARCHAR(20)  NOT NULL DEFAULT 'Agendado',  -- Agendado | Concluído | Cancelado
    PRIMARY KEY (id),
    FOREIGN KEY (id_salao) REFERENCES salao(id) ON DELETE CASCADE
);
