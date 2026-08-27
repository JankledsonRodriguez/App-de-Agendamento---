CREATE DATABASE IF NOT EXISTS agendamento CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE agendamento;

CREATE TABLE usuarios(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 email VARCHAR(120) NOT NULL UNIQUE,
 senha VARCHAR(255) NOT NULL
);

CREATE TABLE pacientes(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(120) NOT NULL,
 cpf VARCHAR(14) UNIQUE,
 data_nascimento DATE,
 telefone VARCHAR(30) NOT NULL,
 email VARCHAR(120),
 endereco VARCHAR(255)
);

CREATE TABLE especialidades(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 descricao VARCHAR(255)
);

CREATE TABLE medicos(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(120) NOT NULL,
 especialidade VARCHAR(100),
 crm VARCHAR(30)
);

CREATE TABLE consultas(
 id INT AUTO_INCREMENT PRIMARY KEY,
 paciente_id INT NOT NULL,
 medico_id INT,
 data DATE NOT NULL,
 hora TIME NOT NULL,
 especialidade VARCHAR(120) NOT NULL,
 observacao TEXT,
 status VARCHAR(30) DEFAULT 'AGENDADO',
 criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY(paciente_id) REFERENCES pacientes(id)
 ON UPDATE CASCADE ON DELETE RESTRICT,
 FOREIGN KEY(medico_id) REFERENCES medicos(id)
 ON UPDATE CASCADE ON DELETE SET NULL
);

INSERT INTO usuarios(nome,email,senha) VALUES
('Administrador','admin@clinique.com','123456');

INSERT INTO pacientes(nome,cpf,data_nascimento,telefone,email,endereco) VALUES
('Paciente Exemplo','000.000.000-00','1990-01-01','61999990000','paciente@email.com','Rua das Clínicas, 123');

INSERT INTO medicos(nome,especialidade,crm) VALUES
('Dr. Carlos Silva','Cardiologia','CRM/BR 12345');

INSERT INTO consultas(paciente_id,medico_id,data,hora,especialidade,observacao,status) VALUES
(1,1,CURDATE(),'10:00:00','Cardiologia','Atendimento inicial','AGENDADO');
