-- Criando tabela usuario
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL
);


-- Criando tabela minicurso
CREATE TABLE minicurso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    instrutor_id BIGINT,
    carga_horaria INT NOT NULL,
    vagas INT NOT NULL,
    numero_de_inscritos INT NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL,
    data_inicio DATE,
    data_fim DATE,
    FOREIGN KEY (instrutor_id) REFERENCES usuario(id)
);

-- Criando tabela inscricao
CREATE TABLE inscricao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    minicurso_id BIGINT NOT NULL,
    data_inscricao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (minicurso_id) REFERENCES minicurso(id),
    UNIQUE (usuario_id, minicurso_id)
);

-- Inserindo Admin User
INSERT INTO usuario (nome, email, username, senha, tipo)
VALUES ('admin', 'admin@gmail.com', 'admin', '$2a$10$DGElVstoIBDT1ct3CXOEoOckB8gcYrszChtCXkk8J56oCguy8NzDC', 'ADMIN');

-- Inserindo dados de exemplo
INSERT INTO usuario (nome, email, username, senha, tipo)
VALUES
('Gustavo Alves Mendes', 'gustavoamendes@hotmail.com', 'gustavo_mendes', '$2a$10$V6NFl3it35D2LYyT6vnFde5vYvqUJ54YptbIcGfILrktAU93a3hxO', 'ALUNO'),
('Wiliane da Silva Lima', 'wilyanelima17@outlook.com', 'wilianelima', '$2a$10$xwr4tXeT06aDlf6H1/26redjdGsZJHT91DbiAbeeSEyVii1Az8IJ6', 'ALUNO'),
('Ruan Pabllo Barbosa Claudino', 'ruanpabllobc@gmail.com', 'ruanpabllobc', '$2a$10$SsmR8iGrxe5AvpNZhzzB7uOIWdqw5zG5wk7bx2OhUakvb2jpk46d2', 'ALUNO'),
('Huliane Medeiros da Silva', 'huliane@ufersa.edu.br', 'huliane', '$2a$10$PEl0dTKvc8iX1.SRxGayKOh0BIarU7.u5FbS1eV/JwC2y806rLvxi', 'PROFESSOR');

INSERT INTO minicurso (titulo, descricao, instrutor_id, carga_horaria, vagas, status, data_inicio, data_fim)
VALUES
('Introdução a Robótica', 'Aprenda a construir robôs, usando arduino', 2, 10, 20, 'CANCELADO', '2025-04-15', '2025-04-16'),
('Desenvolvimento Web com React', 'Crie aplicações web modernas utilizando a biblioteca React e todo seu ecossistema.', 3, 20, 25, 'APROVADO', '2025-05-01', '2025-05-03'),
('Machine Learning para Iniciantes', 'Introdução aos conceitos fundamentais de aprendizado de máquina com Python e scikit-learn.', 1, 15, 20, 'PENDENTE', '2025-04-15', '2025-04-16');