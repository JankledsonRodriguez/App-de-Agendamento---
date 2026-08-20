CREATE DATABASE IF NOT EXISTS agendamento CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE agendamento;

CREATE TABLE usuarios(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 email VARCHAR(120) NOT NULL UNIQUE,
 senha VARCHAR(255) NOT NULL
);

CREATE TABLE clientes(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(120) NOT NULL,
 telefone VARCHAR(30) NOT NULL,
 email VARCHAR(120)
);

CREATE TABLE servicos(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 descricao VARCHAR(255),
 duracao INT DEFAULT 30,
 preco DECIMAL(10,2) DEFAULT 0
);

CREATE TABLE profissionais(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(120) NOT NULL,
 especialidade VARCHAR(100),
 telefone VARCHAR(30)
);

CREATE TABLE horarios(
 id INT AUTO_INCREMENT PRIMARY KEY,
 dia_semana TINYINT NOT NULL,
 hora_inicio TIME NOT NULL,
 hora_fim TIME NOT NULL
);

CREATE TABLE agendamentos(
 id INT AUTO_INCREMENT PRIMARY KEY,
 cliente_id INT NOT NULL,
 data DATE NOT NULL,
 hora TIME NOT NULL,
 servico VARCHAR(120) NOT NULL,
 observacao TEXT,
 status VARCHAR(30) DEFAULT 'AGENDADO',
 criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY(cliente_id) REFERENCES clientes(id)
 ON UPDATE CASCADE ON DELETE RESTRICT
);

INSERT INTO usuarios(nome,email,senha) VALUES
('Administrador','admin@agenda.com','123456');

INSERT INTO clientes(nome,telefone,email) VALUES
('João da Silva','61999990000','joao@email.com'),
('Maria Oliveira','61988880000','maria@email.com');

INSERT INTO servicos(nome,descricao,duracao,preco) VALUES
('Consulta','Atendimento padrão',60,100.00),
('Manutenção','Serviço técnico',90,150.00);

INSERT INTO profissionais(nome,especialidade,telefone) VALUES
('Carlos Souza','Atendimento','61977770000'),
('Ana Lima','Especialista','61966660000');

INSERT INTO horarios(dia_semana,hora_inicio,hora_fim) VALUES
(1,'08:00:00','18:00:00'),
(2,'08:00:00','18:00:00'),
(3,'08:00:00','18:00:00'),
(4,'08:00:00','18:00:00'),
(5,'08:00:00','18:00:00');

INSERT INTO agendamentos(cliente_id,data,hora,servico,observacao,status) VALUES
(1,CURDATE(),'10:00:00','Consulta','Primeiro atendimento','AGENDADO');
