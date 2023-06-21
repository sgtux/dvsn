DROP TABLE IF EXISTS comentario;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS sessao;

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    sobrenome VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    senha VARCHAR(200) NOT NULL,
    foto VARCHAR(500),
    funcao INT,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP
);

CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    texto VARCHAR(2000) NOT NULL,
    foto VARCHAR(200),
    visibilidade CHAR(1),
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP,
    usuario_id INT CONSTRAINT fk_post_usuario REFERENCES usuario(id)
);

CREATE TABLE comentario (
    id SERIAL PRIMARY KEY,
    texto VARCHAR(2000) NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    usuario_id INT CONSTRAINT fk_comentario_usuario REFERENCES usuario (id),
    post_id INT CONSTRAINT fk_comentario_post REFERENCES post (id)
);

CREATE TABLE sessao (
    id SERIAL PRIMARY KEY,
    token VARCHAR(200) NOT NULL,
    expira_em TIMESTAMP NOT NULL,
    ativo BOOLEAN,
    usuario_id INT CONSTRAINT fk_comentario_usuario REFERENCES usuario (id)
);

INSERT INTO usuario (nome, sobrenome, email, senha, foto, funcao, criado_em, atualizado_em) VALUES
('Janis', 'Joplin', 'janis@mail.com', '46f94c8de14fb36680850768ff1b7f2a', '4b0a5bindex.jpg', 2, '2021-04-18 14:49:16.698882', '2021-04-26 14:49:26.435425'),
('Jimi', 'Hendrix', 'jimi@mail.com', '061fba5bdfc076bb7362616668de87c8', '4cc947jimiprofile.jpg', 2, '2021-04-20 14:29:16.749603', '2021-04-26 14:45:12.258211'),
('Bob', 'Marley', 'bob@mail.com', '0acf4539a14b3aa27deeb4cbdf6e989f', '46871abobprofile.jpg', 1, '2021-04-21 14:46:00.556941', '2021-04-26 14:11:48.999775'),
('Jim', 'Morrison', 'jim@mail.com', '5f4dcc3b5aa765d61d8327deb882cf99', '7eh3iufymorrison.jpg', 2, '2022-07-13 10:23:00.556941', '2021-04-26 14:11:48.999775'),
('Amy''', 'Winehouse', 'amy@mail.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', '7fury78chnvamy.jpg', 2, '2022-09-08 17:42:00.556941', '2021-04-26 14:11:48.999775');

INSERT INTO post (texto, foto, visibilidade, criado_em, atualizado_em, usuario_id) VALUES
('Everything that you want from me negative will stick to your chest and return in the form of peace.', 'cf96c2bobpost2.jpg', 'R', '2021-04-22 14:54:59.629374', null, 3),
('The only thing you have in this life that is really worth it are feelings.', 'ada3a2janispost2.jpg', 'R', '2021-04-23 14:30:37.773427', null, 1),
('To change the world you need to change your mind.', '61615bjimipost3.jpg', 'F', '2021-04-23 14:31:53.475346', null, 2),
('As long as eye color is more important than skin color, there will be war!', 'ac6612bobpost1.jpg', 'P', '2021-04-23 14:51:58.845436', null, 3),
('When the power of love overcomes the love of power the world will know peace.', '1f3fe2jimipost2.jpg', 'P', '2021-04-25 14:46:10.952131', null, 2),
('If those who dont like me knew what I feel for them, they would like it even less.', '3136f6bobpost3.jpg', 'F', '2021-04-26 14:40:48.199879', null, 3),
('It is better to live, Ten years of an effervescent life than to die at seventy and have spent your life watching TV.', 'f0e194janispost1.jpg', 'F', '2021-04-26 14:47:37.880515', null, 1);

INSERT INTO comentario (texto, criado_em, usuario_id, post_id) VALUES 
('Very Nice Janis!!!', '2021-04-26 15:00:02.880515', 2, 7),
('Tks Jimi!', '2021-04-26 15:02:02.880515', 1, 7);

