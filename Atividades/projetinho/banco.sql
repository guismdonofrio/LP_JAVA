CREATE DATABASE IF NOT EXISTS projetinho;

USE projetinho;

CREATE TABLE IF NOT EXISTS barraca (
    id       INT          NOT NULL AUTO_INCREMENT,
    nome     VARCHAR(100) NOT NULL,
    produto  VARCHAR(100) NOT NULL,
    estoque  INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS farmacia (
    id       INT          NOT NULL AUTO_INCREMENT,
    nome     VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    estoque  INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
) ;

CREATE TABLE IF NOT EXISTS salao (
    id           INT          NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(100) NOT NULL,
    funcionarios INT          NOT NULL DEFAULT 0,
    servico      VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
)

