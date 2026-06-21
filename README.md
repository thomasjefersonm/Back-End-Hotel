# 🏨 Sistema de Reserva para Hotéis - "Descanso Perfeito"

API REST desenvolvida em Java com Spring Boot para o gerenciamento inteligente de hóspedes, quartos e reservas da rede de hotéis "Descanso Perfeito". 
Este projeto foi elaborado seguindo a metodologia de Atividade Baseada em Problema (ABP) como avaliação final da disciplina de Back-End 
(4ª Fase - Engenharia de Software - UNISATC 2026.1).

---

## 👥 Estrutura da Equipe e Divisão de Tarefas

Para garantir a transparência no desenvolvimento e atender rigorosamente aos critérios de avaliação individual, o escopo foi dividido de forma 
igualitária por entidade entre os 3 membros da equipe:

* Integrante 1: Responsável pela infraestrutura do banco de dados em memória H2, criação da classe abstrata de persistência para herança de ID
  e desenvolvimento do CRUD completo da entidade `Hospede` (Model, Repository, Service e Controller).
* Integrante 2: Responsável pela configuração de políticas de CORS, desenvolvimento do CRUD completo da entidade `Quarto` e implementação dos filtros avançados,
  paginação e ordenação na busca de quartos disponíveis.
* Integrante 3: Responsável pelo tratamento global de exceções da API (`@ControllerAdvice`), desenvolvimento do CRUD completo da entidade `Reserva` e
  implementação da regra de negócio crítica contra *overbooking* (validação de choque de datas para o mesmo quarto).

---

## 🛠️ Tecnologias Utilizadas

* Linguagem: Java 25
* Framework: Spring Boot 4.0.6
* Gerenciador de Dependências: Gradle
* Persistência de Dados: Spring Data JPA / Hibernate
* Banco de Dados: H2 Database (Banco em memória executado em tempo de execução)

---

## 🚀 Como Executar o Projeto Localmente

- Pré-requisitos
* Java JDK 25 instalado no sistema.
* IDE de sua preferência (Recomendado: IntelliJ IDEA).

- Passos para Execução
1. Abra a IDE IntelliJ e selecione a opção "Open" (Abrir).
2. Localize a pasta raiz do projeto (`descansoperfeito`) e clique em OK para importar como um projeto Gradle.
3. Aguarde a IDE indexar os arquivos e baixar as dependências descritas no arquivo `build.gradle` (caso necessário, clique no ícone do elefante do Gradle para sincronizar).
4. Localize a classe principal do sistema em:
   `src/main/java/com/example/descansoperfeito/DescansoperfeitoApplication.java`
5. Clique com o botão direito do mouse sobre ela e selecione "Run 'DescansoperfeitoApplication'".
6. A API será inicializada e estará disponível para testes no endereço local: `http://localhost:8080`

---

## 🗺️ Documentação das Rotas da API (Endpoints)

### 👤 Módulo de Hóspedes (Desenvolvido pelo Integrante 1)
Este módulo gerencia todo o ciclo de vida dos clientes na rede de hotéis, aplicando conceitos fundamentais de Orientação a Objetos 
(como encapsulamento rigoroso e herança a partir de uma classe base).

* **Listar todos os hóspedes**
    * **Rota:** `GET /api/hospedes`
    * **Descrição:** Retorna a listagem completa de hóspedes cadastrados no sistema.
    * **Status de Sucesso:** `200 OK`

* **Buscar hóspede por ID**
    * **Rota:** `GET /api/hospedes/{id}`
    * **Descrição:** Busca um hóspede específico baseado no ID numérico fornecido na URL.
    * **Status de Sucesso:** `200 OK`
    * **Status de Erro:** `404 Not Found` (Caso o ID informado não exista no banco)

* **Cadastrar um novo hóspede**
    * **Rota:** `POST /api/hospedes`
    * **Descrição:** Registra um novo cliente no banco de dados em memória.
    * **Corpo da Requisição (JSON):**
        ```json
        {
          "nome": "João da Silva",
          "email": "joao@email.com",
          "telefone": "(48) 99999-9999",
          "cpf": "123.456.789-00"
        }
        ```
    * **Status de Sucesso:** `201 Created`
    * **Status de Erro:** `400 Bad Request` (Caso ocorra erro na validação dos dados enviados)

* **Atualizar dados de um hóspede**
    * **Rota:** `PUT /api/hospedes/{id}`
    * **Descrição:** Altera as informações de um hóspede existente a partir do seu ID.
    * **Corpo da Requisição (JSON):** Dados atualizados do cliente.
    * **Status de Sucesso:** `200 OK`
    * **Status de Erro:** `404 Not Found` ou `400 Bad Request`

* **Remover um hóspede**
    * **Rota:** `DELETE /api/hospedes/{id}`
    * **Descrição:** Exclui permanentemente o registro do hóspede do banco de dados.
    * **Status de Sucesso:** `204 No Content`
    * **Status de Erro:** `404 Not Found`

---

# 🛏️ Módulo de Quartos - Descanso Perfeito

Desenvolvido pelo **Integrante 2** - Bruno Goulart (Baah151)

---

## 📋 Descrição

Este módulo é responsável pelo gerenciamento completo da entidade **Quarto** no sistema de reserva de hotéis "Descanso Perfeito". Implementa todas as operações CRUD (Create, Read, Update, Delete), além de filtros avançados, paginação e ordenação para buscas inteligentes de quartos disponíveis.

---

## 🎯 Funcionalidades Implementadas

✅ **CRUD Completo** - Criar, ler, atualizar e deletar quartos  
✅ **Filtros Avançados** - Buscar por categoria, disponibilidade e combinações  
✅ **Paginação** - Suporte a requisições paginadas com tamanho customizável  
✅ **Ordenação** - Ordenar resultados por qualquer atributo (ascendente/descendente)  
✅ **CORS Configurado** - Permite requisições de qualquer origem  
✅ **API RESTful** - 10 endpoints implementados e testados  
✅ **Persistência em Banco de Dados** - H2 em memória com Hibernate  

---

## 📁 Arquivos Desenvolvidos

```
src/main/java/com/example/descansoperfeito/
├── model/
│   └── Quarto.java                      # Entidade com atributos e categorias
├── repository/
│   └── QuartoRepository.java             # Interface JpaRepository com queries customizadas
├── service/
│   └── QuartoService.java                # Lógica de negócio CRUD e filtros
├── controller/
│   └── QuartoController.java             # 10 Endpoints REST
└── config/
    └── CorsConfig.java                  # Configuração CORS global
```

---

## 📝 Entidade Quarto

### Modelo de Dados

```java
@Entity
@Table(name = "quarto")
public class Quarto extends EntidadeBase {
    
    @Column(nullable = false)
    private Integer numero;                    // Número identificador
    
    @Enumerated(EnumType.STRING)
    private CategoriaQuarto categoria;         // Tipo do quarto
    
    @Column(nullable = false)
    private Double preco;                      // Valor da diária
    
    @Column(nullable = false)
    private Boolean disponibilidade;           // Status de disponibilidade
    
    public enum CategoriaQuarto {
        SIMPLES,          // Quarto básico com cama simples
        DUPLO,            // Quarto com cama de casal
        SUITE,            // Quarto de luxo com área de estar
        PRESIDENCIAL      // Quarto premium com todas as comodidades
    }
}
```

---

## 🛣️ Endpoints da API

### Base URL: `http://localhost:8080/api/quartos`

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| **GET** | `/` | Listar todos os quartos | 200 |
| **GET** | `/{id}` | Buscar quarto por ID | 200 |
| **GET** | `/numero/{numero}` | Buscar quarto por número | 200 |
| **POST** | `/` | Criar novo quarto | 201 |
| **PUT** | `/{id}` | Atualizar quarto existente | 200 |
| **DELETE** | `/{id}` | Deletar quarto | 204 |
| **GET** | `/filtrados/buscar` | ⭐ Filtro avançado (categoria, disponibilidade) | 200 |
| **GET** | `/disponiveis` | Listar quartos disponíveis (paginado) | 200 |
| **GET** | `/categoria/{categoria}` | Filtrar por categoria (paginado) | 200 |
| **GET** | `/estatisticas` | Retornar contagem de quartos | 200 |

---

## 📚 Exemplos de Requisições

### 1️⃣ Criar um Quarto (POST)

**Requisição:**
```bash
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 101,
    "categoria": "DUPLO",
    "preco": 150.00,
    "disponibilidade": true
  }'
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "numero": 101,
  "categoria": "DUPLO",
  "preco": 150.0,
  "disponibilidade": true
}
```

---

### 2️⃣ Listar Todos os Quartos (GET)

**Requisição:**
```bash
curl http://localhost:8080/api/quartos
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "numero": 101,
    "categoria": "DUPLO",
    "preco": 150.0,
    "disponibilidade": true
  },
  {
    "id": 2,
    "numero": 102,
    "categoria": "SUITE",
    "preco": 250.0,
    "disponibilidade": false
  }
]
```

---

### 3️⃣ Filtro Avançado com Paginação e Ordenação (GET)

**Requisição:**
```bash
curl "http://localhost:8080/api/quartos/filtrados/buscar?categoria=DUPLO&disponibilidade=true&page=0&size=10&sort=preco,asc"
```

**Resposta (200 OK):**
```json
{
  "conteudo": [
    {
      "id": 1,
      "numero": 101,
      "categoria": "DUPLO",
      "preco": 150.0,
      "disponibilidade": true
    }
  ],
  "paginaAtual": 0,
  "totalElementos": 1,
  "totalPaginas": 1,
  "ehUltimaPagina": true,
  "totalPorPagina": 10,
  "ehPrimeiraPagina": true
}
```

---

### 4️⃣ Buscar Quartos por Categoria (GET)

**Requisição:**
```bash
curl "http://localhost:8080/api/quartos/categoria/DUPLO?page=0&size=5"
```

**Resposta (200 OK):**
```json
{
  "conteudo": [
    {
      "id": 1,
      "numero": 101,
      "categoria": "DUPLO",
      "preco": 150.0,
      "disponibilidade": true
    }
  ],
  "paginaAtual": 0,
  "totalElementos": 1,
  "categoria": "Duplo",
  "totalPaginas": 1,
  "totalPorPagina": 5
}
```

---

### 5️⃣ Atualizar um Quarto (PUT)

**Requisição:**
```bash
curl -X PUT http://localhost:8080/api/quartos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 101,
    "categoria": "SUITE",
    "preco": 250.00,
    "disponibilidade": false
  }'
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "numero": 101,
  "categoria": "SUITE",
  "preco": 250.0,
  "disponibilidade": false
}
```

---

### 6️⃣ Deletar um Quarto (DELETE)

**Requisição:**
```bash
curl -X DELETE http://localhost:8080/api/quartos/1
```

**Resposta:** `204 No Content`

---

## 🔧 Parâmetros de Paginação

| Parâmetro | Tipo | Descrição | Exemplo |
|-----------|------|-----------|---------|
| `page` | Integer | Número da página (começa em 0) | `?page=0` |
| `size` | Integer | Registros por página | `?size=10` |
| `sort` | String | Campo e direção (asc/desc) | `?sort=preco,asc` |

---

## ⚙️ Configuração CORS

A configuração CORS permite requisições de qualquer origem:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .maxAge(3600);
    }
}
```

---

## 🧪 Testes Realizados

✅ **POST** - Criar quarto (HTTP 201)  
✅ **GET All** - Listar todos (HTTP 200)  
✅ **GET by ID** - Buscar específico (HTTP 200)  
✅ **GET by Category** - Filtro por categoria (HTTP 200)  
✅ **GET Advanced Filter** - Filtro + paginação + ordenação (HTTP 200)  
✅ **PUT** - Atualizar (HTTP 200)  
✅ **DELETE** - Deletar (HTTP 204)  

---

## 💡 Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| **Spring Boot** | 4.0.6 | Framework web |
| **Spring Data JPA** | 4.0.5 | Persistência |
| **Hibernate** | 7.2.12 | ORM |
| **H2 Database** | 2.4.240 | Banco em memória |
| **Java** | 25.0.3 | Linguagem |
| **Gradle** | 9.5.1 | Build |

---

## 📌 Notas Importantes

- O banco de dados H2 é executado **em memória**, portanto os dados são perdidos quando a aplicação é encerrada.
- A paginação começa no índice **0** (primeira página).
- Os filtros aceitam valores `null` para parâmetros opcionais (ex: `categoria=null`).
- A ordenação suporta múltiplos campos (ex: `sort=categoria,asc&sort=preco,desc`).

---

## 👨‍💻 Desenvolvedor

**Bruno Goulart (Baah151)**  
Email: brughisidasilva@gmail.com  
GitHub: [@thomasjefersonm](https://github.com/thomasjefersonm)

---

**✨ Implementação concluída em 20 de Junho de 2026**

### 📅 Módulo de Reservas (Desenvolvido pelo Integrante 3)
Este módulo é o coração do sistema, responsável por gerenciar a estada dos hóspedes e garantir a integridade dos dados através de validações críticas contra overbooking.

### 🗺️ Documentação das Rotas da API (Endpoints)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **GET** | `/api/reservas` | Lista todas as reservas com paginação |
| **POST** | `/api/reservas` | Cria uma nova reserva com validação de overbooking |
| **PUT** | `/api/reservas/{id}` | Atualiza os dados de uma reserva existente |
| **DELETE** | `/api/reservas/{id}` | Remove permanentemente uma reserva |

Listar todas as reservas
Rota: GET /reservas

Descrição: Retorna a lista de reservas cadastradas, com suporte a paginação e ordenação automática por data de check-in.

Status de Sucesso: 200 OK

Criar uma nova reserva
Rota: POST /reservas

Descrição: Registra uma nova reserva. O sistema valida automaticamente se o quarto já está ocupado no período solicitado, impedindo conflitos de datas (overbooking).

Corpo da Requisição (JSON):

 ```JSON

{
  "hospedeId": 1,
  "quartoId": 5,
  "dataCheckin": "2026-07-01",
  "dataCheckout": "2026-07-05"
}

Status de Sucesso: 201 Created

Status de Erro: 400 Bad Request (Caso o quarto esteja ocupado ou os dados estejam inválidos)

Atualizar reserva
Rota: PUT /reservas/{id}

Descrição: Altera as datas de uma reserva existente, revalidando a disponibilidade do quarto.

Corpo da Requisição (JSON):

{
  "dataCheckin": "2026-07-02",
  "dataCheckout": "2026-07-06"
}

Status de Sucesso: 200 OK

Status de Erro: 400 Bad Request ou 404 Not Found

Remover reserva
Rota: DELETE /reservas/{id}

Descrição: Cancela e remove uma reserva do sistema.

Status de Sucesso: 204 No Content

Status de Erro: 404 Not Found (Caso a reserva não exista)
```

*Documentação oficial desenvolvida em conformidade com as diretrizes descritas no trabalho da Atividade Baseada em Problemas (ABP) - Professor Juliano Almeida - UNISATC, 2026.*
