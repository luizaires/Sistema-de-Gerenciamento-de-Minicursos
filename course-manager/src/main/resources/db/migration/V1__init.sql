-- Criando tabela usuario
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
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
INSERT INTO usuario (nome, email, usuario, senha, tipo)
VALUES ('admin', 'admin@gmail.com', 'Administrator', 'admin123', 'ADMIN');

-- Inserindo dados de exemplo
INSERT INTO usuario (nome, email, usuario, senha, tipo)
VALUES
('Gustavo Alves Mendes', 'gustavoamendes@hotmail.com', 'gustavo_mendes', 'gustavo123', 'ALUNO'),
('Wiliane da Silva Lima', 'wilyanelima17@outlook.com', 'wilianelima', 'wiliane123', 'ALUNO'),
('Ruan Pabllo Barbosa Claudino', 'ruanpabllobc@gmail.com', 'ruanpabllobc', 'ruanpabllo123', 'ALUNO'),
('Huliane Medeiros da Silva', 'huliane@ufersa.edu.br', 'huliane', 'huliane123', 'PROFESSOR');

INSERT INTO minicurso (titulo, descricao, instrutor_id, carga_horaria, vagas, data_inicio, data_fim)
VALUES
('Introdução a Robótica', 'Aprenda a construir robôs, usando arduino', 2, 10, 20, '2025-04-15', '2025-04-16'),
('Desenvolvimento Web com React', 'Crie aplicações web modernas utilizando a biblioteca React e todo seu ecossistema.', 3, 20, 25, '2025-05-01', '2025-05-03'),
('Machine Learning para Iniciantes', 'Introdução aos conceitos fundamentais de aprendizado de máquina com Python e scikit-learn.', 1, 15, 20, '2025-04-15', '2025-04-16');