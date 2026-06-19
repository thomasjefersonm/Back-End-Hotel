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

### 🛏️ Módulo de Quartos (Desenvolvido pelo Integrante 2)
*Endpoints a serem mapeados e implementados pelo Integrante 2 (Ex: `GET /api/quartos`, `POST /api/quartos`, paginação e filtros).*

### 📅 Módulo de Reservas (Desenvolvido pelo Integrante 3)
*Endpoints a serem mapeados e implementados pelo Integrante 3 (Ex: `POST /api/reservas`, validação de datas e tratamento global de exceções com `@ControllerAdvice`).*

Este módulo é o coração do sistema, responsável por gerenciar a estada dos hóspedes e garantir a integridade dos dados através de validações críticas contra overbooking.

Listar todas as reservas
Rota: GET /reservas

Descrição: Retorna a lista de reservas cadastradas, com suporte a paginação e ordenação automática por data de check-in.

Status de Sucesso: 200 OK

Criar uma nova reserva
Rota: POST /reservas

Descrição: Registra uma nova reserva. O sistema valida automaticamente se o quarto já está ocupado no período solicitado, impedindo conflitos de datas (overbooking).

Corpo da Requisição (JSON):

JSON

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

JSON

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
---
*Documentação oficial desenvolvida em conformidade com as diretrizes descritas no trabalho da Atividade Baseada em Problemas (ABP) - Professor Juliano Almeida - SATC, 2026.*
