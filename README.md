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
|Email|Hash Senha|Senha|Função|
|-|-|-|
|bob@mail.com|0acf4539a14b3aa27deeb4cbdf6e989f|1|
|jimi@mail.com|061fba5bdfc076bb7362616668de87c8|2|
|jim@mail.com|5f4dcc3b5aa765d61d8327deb882cf99|2|
|amy@mail.com|d8578edf8458ce06fbc5bb76a58c5ca4|2|
|janis@mail.com|202cb962ac59075b964b07152d234b70|2|



## Comandos extras:
Gerar certificado para o servidor apache2.
```sh
openssl req -newkey rsa:2048 -nodes -keyout key.pem -x509 -days 365 -out certificate.pem
```