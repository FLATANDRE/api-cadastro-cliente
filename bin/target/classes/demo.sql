set schema 'di';

-- Endereços
INSERT INTO endereco (id, bairro, cep, logradouro,  id_cidade) VALUES 
        (1, 'Centro', '21911123', 'Rua Professor José, 23', 3658),
        (2, 'Jardim', '33522021', 'Av Pereira, 627',  5270),
        (3, 'Centro', '5544525', 'Rua da Ladeira, 122',  5271),
        (4, 'Freguesia', '21311423', 'Rua Pereira, 1420',  570),
        (5, 'Barreto', '172638472', 'Rua das Conchas, 132',  270),
        (6, 'Nova Cidade', '19583723', 'Av Presidente, 212',  50),
        (7, 'Icaraí', '98746372', 'Rua das flores, 412',  5),
        (8, 'Penha', '54830298', 'Rua Bastos, 122',  152);

SELECT setval('endereco_seq', 8);

-- Pessoas Juridicas 
INSERT INTO pessoa (id, nome, id_endereco) VALUES 
        (1, 'AICare', 1),
        (2, 'Cliente 1', 2),
        (3, 'Cliente 2', 3),
        (4, 'Cliente 3', 4);

INSERT INTO pessoa_juridica (cnpj, nome_fantasia, id) VALUES
        ('87884331000110', 'AICare', 1),
	('31879666000195', 'Cliente 1 LTDA', 2),
	('68536992000100', 'Cliente 2 LTDA', 3),
	('31661118000194', 'Cliente 3 LTDA', 4);

-- Pessoas Fisicas
INSERT INTO pessoa (id, nome, id_endereco) VALUES 
        (5, 'Mariana Almeida', 5),
        (6, 'Adalberto Reis', 6),
        (7, 'Lucia de Fátima', 7),
        (8, 'Maria José', 8);

INSERT INTO pessoa_fisica (cpf, data_nascimento, rg, id) VALUES
        ('03878922060','1980-02-20','258624462',5),
        ('55391989051','1976-05-28','396052083',6),
        ('86003424060','1964-08-19','497308289',7),
        ('18782636095','1972-10-10','353974419',8);

SELECT setval('pessoa_seq', 8);

-- Usuario user
-- senha: 12345678
INSERT INTO usuario
	(id, data_criacao, login, senha, habilitado, id_pessoa)
	VALUES (1, '2019-05-28', 'user', 'Q49twsPgvzjcYbGb6vV6RgdIy8pfANntyqxBcN0djWYjbMXyiBHOv559IUmLZG1Ne6YP3UsiR+92NiJYzBAt4A==', true, 5);

SELECT setval('usuario_seq', 1);

INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES
        (1, 1),
        (1, 2),
        (1, 3);

-- Edificio do hospital
INSERT INTO localizacao_fisica
           (id, nome, pessoa_juridica, endereco)
           VALUES (1, 'Cliente 1 LTDA', 2,2);

SELECT setval('localizacao_fisica_seq', 1);

-- Estoque de bombas de infusão
INSERT INTO compartimento
            (id, nome, id_localizacao_fisica, descricao, tipo_compartimento)
            VALUES (1, 'Estoque' ,1, 'Descrição do estoque', 5);

-- Sala de enfermagem
INSERT INTO compartimento
            (id, nome, id_localizacao_fisica, descricao, tipo_compartimento)
            VALUES (2, 'Sala 100' ,1, 'Descrição da sala', 1);

-- Quarto 101
INSERT INTO compartimento
            (id, nome, id_localizacao_fisica, descricao, tipo_compartimento)
            VALUES (3, 'Quarto 101' ,1, 'Descrição do quarto', 4);

SELECT setval('compartimento_seq', 3);

-- Bomba Infusora
INSERT INTO equipamento
            (id,  modelo, serial_number, fabricante, operativo)
            VALUES (1,'xkt1','123456789','xpto',true);
INSERT INTO bomba
            (id)
            VALUES (1);

SELECT setval('equipamento_seq', 1);