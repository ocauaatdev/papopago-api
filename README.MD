                                                        # 💸 PapoPago Fintech API

API REST desenvolvida com **Spring Boot** para gerenciamento financeiro pessoal, permitindo o controle de contas bancárias, transações, categorias e instituições financeiras. Projeto acadêmico desenvolvido na **FIAP**.

---

## 🛠️ Tecnologias utilizadas

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Data JPA | — |
| Spring Validation | — |
| Oracle Database | FIAP Cloud |
| Driver JDBC | ojdbc11 |

---

## ⚙️ Como executar o projeto

### Pré-requisitos

- Java 17+
- Maven
- Acesso à instância Oracle da FIAP

### Variáveis de ambiente

Configure as variáveis antes de rodar a aplicação. No IntelliJ: `Run > Edit Configurations > Environment variables`.

| Variável | Descrição |
|---|---|
| `DB_URL_PP` | URL de conexão Oracle |
| `DB_USER_PP` | Usuário do banco |
| `DB_PASSWORD_PP` | Senha do banco |

### Rodando a aplicação

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/papopago-fintech.git

# Entre na pasta
cd papopago-fintech

# Execute com Maven
./mvnw spring-boot:run
```

A API ficará disponível em: `http://localhost:8080`

---

## 📐 Arquitetura do projeto

```
src/main/java/com/fiap/papopago_fintech/
├── controller/      → Endpoints REST (recebe e responde requisições)
├── service/         → Regras de negócio
├── repository/      → Acesso ao banco de dados (JPA)
├── entity/          → Entidades mapeadas para as tabelas Oracle
├── dto/             → Objetos de transferência de dados (Request/Response)
└── exception/       → Exceções customizadas e handler global
```

### Entidades do sistema

```
Usuario
  └── Conta (N contas por usuário)
        └── Transacao (N transações por conta)
              └── Categoria (1 categoria por transação)
  Conta
    └── InstituicaoFinanceira (1 banco por conta)
```

---

## 🔁 Fluxo recomendado de uso

Para testar a API do zero, siga esta ordem — cada recurso depende do anterior:

```
1. Cadastrar Instituição Financeira  →  POST /inst-financeira/cadastrar
2. Cadastrar Usuário                 →  POST /usuario/cadastrar
3. Cadastrar Conta                   →  POST /conta/cadastrar  (usa id do usuário e do banco)
4. Cadastrar Categoria               →  POST /categorias/nova
5. Registrar Transação               →  POST /transacao/nova  (usa id da conta e da categoria)
```

---

## 📋 Padrão de erros da API

Todos os erros retornam o seguinte formato JSON:

```json
{
  "message": "Descrição do erro",
  "status": "NOME_DO_STATUS_HTTP"
}
```

| Situação | Status HTTP |
|---|---|
| Campo obrigatório ausente ou inválido | `400 Bad Request` |
| Regra de negócio violada (ex.: e-mail duplicado) | `400 Bad Request` |
| Recurso não encontrado pelo ID | `404 Not Found` |

---

## 🏦 Endpoints — Instituição Financeira

Base URL: `/inst-financeira`

> **Cadastre o banco antes de criar contas.** O código BACEN é único por instituição (máximo 3 caracteres).

---

### `POST /inst-financeira/cadastrar`
Cadastra uma nova instituição financeira.

**Request body:**
```json
{
  "nome": "Nubank",
  "codigoBacen": "260"
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `nome` | String | ✅ | Nome da instituição (deve ser único) |
| `codigoBacen` | String | ✅ | Código BACEN de 3 dígitos (deve ser único) |

**Response `201 Created`:**
```json
{
  "id": 1,
  "nome": "Nubank",
  "codigoBacen": "260"
}
```

**Erros possíveis:**
```json
{ "message": "Código do Bacen já existe", "status": "BAD_REQUEST" }
{ "message": "Nome da instituição financeira já em uso", "status": "BAD_REQUEST" }
```

---

### `GET /inst-financeira/listar`
Retorna todas as instituições financeiras cadastradas.

**Response `200 OK`:**
```json
[
  { "id": 1, "nome": "Nubank", "codigoBacen": "260" },
  { "id": 2, "nome": "Itaú Unibanco", "codigoBacen": "341" }
]
```

---

### `GET /inst-financeira/{id}`
Busca uma instituição financeira pelo ID.

**Exemplo:** `GET /inst-financeira/1`

**Response `200 OK`:**
```json
{
  "id": 1,
  "nome": "Nubank",
  "codigoBacen": "260"
}
```

**Erros possíveis:**
```json
{ "message": "Instituição financeira não encontrada", "status": "NOT_FOUND" }
```

---

### `PUT /inst-financeira/{id}`
Atualiza os dados de uma instituição financeira.

**Exemplo:** `PUT /inst-financeira/1`

**Request body:**
```json
{
  "nome": "Nu Pagamentos S.A.",
  "codigoBacen": "260"
}
```

**Response `200 OK`:**
```json
{
  "id": 1,
  "nome": "Nu Pagamentos S.A.",
  "codigoBacen": "260"
}
```

---

### `DELETE /inst-financeira/{id}`
Remove uma instituição financeira.

**Exemplo:** `DELETE /inst-financeira/1`

**Response `204 No Content`** *(sem corpo)*

**Erros possíveis:**
```json
{ "message": "Instituição financeira não encontrada", "status": "NOT_FOUND" }
```

---

## 👤 Endpoints — Usuário

Base URL: `/usuario`

> O CPF é validado no formato `XXX.XXX.XXX-XX` ou `XXXXXXXXXXX`. O e-mail deve ser único no sistema.

---

### `POST /usuario/cadastrar`
Cadastra um novo usuário.

**Request body:**
```json
{
  "nome": "João da Silva",
  "email": "joao.silva@email.com",
  "dataNascimento": "15/03/1998",
  "numeroTelefone": "11999887766",
  "cpf": "123.456.789-09",
  "senha": "minhasenha123"
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `nome` | String | ✅ | Nome completo |
| `email` | String | ✅ | E-mail válido e único no sistema |
| `dataNascimento` | String | ✅ | Formato `dd/MM/yyyy` |
| `numeroTelefone` | String | ✅ | Número com DDD (até 15 caracteres) |
| `cpf` | String | ✅ | CPF válido (com ou sem pontuação) |
| `senha` | String | ✅ | Senha do usuário |

**Response `201 Created`:**
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao.silva@email.com",
  "dataNascimento": "1998-03-15",
  "numeroTelefone": "11999887766",
  "cpf": "123.456.789-09"
}
```

> ⚠️ A senha **não é retornada** na resposta por segurança.

**Erros possíveis:**
```json
{ "message": "Email já cadastrado", "status": "BAD_REQUEST" }
{ "message": "Data de nascimento inválida. Use o formato dd/MM/yyyy.", "status": "BAD_REQUEST" }
{ "message": "CPF deve ser válido", "status": "BAD_REQUEST" }
```

---

### `GET /usuario/{id}`
Busca um usuário pelo ID.

**Exemplo:** `GET /usuario/1`

**Response `200 OK`:**
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao.silva@email.com",
  "dataNascimento": "1998-03-15",
  "numeroTelefone": "11999887766",
  "cpf": "123.456.789-09"
}
```

**Erros possíveis:**
```json
{ "message": "Usuário não encontrado com o ID: 1", "status": "NOT_FOUND" }
```

---

### `PUT /usuario/{id}`
Atualiza os dados de um usuário. Não é possível alterar CPF ou senha por este endpoint.

**Exemplo:** `PUT /usuario/1`

**Request body:**
```json
{
  "nome": "João da Silva Santos",
  "email": "joao.santos@email.com",
  "dataNascimento": "15/03/1998",
  "numeroTelefone": "11988776655"
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `nome` | String | ✅ | Nome completo |
| `email` | String | ✅ | E-mail válido |
| `dataNascimento` | String | ✅ | Formato `dd/MM/yyyy` |
| `numeroTelefone` | String | ✅ | Número com DDD |

**Response `200 OK`:**
```json
{
  "id": 1,
  "nome": "João da Silva Santos",
  "email": "joao.santos@email.com",
  "dataNascimento": "1998-03-15",
  "numeroTelefone": "11988776655",
  "cpf": "123.456.789-09"
}
```

---

### `DELETE /usuario/{id}`
Remove um usuário.

**Exemplo:** `DELETE /usuario/1`

**Response `204 No Content`** *(sem corpo)*

---

## 💳 Endpoints — Conta

Base URL: `/conta`

> A conta precisa de um usuário e de uma instituição financeira já cadastrados. O saldo inicial deve ser zero ou positivo.

---

### `POST /conta/cadastrar`
Cadastra uma nova conta bancária.

**Request body:**
```json
{
  "nomeConta": "Conta Corrente Nubank",
  "saldo": 1500.00,
  "idUsuario": 1,
  "idBank": 1
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `nomeConta` | String | ✅ | Nome ou apelido da conta |
| `saldo` | Number | ✅ | Saldo inicial (≥ 0) |
| `idUsuario` | Long | ✅ | ID do usuário dono da conta |
| `idBank` | Long | ✅ | ID da instituição financeira |

**Response `201 Created`:**
```json
{
  "id": 1,
  "nomeConta": "Conta Corrente Nubank",
  "saldo": 1500.00,
  "usuario": {
    "id": 1,
    "nome": "João da Silva"
  },
  "instituicaoFinanceira": {
    "id": 1,
    "nome": "Nubank",
    "codigoBacen": "260"
  }
}
```

**Erros possíveis:**
```json
{ "message": "Usuário não encontrado com o ID: 1", "status": "NOT_FOUND" }
{ "message": "Instituição financeira não encontrada com o ID: 1", "status": "NOT_FOUND" }
{ "message": "O saldo deve ser um valor positivo ou zero", "status": "BAD_REQUEST" }
```

---

### `GET /conta/{id}`
Busca uma conta pelo ID.

**Exemplo:** `GET /conta/1`

**Response `200 OK`:**
```json
{
  "id": 1,
  "nomeConta": "Conta Corrente Nubank",
  "saldo": 1500.00,
  "usuario": {
    "id": 1,
    "nome": "João da Silva"
  },
  "instituicaoFinanceira": {
    "id": 1,
    "nome": "Nubank",
    "codigoBacen": "260"
  }
}
```

---

### `GET /conta/minhas-contas/{idUsuario}`
Lista todas as contas de um usuário.

**Exemplo:** `GET /conta/minhas-contas/1`

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "nomeConta": "Conta Corrente Nubank",
    "saldo": 1500.00,
    "usuario": { "id": 1, "nome": "João da Silva" },
    "instituicaoFinanceira": { "id": 1, "nome": "Nubank", "codigoBacen": "260" }
  },
  {
    "id": 2,
    "nomeConta": "Poupança Itaú",
    "saldo": 3200.50,
    "usuario": { "id": 1, "nome": "João da Silva" },
    "instituicaoFinanceira": { "id": 2, "nome": "Itaú Unibanco", "codigoBacen": "341" }
  }
]
```

---

### `PUT /conta/{id}`
Atualiza os dados de uma conta. Não é possível alterar o dono da conta.

**Exemplo:** `PUT /conta/1`

**Request body:**
```json
{
  "nomeConta": "Conta PJ Nubank",
  "saldo": 2000.00,
  "idBank": 1
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `nomeConta` | String | ✅ | Novo nome da conta |
| `saldo` | Number | ✅ | Novo saldo (≥ 0) |
| `idBank` | Long | ✅ | ID da instituição financeira |

**Response `200 OK`:**
```json
{
  "id": 1,
  "nomeConta": "Conta PJ Nubank",
  "saldo": 2000.00,
  "usuario": { "id": 1, "nome": "João da Silva" },
  "instituicaoFinanceira": { "id": 1, "nome": "Nubank", "codigoBacen": "260" }
}
```

---

### `DELETE /conta/{id}`
Remove uma conta.

**Exemplo:** `DELETE /conta/1`

**Response `204 No Content`** *(sem corpo)*

---

## 🏷️ Endpoints — Categoria

Base URL: `/categorias`

> Categorias classificam as transações. O tipo é salvo em maiúsculas automaticamente (ex.: `alimentacao` → `ALIMENTACAO`).

---

### `POST /categorias/nova`
Cadastra uma nova categoria.

**Request body:**
```json
{
  "nome": "Alimentação",
  "tipo": "despesa"
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `nome` | String | ✅ | Nome da categoria (deve ser único) |
| `tipo` | String | ✅ | Tipo da categoria (salvo em maiúsculas) |

**Exemplos de tipos sugeridos:** `DESPESA`, `RECEITA`, `INVESTIMENTO`, `LAZER`, `SAUDE`, `EDUCACAO`

**Response `201 Created`:**
```json
{
  "id": 1,
  "nome": "Alimentação",
  "tipo": "DESPESA"
}
```

**Erros possíveis:**
```json
{ "message": "Já existe uma categoria com esse nome.", "status": "BAD_REQUEST" }
```

---

### `GET /categorias/listar`
Lista todas as categorias cadastradas.

**Response `200 OK`:**
```json
[
  { "id": 1, "nome": "Alimentação", "tipo": "DESPESA" },
  { "id": 2, "nome": "Salário", "tipo": "RECEITA" },
  { "id": 3, "nome": "Academia", "tipo": "SAUDE" }
]
```

---

### `GET /categorias/{id}`
Busca uma categoria pelo ID.

**Exemplo:** `GET /categorias/1`

**Response `200 OK`:**
```json
{
  "id": 1,
  "nome": "Alimentação",
  "tipo": "DESPESA"
}
```

**Erros possíveis:**
```json
{ "message": "Categoria não encontrada com o ID: 1", "status": "NOT_FOUND" }
```

---

### `PUT /categorias/{id}`
Atualiza uma categoria.

**Exemplo:** `PUT /categorias/1`

**Request body:**
```json
{
  "nome": "Alimentação e Bebidas",
  "tipo": "despesa"
}
```

**Response `200 OK`:**
```json
{
  "id": 1,
  "nome": "Alimentação e Bebidas",
  "tipo": "DESPESA"
}
```

---

### `DELETE /categorias/{id}`
Remove uma categoria.

**Exemplo:** `DELETE /categorias/1`

**Response `204 No Content`** *(sem corpo)*

---

## 💰 Endpoints — Transação

Base URL: `/transacao`

> A transação vincula uma movimentação financeira a uma conta e a uma categoria. A data deve estar no formato `dd/MM/yyyy`.

---

### `POST /transacao/nova`
Registra uma nova transação.

**Request body:**
```json
{
  "descricao": "Almoço no restaurante",
  "valor": 45.90,
  "data": "24/05/2025",
  "origem": "DEBITO",
  "idConta": 1,
  "idCategoria": 1
}
```

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `descricao` | String | ✅ | Descrição da transação (até 100 caracteres) |
| `valor` | Number | ✅ | Valor da transação (≥ 0) |
| `data` | String | ✅ | Data no formato `dd/MM/yyyy` |
| `origem` | String | ✅ | Origem do pagamento (até 20 caracteres) |
| `idConta` | Long | ✅ | ID da conta vinculada |
| `idCategoria` | Long | ✅ | ID da categoria |

**Exemplos de `origem`:** `DEBITO`, `CREDITO`, `PIX`, `DINHEIRO`, `TRANSFERENCIA`

**Response `201 Created`:**
```json
{
  "id": 1,
  "descricao": "Almoço no restaurante",
  "valor": 45.90,
  "data": "24/05/2025",
  "origem": "DEBITO",
  "conta": {
    "id": 1,
    "nomeConta": "Conta Corrente Nubank",
    "saldo": 1500.00,
    "usuario": { "id": 1, "nome": "João da Silva" },
    "instituicaoFinanceira": { "id": 1, "nome": "Nubank", "codigoBacen": "260" }
  },
  "categoria": {
    "id": 1,
    "nome": "Alimentação",
    "tipo": "DESPESA"
  }
}
```

**Erros possíveis:**
```json
{ "message": "Conta não encontrada com ID: 1", "status": "NOT_FOUND" }
{ "message": "Categoria não encontrada com ID: 1", "status": "NOT_FOUND" }
{ "message": "Data de transação inválida. Use o formato dd/MM/yyyy.", "status": "BAD_REQUEST" }
{ "message": "O valor da transação deve ser positivo ou zero.", "status": "BAD_REQUEST" }
```

---

### `GET /transacao/conta/{idConta}`
Lista todas as transações de uma conta.

**Exemplo:** `GET /transacao/conta/1`

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "descricao": "Almoço no restaurante",
    "valor": 45.90,
    "data": "24/05/2025",
    "origem": "DEBITO",
    "conta": { "id": 1, "nomeConta": "Conta Corrente Nubank", "saldo": 1500.00, "usuario": { "id": 1, "nome": "João da Silva" }, "instituicaoFinanceira": { "id": 1, "nome": "Nubank", "codigoBacen": "260" } },
    "categoria": { "id": 1, "nome": "Alimentação", "tipo": "DESPESA" }
  },
  {
    "id": 2,
    "descricao": "Salário maio",
    "valor": 4500.00,
    "data": "05/05/2025",
    "origem": "PIX",
    "conta": { "id": 1, "nomeConta": "Conta Corrente Nubank", "saldo": 1500.00, "usuario": { "id": 1, "nome": "João da Silva" }, "instituicaoFinanceira": { "id": 1, "nome": "Nubank", "codigoBacen": "260" } },
    "categoria": { "id": 2, "nome": "Salário", "tipo": "RECEITA" }
  }
]
```

---

### `GET /transacao/{id}`
Busca uma transação pelo ID.

**Exemplo:** `GET /transacao/1`

**Response `200 OK`:**
```json
{
  "id": 1,
  "descricao": "Almoço no restaurante",
  "valor": 45.90,
  "data": "24/05/2025",
  "origem": "DEBITO",
  "conta": {
    "id": 1,
    "nomeConta": "Conta Corrente Nubank",
    "saldo": 1500.00,
    "usuario": { "id": 1, "nome": "João da Silva" },
    "instituicaoFinanceira": { "id": 1, "nome": "Nubank", "codigoBacen": "260" }
  },
  "categoria": {
    "id": 1,
    "nome": "Alimentação",
    "tipo": "DESPESA"
  }
}
```

**Erros possíveis:**
```json
{ "message": "Transação não encontrada com ID: 1", "status": "NOT_FOUND" }
```

---

### `PUT /transacao/{id}`
Atualiza uma transação existente.

**Exemplo:** `PUT /transacao/1`

**Request body:**
```json
{
  "descricao": "Almoço + sobremesa",
  "valor": 62.50,
  "data": "24/05/2025",
  "origem": "CREDITO",
  "idConta": 1,
  "idCategoria": 1
}
```

**Response `200 OK`:**
```json
{
  "id": 1,
  "descricao": "Almoço + sobremesa",
  "valor": 62.50,
  "data": "24/05/2025",
  "origem": "CREDITO",
  "conta": {
    "id": 1,
    "nomeConta": "Conta Corrente Nubank",
    "saldo": 1500.00,
    "usuario": { "id": 1, "nome": "João da Silva" },
    "instituicaoFinanceira": { "id": 1, "nome": "Nubank", "codigoBacen": "260" }
  },
  "categoria": {
    "id": 1,
    "nome": "Alimentação",
    "tipo": "DESPESA"
  }
}
```

---

### `DELETE /transacao/{id}`
Remove uma transação.

**Exemplo:** `DELETE /transacao/1`

**Response `204 No Content`** *(sem corpo)*

**Erros possíveis:**
```json
{ "message": "Transação não encontrada com ID: 1", "status": "NOT_FOUND" }
```

---

## 📊 Resumo de todos os endpoints

| Método | Endpoint | Descrição | Status de sucesso |
|---|---|---|---|
| `POST` | `/inst-financeira/cadastrar` | Cadastrar instituição financeira | `201` |
| `GET` | `/inst-financeira/listar` | Listar todas as instituições | `200` |
| `GET` | `/inst-financeira/{id}` | Buscar instituição por ID | `200` |
| `PUT` | `/inst-financeira/{id}` | Atualizar instituição | `200` |
| `DELETE` | `/inst-financeira/{id}` | Remover instituição | `204` |
| `POST` | `/usuario/cadastrar` | Cadastrar usuário | `201` |
| `GET` | `/usuario/{id}` | Buscar usuário por ID | `200` |
| `PUT` | `/usuario/{id}` | Atualizar usuário | `200` |
| `DELETE` | `/usuario/{id}` | Remover usuário | `204` |
| `POST` | `/conta/cadastrar` | Cadastrar conta | `201` |
| `GET` | `/conta/{id}` | Buscar conta por ID | `200` |
| `GET` | `/conta/minhas-contas/{idUsuario}` | Listar contas do usuário | `200` |
| `PUT` | `/conta/{id}` | Atualizar conta | `200` |
| `DELETE` | `/conta/{id}` | Remover conta | `204` |
| `POST` | `/categorias/nova` | Cadastrar categoria | `201` |
| `GET` | `/categorias/listar` | Listar todas as categorias | `200` |
| `GET` | `/categorias/{id}` | Buscar categoria por ID | `200` |
| `PUT` | `/categorias/{id}` | Atualizar categoria | `200` |
| `DELETE` | `/categorias/{id}` | Remover categoria | `204` |
| `POST` | `/transacao/nova` | Registrar transação | `201` |
| `GET` | `/transacao/conta/{idConta}` | Listar transações da conta | `200` |
| `GET` | `/transacao/{id}` | Buscar transação por ID | `200` |
| `PUT` | `/transacao/{id}` | Atualizar transação | `200` |
| `DELETE` | `/transacao/{id}` | Remover transação | `204` |

---

## 👨‍💻 Autor

Desenvolvido como projeto acadêmico na **FIAP** — Turma de Análise e Desenvolvimento de Sistemas.