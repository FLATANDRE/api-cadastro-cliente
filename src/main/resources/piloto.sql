set schema 'di';

-- Endereços
INSERT INTO endereco (id, bairro, cep, logradouro,  id_cidade, latitude, longitude) VALUES 
        (1, 'Ipanema', '22411010', 'R. Vinícius de Moraes, 49', 3658, null, null),
        (2, 'Copacabana', '22061080', 'Tv. Frederico Pamplona, 32' ,  3658, -22.9724808, -43.1924194),
        (3, 'Cerqueira César', '01409002', 'Rua Peixoto Gomide, 545',  5270, -23.5591256, -46.6551639),
        (4, 'Jardim Paulista', '01423002', 'R. José Maria Lisboa, 838',  5270, null, null),
        (5, 'Botafogo', '22280030', 'R. Dezenove de Fevereiro, 140',  3658, null, null),
        (6, 'Bela Vista', '01310000', 'Av. Paulista, 854',  5270, null, null),
        (7, 'Centro', '20031003', 'Av. Alm. Barroso, 25',  3658, null, null);

SELECT setval('endereco_seq', 7);

-- Pessoas Juridicas 
INSERT INTO pessoa (id, nome, id_endereco) VALUES 
        (1, 'AICare', 1),
        (2, 'Hospital São Lucas Copacabana', 2),
        (3, 'Hospital 9 de Julho', 3);

INSERT INTO pessoa_juridica (cnpj, nome_fantasia, id) VALUES
        ('87884331000110', 'AICare', 1),
	('31879666000195', 'Hospital São Lucas Copacabana', 2),
	('68536992000100', 'Hospital 9 de Julho', 3);

-- Pessoas Fisicas
INSERT INTO pessoa (id, nome, id_endereco) VALUES 
        (4, 'Mariana Almeida', 4),
        (5, 'Adalberto Reis', 5),
        (6, 'Lucia de Fátima', 6),
        (7, 'Maria José', 7);

INSERT INTO pessoa_fisica (cpf, data_nascimento, rg, id) VALUES
        ('03878922060','1980-02-20','258624462',4),
        ('55391989051','1976-05-28','396052083',5),
        ('86003424060','1964-08-19','497308289',6),
        ('18782636095','1972-10-10','353974419',7);

SELECT setval('pessoa_seq', 7);

-- Telefones
INSERT INTO telefone (numero, id_pessoa, id) VALUES
        ('5521999999999', 1, 1),
        ('5521999999999', 2, 2),
        ('5521999999999', 3, 3),
        ('5521999999999', 4, 4),
        ('5521999999999', 5, 5),
        ('5521999999999', 6, 6),
        ('5521999999999', 7, 7);

SELECT setval('telefone_seq', 7);

-- Configuracao

INSERT INTO configuracao (id, titulo, descricao,  pj, registro, sobre) VALUES
        (1, 'AIcare DI', 'Dynamic Inventory',  1, 'AICare', 'A Inteligência a Serviço da VIDA

Sistemas de inteligência artificial desenvolvidos com foco no suporte a vida e o cuidado aos pacientes.

Aumentar o controle e gestão sobre atendimentos e procedimentos mais importantes no ambiente hospitalar, se tornou imperativo para quem busca a excelência na saúde.

Eliminar processos burocráticos do profissional de saúde, reflete em um atendimento melhor e mais humano a quem realmente precisa de atenção: o paciente.

Ter um controle online dos principais equipamentos e medicamentos de sua unidade hospitalar, pode garantir uma melhor produtividade e aproveitamento de recursos para quem busca redução de custo operacional e retorno sobre investimentos.

Garantir a correta movimentação e administração de drogas e medicamentos em tempo real da farmácia ao paciente. Uma nova realidade, que pode transformar a rotina e a segurança para quem trabalha e quem é atendido hoje em uma unidade hospitalar.

Monitorar remotamente parâmetros importates a pacientes ou idosos de forma a viabilizar uma assistência efetiva e responsiva em eventos de risco e acompanhamento do atendimento recebido.');

-- Usuarios
-- senha: 12345678
INSERT INTO usuario
	(id, data_criacao, login, senha, habilitado, id_pessoa) VALUES
        (1, '2019-05-28', 'admin', 'Q49twsPgvzjcYbGb6vV6RgdIy8pfANntyqxBcN0djWYjbMXyiBHOv559IUmLZG1Ne6YP3UsiR+92NiJYzBAt4A==', true, 5),
        (2, '2019-05-28', 'gestor', 'Q49twsPgvzjcYbGb6vV6RgdIy8pfANntyqxBcN0djWYjbMXyiBHOv559IUmLZG1Ne6YP3UsiR+92NiJYzBAt4A==', true, 6),
        (3, '2019-05-28', 'operador', 'Q49twsPgvzjcYbGb6vV6RgdIy8pfANntyqxBcN0djWYjbMXyiBHOv559IUmLZG1Ne6YP3UsiR+92NiJYzBAt4A==', true, 7);

SELECT setval('usuario_seq', 3);

INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES
        (1, 2),
        (2, 1),
        (2, 5),
        (2, 6),
        (3, 3),
        (3, 4),
        (3, 7);

-- API KEY

INSERT INTO api_key
        (id, data_criacao, data_vencimento, nome, habilitado, key) VALUES
        (1, '2019-10-23','2030-01-01','Processador de eventos',true,'YWljYXJlLmFwaWtleS5wcm9jZXNzYWRvcl9kZV9ldmVudG9zLjE1NzE4NjY5ODUzNzYuZ0xnSmJBSmFfbDUySFFBanZZWXVMZkprVmdTNXVNa2UwNVZta05FRTZneTEydjNBa2V3elZxR3MyQjh3T0NJcm9KYUZxQ2owRXNONlExRTQ4TmRoV0E9PQ=='),
        (2, '2019-10-23','2030-01-01','Teste admin',true,'YWljYXJlLmFwaWtleS50ZXN0ZV9hZG1pbi4xNTcxODY3MDA2ODc0LnFJdmgwYUZBalEtMXNiZk51dDRzclBQYWNhekVsREVDR0xrOHl6d00waXNjZ2ZVeDZfVmxlb0JKSUZpcEVhelYyaVp3WVNZMHZoYWpFSDBrLVk0R3dRPT0=');

SELECT setval('api_key_seq', 2);

INSERT INTO api_key_perfil (id_api_key, id_perfil) VALUES
        (1, 8),
        (2, 2);

-- Edificio do hospital
INSERT INTO localizacao_fisica
           (id, nome, pessoa_juridica, endereco) VALUES
            (1, 'Hospital São Lucas Copacabana', 2,2),
            (2, 'Hospital 9 de Julho', 3,3);

SELECT setval('localizacao_fisica_seq', 2);

-- Estoque de bombas de infusão
INSERT INTO compartimento
            (id, nome, id_localizacao_fisica, descricao, tipo_compartimento) VALUES 
            (1, 'Engenharia Clínica' ,1, 'Engenharia Clínica do Hospital São Lucas Copacabana', 2),
            (2, 'Engenharia Clínica 1 Andar' ,2, 'Engenharia Clínica 1 Andar do Hospital 9 de Julho', 2);

SELECT setval('compartimento_seq', 2);

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
    (2, 'Infusomat Compact', 6, 2);
SELECT setval('modelo_equipamento_seq', 2);

-- Bomba Infusora
INSERT INTO equipamento
            (id,  modelo, serial_number, operativo)
            VALUES (1, 2,'123456789',true);
INSERT INTO bomba
            (id, tipo_bomba)
            VALUES (1, 1);

SELECT setval('equipamento_seq', 1);


-- Dispositivos
INSERT INTO dispositivo_estado_atual
            (id) VALUES (1), (2);

SELECT setval('dispositivo_estado_atual_seq', 2);

INSERT INTO dispositivo
            (id, mac, operativo, modelo, tipo_dispositivo, estado_atual) VALUES
            (1, 'AC233FC0370C',true, 3, 2, 1),
            (2, 'AC233FC036FA',true, 3, 2, 2);

SELECT setval('dispositivo_seq', 1);

-- Associacoes
INSERT INTO associacao_dispositivo
            (id, associacao, id_dispositivo) VALUES
            (1, '2019-09-20 22:19:45.485', 1),
            (2, '2019-09-20 22:29:45.485', 2);

INSERT INTO associacao_dispositivo_compartimento
            (id, id_compartimento) VALUES
            (1, 1),
            (2, 2);

 SELECT setval('associacao_dispositivo_seq', 2);     

-- Tipos de insumos
INSERT INTO tipo_insumo
            (id,nome,codigo,preco_padrao) VALUES
            (1, 'Insumo 1', '111111', 12.5),
            (2, 'Insumo 2', '222222', 1.99),
            (3, 'Insumo 3', '333333', 2.45);