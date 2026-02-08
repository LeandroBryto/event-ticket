f# API de Gestão de Ingressos

![Status](https://img.shields.io/badge/status-em_desenvolvimento-yellow)
![Linguagem](https://img.shields.io/badge/Java-21-blue)
![Framework](https://img.shields.io/badge/Spring_Boot-3.2.2-green)

## 📝 Sobre o Projeto

Esta é uma API RESTful para um **Sistema de Gestão de Eventos**, desenvolvida como parte de um desafio técnico. A aplicação permite o gerenciamento completo de eventos, a venda de ingressos e a autenticação de diferentes perfis de usuários (Comprador, Organizador e Master).

O projeto foi construído com foco em boas práticas de desenvolvimento, código limpo, arquitetura em camadas e segurança.

---

##  Funcionalidades Principais

- **Autenticação e Autorização:**
  - Sistema de login com JWT (Access Token + Refresh Token).
  - Controle de acesso baseado em perfis (`USUARIO`, `ORGANIZADOR`, `MASTER`).
  - Endpoints para registro, login e recuperação de senha.

- **Gerenciamento de Eventos (CRUD):**
  - Organizadores podem criar, listar, atualizar e deletar seus próprios eventos.
  - Validação para garantir que apenas organizadores gerenciem eventos.
  - Endpoints públicos para listagem e visualização de eventos.

- **Venda de Ingressos:**
  - Endpoint transacional para compra de ingressos.
  - Validação de capacidade e baixa automática de estoque.
  - Geração de ingressos com código de validação único.

- **Notificações por Email:**
  - Envio de email de boas-vindas no registro.
  - Envio de email para recuperação de senha.

- **Relatórios:**
  - Endpoint para um usuário listar todos os seus ingressos comprados.
  - Endpoint para um organizador listar todos os ingressos vendidos de um evento específico.

---

##  Tecnologias Utilizadas

- **Backend:**
  - **Java 21**
  - **Spring Boot 3.2.2**
  - **Spring Security:** Para autenticação e autorização.
  - **Spring Data JPA:** Para persistência de dados.
  - **Auth0 Java JWT:** Para gerenciamento de JSON Web Tokens.
  - **Spring Boot Mail:** Para envio de emails.

- **Banco de Dados:**
  - **PostgreSQL**

- **Ambiente e Build:**
  - **Docker & Docker Compose:** Para containerização da aplicação e do banco de dados.
  - **Maven:** Para gerenciamento de dependências e build do projeto.

---

##  Como Executar o Projeto

A maneira mais recomendada de executar o projeto é utilizando Docker, pois ele configura todo o ambiente necessário (banco de dados e aplicação) automaticamente.

### Pré-requisitos

- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Java 21 (ou superior)](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)

### 1. Clone o Repositório

```bash
git clone https://github.com/LeandroBryto/event-ticket.git
cd event-ticket
```

### 2. Configuração do Ambiente

O projeto utiliza um arquivo `application-dev.properties` para as configurações locais, que é ignorado pelo Git.

**Para rodar localmente na sua IDE:**
O arquivo `application-dev.properties` já contém as credenciais de email. A aplicação usará o perfil `dev` por padrão e funcionará sem nenhuma configuração adicional.

**Para rodar com Docker:**
Crie um arquivo chamado `.env` na raiz do projeto com as seguintes variáveis de ambiente (este arquivo não será enviado para o GitHub):

```env
# Credenciais do Email para o Docker
MAIL_USERNAME=
MAIL_PASSWORD=

# (Opcional) Você pode sobrescrever as credenciais do banco aqui também
# SPRING_DATASOURCE_USERNAME=
# SPRING_DATASOURCE_PASSWORD=
```

### 3. Executando com Docker (Recomendado)

Com o Docker e o Docker Compose instalados, execute o seguinte comando na raiz do projeto:

```bash
docker-compose up --build
```

A aplicação estará disponível em `http://localhost:8080`.

### 4. Executando Localmente (Alternativa)

1.  Certifique-se de ter uma instância do PostgreSQL rodando na sua máquina.
2.  Abra o projeto na sua IDE de preferência (IntelliJ, Eclipse, etc.).
3.  A IDE deve carregar as dependências do Maven automaticamente.
4.  Execute a classe principal `GestaoDeIngressosApplication.java`.

---

##  Documentação da API

A coleção do Postman com todos os endpoints pode ser encontrada no repositório (ou pode ser gerada/exportada).

**Principais Endpoints:**

- `POST /auth/register` - Registrar um novo usuário.
- `POST /auth/login` - Autenticar e receber tokens.
- `POST /auth/refresh` - Obter um novo access token.

- `GET /eventos` - Listar todos os eventos (público).
- `GET /eventos/{id}` - Ver detalhes de um evento (público).
- `POST /eventos` - Criar um novo evento (requer token de `ORGANIZADOR`).
- `PUT /eventos/{id}` - Atualizar um evento (requer token do dono).
- `DELETE /eventos/{id}` - Deletar um evento (requer token do dono).

- `POST /vendas` - Realizar a compra de ingressos (requer token de `USUARIO`).

- `GET /ingressos/meus-ingressos` - Listar os ingressos do usuário logado.
- `GET /ingressos/evento/{eventoId}` - Listar ingressos vendidos de um evento (requer token de `ORGANIZADOR`).

---

##  Status do Projeto

**Em Desenvolvimento.** O projeto está funcional, mas ainda há espaço para melhorias e novas funcionalidades.

---

## 👨‍💻 Autor

**Leandro Brito**


