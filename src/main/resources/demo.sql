set schema 'di';

-- Endereços
INSERT INTO endereco (id, bairro, cep, logradouro,  id_cidade) VALUES 
        (1, 'Ipanema', '22411010', 'R. Vinícius de Moraes, 49', 3658),
        (2, 'Bela Vista', '01333000','R. São Carlos do Pinhal, 495' ,  5270),
        (3, 'Copacabana', '22060001', 'Av. Nossa Sra. de Copacabana, 945 A',  3658),
        (4, 'Jardim Paulista', '01423002', 'R. José Maria Lisboa, 838',  5270),
        (5, 'Botafogo', '22280030', 'R. Dezenove de Fevereiro, 140',  3658),
        (6, 'Bela Vista', '01310000', 'Av. Paulista, 854',  5270),
        (7, 'Centro', '20031003', 'Av. Alm. Barroso, 25',  3658),
        (8, 'Bela Vista', '01311000', 'Av. Paulista, 149',  5270);

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

-- Configuracao

INSERT INTO configuracao (id, titulo, descricao,  pj, registro, sobre) VALUES
        (1, 'AIcare DI', 'Dynamic Inventory',  1, 'AICare', 'A Inteligência a Serviço da VIDA

Sistemas de inteligência artificial desenvolvidos com foco no suporte a vida e o cuidado aos pacientes.

Aumentar o controle e gestão sobre atendimentos e procedimentos mais importantes no ambiente hospitalar, se tornou imperativo para quem busca a excelência na saúde.

Eliminar processos burocráticos do profissional de saúde, reflete em um atendimento melhor e mais humano a quem realmente precisa de atenção: o paciente.

Ter um controle online dos principais equipamentos e medicamentos de sua unidade hospitalar, pode garantir uma melhor produtividade e aproveitamento de recursos para quem busca redução de custo operacional e retorno sobre investimentos.

Garantir a correta movimentação e administração de drogas e medicamentos em tempo real da farmácia ao paciente. Uma nova realidade, que pode transformar a rotina e a segurança para quem trabalha e quem é atendido hoje em uma unidade hospitalar.

Monitorar remotamente parâmetros importates a pacientes ou idosos de forma a viabilizar uma assistência efetiva e responsiva em eventos de risco e acompanhamento do atendimento recebido.');


-- Telefones
INSERT INTO telefone (numero, id_pessoa, id) VALUES
        ('5521999999999', 1, 1),
        ('5521999999999', 2, 2),
        ('5521999999999', 3, 3),
        ('5521999999999', 4, 4),
        ('5521999999999', 5, 5),
        ('5521999999999', 6, 6),
        ('5521999999999', 7, 7),
        ('5521999999999', 8, 8);

SELECT setval('telefone_seq', 8);

-- Usuarios
-- senha: 12345678
INSERT INTO usuario
	(id, data_criacao, login, senha, habilitado, id_pessoa) VALUES
        (1, '2019-05-28', 'admin', 'Q49twsPgvzjcYbGb6vV6RgdIy8pfANntyqxBcN0djWYjbMXyiBHOv559IUmLZG1Ne6YP3UsiR+92NiJYzBAt4A==', true, 5),
        (2, '2019-05-28', 'gestor', 'Q49twsPgvzjcYbGb6vV6RgdIy8pfANntyqxBcN0djWYjbMXyiBHOv559IUmLZG1Ne6YP3UsiR+92NiJYzBAt4A==', true, 6),
        (3, '2019-05-28', 'operador', 'Q49twsPgvzjcYbGb6vV6RgdIy8pfANntyqxBcN0djWYjbMXyiBHOv559IUmLZG1Ne6YP3UsiR+92NiJYzBAt4A==', true, 7);

SELECT setval('usuario_seq', 3);

INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES
        (1, 1),
        (1, 2),
        (1, 3),
        (1, 4),
        (1, 5),
        (1, 6),
        (1, 7),
        (2, 1),
        (2, 5),
        (2, 6),
        (3, 3),
        (3, 4),
        (3, 7);

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

-- Modelos e fabricantes

INSERT INTO fabricante_dispositivo (id, nome) VALUES 
    (2, 'MGT');
SELECT setval('fabricante_dispositivo_seq', 2);

INSERT INTO modelo_dispositivo (id, nome, tensao_min_bat, tensao_max_bat, fabricante) VALUES 
    (2, 'C7', 2.5, 3.0, 2),
    (3, 'G1', 0, 0, 2);
SELECT setval('modelo_dispositivo_seq', 3);

INSERT INTO fabricante_equipamento (id, nome) VALUES 
    (2, 'BBraun');
SELECT setval('fabricante_equipamento_seq', 2);

INSERT INTO modelo_equipamento (id, nome, intervalo_manutencao_obrigatoria, fabricante) VALUES 
    (2, 'Infusomat Compact', 6, 2),
    (3, 'Nutrimat Ii', 2, 2);
SELECT setval('modelo_equipamento_seq', 3);

-- Bomba Infusora
INSERT INTO equipamento
            (id,  modelo, serial_number, operativo)
            VALUES (1, 2,'123456789',true);
INSERT INTO bomba
            (id, tipo_bomba)
            VALUES (1, 1);

SELECT setval('equipamento_seq', 1);

insert into colaborador (id, inicio, id_pessoa_fisica, id_profissao)
    values(1, current_date, 5, 1), 
          (2, current_date, 5, 2),
          (3, current_date, 6, 2),
          (4, current_date, 6, 3),
          (5, current_date, 7, 3),
          (6, current_date, 7, 4),
          (7, current_date, 8, 4),
          (10,current_date, 5, 5);


insert into vinculado (id, matricula, observacao, id_natureza_vinculo)
   values(1, '2705', 'Vinculado 1' ,1), 
         (2, '2707', 'Vinculado 2' ,2),
         (3, '2708', 'Vinculado 3' ,2),
         (4, '2709', 'Vinculado 4' ,3),
         (5, '2710', 'Vinculado 5' ,3),
         (6, '2711', 'Vinculado 6' ,4),
         (7, '2712', 'Vinculado 7' ,4),
         (10, '2713', 'Vinculado 8',2);
SELECT setval('colaborador_seq', 10);
