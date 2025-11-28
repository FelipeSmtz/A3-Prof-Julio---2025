CREATE DATABASE IF NOT EXISTS estoqueprodutos;

USE estoqueprodutos;

CREATE TABLE estoque (
  id_produto INT AUTO_INCREMENT PRIMARY KEY,
  nome_produto VARCHAR(50) NOT NULL,
  descricao_produto VARCHAR(200) NOT NULL,
  quantidade_estoque INT DEFAULT 0,
  preco DECIMAL(10, 2) DEFAULT 0.00,
  data_cadastro DATE
);