CREATE TABLE contrato (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    numero_contrato VARCHAR(36) NOT NULL UNIQUE ,
    aluno_id BIGINT(20) NOT NULL,
    empresa_id BIGINT(20) NOT NULL,
    horas_semanais BIGINT(20) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    data_inicial DATE NOT NULL,
    data_final DATE,
    situacao_contrato VARCHAR(30) NOT NULL,
    status_termino VARCHAR(30),
    FOREIGN KEY (aluno_id) REFERENCES aluno(id),
    FOREIGN KEY (empresa_id) REFERENCES empresa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



