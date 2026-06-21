# Documentação da API de Quartos - Hotel Descanso Perfeito

## 📋 Visão Geral

Esta é a implementação da entidade **Quarto** para o sistema de gerenciamento de hotel.

### Atributos do Quarto
- **ID**: Identificador único (herdado de EntidadeBase)
- **Número**: Número do quarto (Integer)
- **Categoria**: SIMPLES, DUPLO, SUITE ou PRESIDENCIAL (Enum)
- **Preço**: Valor em reais (Double)
- **Disponibilidade**: true (disponível) ou false (ocupado) (Boolean)

---

## 🔧 Estrutura Implementada

### 1. Model (Quarto.java)
- Entidade JPA que herda de `EntidadeBase`
- Atributos conforme especificação
- Enum `CategoriaQuarto` com 4 categorias
- Construtores, getters e setters

### 2. Repository (QuartoRepository.java)
Interface estendendo `JpaRepository<Quarto, Long>` com métodos especiais:
- `findByDisponibilidade()` - filtrar por disponibilidade
- `findByCategoria()` - filtrar por categoria
- `findByCategoriaAndDisponibilidade()` - filtro combinado
- `findByPreçoBetween()` - filtrar por faixa de preço
- `findByNumero()` - buscar por número do quarto
- `buscarComFiltros()` - query customizada com filtros opcionais

### 3. Service (QuartoService.java)
Implementa a lógica de negócio:
- CRUD completo (criar, ler, atualizar, deletar)
- `listarComFiltros()` - método principal para filtros + paginação
- `listarPorDisponibilidade()` - filtrar apenas disponíveis
- `listarPorCategoria()` - filtrar apenas uma categoria
- `contarDisponíveis()` - contar quartos livres
- `contarIndisponíveis()` - contar quartos ocupados

### 4. Controller (QuartoController.java)
Endpoints REST com CORS ativado:

#### Endpoints CRUD Básicos
```
GET    /api/quartos                    - Listar todos (sem paginação)
GET    /api/quartos/{id}               - Buscar por ID
GET    /api/quartos/numero/{numero}    - Buscar por número
POST   /api/quartos                    - Criar novo quarto
PUT    /api/quartos/{id}               - Atualizar quarto
DELETE /api/quartos/{id}               - Deletar quarto
```

#### 🎯 Endpoints Especiais (Missão Especial - 15%)

**GET /api/quartos/filtrados/buscar**
Endpoint com Paginação, Ordenação e Filtros combinados.

**Parâmetros de Query:**
- `categoria` - Filtrar por categoria (SIMPLES, DUPLO, SUITE, PRESIDENCIAL) - OPCIONAL
- `disponibilidade` - Filtrar por disponibilidade (true/false) - OPCIONAL
- `page` - Número da página (padrão: 0) - OPCIONAL
- `size` - Quantidade de registros por página (padrão: 10) - OPCIONAL
- `sort` - Campo e direção de ordenação (padrão: id,asc) - OPCIONAL

**Campos disponíveis para ordenação:**
- `id` - por ID
- `numero` - por número do quarto
- `categoria` - por categoria
- `preco` - por preço
- `disponibilidade` - por disponibilidade

**Exemplos de Uso:**

1. **Listar todos com paginação padrão:**
```
GET /api/quartos/filtrados/buscar
```

2. **Listar com página específica:**
```
GET /api/quartos/filtrados/buscar?page=0&size=5
```

3. **Listar apenas quartos DUPLO:**
```
GET /api/quartos/filtrados/buscar?categoria=DUPLO
```

4. **Listar apenas quartos disponíveis:**
```
GET /api/quartos/filtrados/buscar?disponibilidade=true
```

5. **Filtro combinado: SUITE disponível, ordenado por preço crescente:**
```
GET /api/quartos/filtrados/buscar?categoria=SUITE&disponibilidade=true&sort=preco,asc
```

6. **Filtro combinado com paginação:**
```
GET /api/quartos/filtrados/buscar?categoria=PRESIDENCIAL&disponibilidade=true&page=0&size=10&sort=preco,desc
```

7. **Ordenar por número do quarto (descendente):**
```
GET /api/quartos/filtrados/buscar?sort=numero,desc
```

8. **Listar apenas indisponíveis, 3 por página:**
```
GET /api/quartos/filtrados/buscar?disponibilidade=false&page=0&size=3
```

#### Resposta do Endpoint de Filtros:
```json
{
  "conteudo": [
    {
      "id": 1,
      "numero": 101,
      "categoria": "SIMPLES",
      "preco": 150.00,
      "disponibilidade": true
    },
    {
      "id": 2,
      "numero": 102,
      "categoria": "DUPLO",
      "preco": 250.00,
      "disponibilidade": true
    }
  ],
  "paginaAtual": 0,
  "totalPorPagina": 10,
  "totalElementos": 25,
  "totalPaginas": 3,
  "ehUltimaPagina": false,
  "ehPrimeiraPagina": true
}
```

#### Endpoints Auxiliares

**GET /api/quartos/disponiveis**
Lista apenas quartos disponíveis com paginação.
```
GET /api/quartos/disponiveis?page=0&size=10&sort=numero,asc
```

**GET /api/quartos/categoria/{categoria}**
Lista quartos por categoria específica.
```
GET /api/quartos/categoria/SUITE?page=0&size=10&sort=preco,asc
```

**GET /api/quartos/estatisticas**
Retorna estatísticas gerais dos quartos.
```json
{
  "totalQuartos": 50,
  "quartosDiponiveis": 35,
  "quartosIndisponíveis": 15
}
```

---

## 🌐 Configuração CORS

O CORS está configurado globalmente em `CorsConfig.java`:
- Permite requisições de qualquer origem (`*`)
- Métodos HTTP: GET, POST, PUT, DELETE, OPTIONS
- Headers aceitos: todos (`*`)
- Cache: 3600 segundos

---

## 📝 Exemplo de Uso Completo

### 1. Criar um novo quarto
```bash
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 101,
    "categoria": "DUPLO",
    "preco": 250.00,
    "disponibilidade": true
  }'
```

### 2. Buscar todos os quartos SUITE disponíveis, ordenados por preço
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=SUITE&disponibilidade=true&sort=preco,asc"
```

### 3. Listar apenas quartos indisponíveis, 5 por página
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?disponibilidade=false&page=0&size=5"
```

### 4. Atualizar um quarto
```bash
curl -X PUT http://localhost:8080/api/quartos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 101,
    "categoria": "SUITE",
    "preco": 350.00,
    "disponibilidade": false
  }'
```

### 5. Deletar um quarto
```bash
curl -X DELETE http://localhost:8080/api/quartos/1
```

---

## 🗄️ Banco de Dados

O projeto utiliza H2 em memória. As tabelas são criadas automaticamente pelo Hibernate.

**Tabela QUARTO:**
```sql
CREATE TABLE quarto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    preco DOUBLE NOT NULL,
    disponibilidade BOOLEAN NOT NULL
);
```

---

## ✅ Status HTTP

| Método | Endpoint | Status Sucesso | Erros Possíveis |
|--------|----------|----------------|-----------------|
| GET | /api/quartos | 200 OK | - |
| GET | /api/quartos/{id} | 200 OK | 404 Not Found |
| POST | /api/quartos | 201 Created | 400 Bad Request |
| PUT | /api/quartos/{id} | 200 OK | 404 Not Found, 400 Bad Request |
| DELETE | /api/quartos/{id} | 204 No Content | 404 Not Found |

---

## 🚀 Como Usar no IntelliJ

1. Copie todos os arquivos para o projeto
2. Faça rebuild do projeto (Build > Build Project)
3. Execute a aplicação (Run > Run 'DescansoperfeitoApplication')
4. Acesse a API em `http://localhost:8080`
5. Use Postman ou Insomnia para testar os endpoints

---

## 📊 Exemplo de Dados para Testes

```json
[
  {
    "numero": 101,
    "categoria": "SIMPLES",
    "preco": 150.00,
    "disponibilidade": true
  },
  {
    "numero": 102,
    "categoria": "DUPLO",
    "preco": 250.00,
    "disponibilidade": true
  },
  {
    "numero": 103,
    "categoria": "SUITE",
    "preco": 450.00,
    "disponibilidade": false
  },
  {
    "numero": 104,
    "categoria": "PRESIDENCIAL",
    "preco": 750.00,
    "disponibilidade": true
  },
  {
    "numero": 201,
    "categoria": "DUPLO",
    "preco": 280.00,
    "disponibilidade": false
  }
]
```

---

## 🎯 Resumo da Missão Especial (15%)

A missão especial foi implementada no endpoint `/api/quartos/filtrados/buscar` e inclui:

✅ **Paginação**: Controle de página e tamanho
✅ **Ordenação**: Ordenação customizável por qualquer campo (asc/desc)
✅ **Filtros**: Filtro por categoria E/OU disponibilidade

**Recursos implementados:**
- Query personalizada com filtros opcionais no Repository
- Método `listarComFiltros()` na Service que aplica lógica de negócio
- Endpoint robusto que retorna metadados de paginação
- Suporte completo a ordenação dinâmica
- Validação de parâmetros

---

**Desenvolvido por**: Integrante 2
**Data**: 2024
**Versão**: 1.0
