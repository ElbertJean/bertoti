-- DETALHES SOBRE COMO CRIAR O BANCO DE DADOS

CREATE DATABASE carrosdb;

CREATE TABLE carro (
    id SERIAL PRIMARY KEY,
    fabricante VARCHAR(100) NOT NULL,
    placa VARCHAR(10) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    ano VARCHAR(4) NOT NULL
);