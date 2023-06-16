# Damn Vulnerable Social Network

Uma aplicação vulnerável indicada para testes e entendimento de conceitos de segurança em Aplicações WEB

Requisitos:
- Java 17
- Node 19
- Docker
- Docker Compose
- Postgres instalado. (Caso não tenha o Docker e Docker Compose)

## Rodar a aplicação

### 1. Subir o banco de dados utilizando o Docker Compose. Rodar o comando abaixo na pasta raíz do projeto.
```
docker-compose up
```

### 2. Criação do banco de dados.

- Executar o script de criação do banco de dados que se encontra em dvsn-api/script.sql informando a senha **Postgres123**:

```
$ psql -U postgres -h 172.45.45.10 -d postgres -a -f ./dvsn-api/script.sql
```
- **ou** executar o script:
```
$ ./create-db.sh
```

### 3. Entrar na pasta do front (dvsn-client) e dar build no projeto, para que os arquivos sejam colocados ná pasta estática da api.

NPM:
```
npm install
npm build
```
YARN:
```
yarn
yarn build
```


### 4. Abrir o IntelliJ e rodar a aplicação.
Main Class -> RootApplication.java

## Usuários disponíveis
|Email|Hash Senha|Senha|Admin|
|-|-|-|-|
|bob@mail.com|0acf4539a14b3aa27deeb4cbdf6e989f|michael|Sim|
|jimi@mail.com|061fba5bdfc076bb7362616668de87c8|lovely|Não|
|jim@mail.com|5f4dcc3b5aa765d61d8327deb882cf99|password|Não|
|amy@mail.com|d8578edf8458ce06fbc5bb76a58c5ca4|qwerty|Não|
|janis@mail.com|46f94c8de14fb36680850768ff1b7f2a|123qwe|Não|



## Comandos extras:
Gerar certificado para o servidor apache2.
```sh
openssl req -newkey rsa:2048 -nodes -keyout key.pem -x509 -days 365 -out certificate.pem
```

## Assuntos abordados:

- OWASP TOP 10
- XSS
- CSRF
- Cookies (HttpOnly, Secure, SameSite)
- Alguns cuidados com LocalStorage e SessionStorage
- Como manter componentes/frameworks/libs seguras e atualizadas
- Cabeçalhos CSP e X-Frame-Options

## Vulnerabilidades
- SQL Injection
    - Login
    - Pesquisa de usuários
- XSS
    - Reflected
        - /hello?nome=Gabriel
    - DOM
        - Listagem de POSTS
    - Stored
        - Comentários dos posts

## TODO
- Adicionar XSS em SVG e CSS
