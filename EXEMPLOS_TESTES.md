# Exemplos de Testes - API de Quartos

## 🔗 Base URL
```
http://localhost:8080
```

---

## ✅ CRUD Básico

### 1. CREATE - Criar um novo quarto
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
**Status esperado**: 201 Created

---

### 2. READ - Listar todos os quartos
```bash
curl -X GET http://localhost:8080/api/quartos
```
**Status esperado**: 200 OK

---

### 3. READ - Buscar quarto por ID
```bash
curl -X GET http://localhost:8080/api/quartos/1
```
**Status esperado**: 200 OK

---

### 4. READ - Buscar quarto por número
```bash
curl -X GET http://localhost:8080/api/quartos/numero/101
```
**Status esperado**: 200 OK

---

### 5. UPDATE - Atualizar quarto
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
**Status esperado**: 200 OK

---

### 6. DELETE - Deletar quarto
```bash
curl -X DELETE http://localhost:8080/api/quartos/1
```
**Status esperado**: 204 No Content

---

## 🎯 MISSÃO ESPECIAL - Filtros, Paginação e Ordenação

### 1. Listar com paginação padrão (página 0, 10 itens)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar"
```

---

### 2. Listar primeira página com 5 itens por página
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?page=0&size=5"
```

---

### 3. Listar segunda página com 10 itens
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?page=1&size=10"
```

---

### 4. Filtrar apenas quartos SIMPLES
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=SIMPLES"
```

---

### 5. Filtrar apenas quartos DUPLO
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=DUPLO"
```

---

### 6. Filtrar apenas quartos SUITE
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=SUITE"
```

---

### 7. Filtrar apenas quartos PRESIDENCIAL
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=PRESIDENCIAL"
```

---

### 8. Filtrar apenas quartos disponíveis
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?disponibilidade=true"
```

---

### 9. Filtrar apenas quartos indisponíveis
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?disponibilidade=false"
```

---

### 10. Ordenar por número (crescente)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?sort=numero,asc"
```

---

### 11. Ordenar por número (decrescente)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?sort=numero,desc"
```

---

### 12. Ordenar por preço (crescente)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?sort=preco,asc"
```

---

### 13. Ordenar por preço (decrescente)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?sort=preco,desc"
```

---

### 14. Ordenar por categoria (crescente)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?sort=categoria,asc"
```

---

### 15. Filtro Combinado: Quartos DUPLO disponíveis
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=DUPLO&disponibilidade=true"
```

---

### 16. Filtro Combinado: Quartos SUITE indisponíveis
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=SUITE&disponibilidade=false"
```

---

### 17. Filtro + Ordenação: DUPLO disponível, ordenado por preço (asc)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=DUPLO&disponibilidade=true&sort=preco,asc"
```

---

### 18. Filtro + Ordenação: SUITE indisponível, ordenado por número (desc)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=SUITE&disponibilidade=false&sort=numero,desc"
```

---

### 19. Filtro + Paginação: SIMPLES, página 0, 5 itens
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=SIMPLES&page=0&size=5"
```

---

### 20. Filtro + Paginação + Ordenação: PRESIDENCIAL disponível, página 0, 3 itens, ordenado por preço (desc)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=PRESIDENCIAL&disponibilidade=true&page=0&size=3&sort=preco,desc"
```

---

### 21. Todos os indisponíveis, paginados (2 itens por página)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?disponibilidade=false&page=0&size=2&sort=numero,asc"
```

---

### 22. Todos os disponíveis, ordenados por número (crescente)
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?disponibilidade=true&sort=numero,asc&page=0&size=10"
```

---

## 📊 Endpoints Auxiliares

### Listar quartos disponíveis com paginação
```bash
curl -X GET "http://localhost:8080/api/quartos/disponiveis?page=0&size=10&sort=numero,asc"
```

---

### Listar quartos de uma categoria específica
```bash
curl -X GET "http://localhost:8080/api/quartos/categoria/SUITE?page=0&size=10&sort=preco,asc"
```

---

### Obter estatísticas gerais
```bash
curl -X GET http://localhost:8080/api/quartos/estatisticas
```

**Resposta esperada**:
```json
{
  "totalQuartos": 50,
  "quartosDiponiveis": 35,
  "quartosIndisponíveis": 15
}
```

---

## 📋 Dados de Teste - INSERT

Para popular o banco com dados de teste, execute os seguintes POSTs:

```bash
# Quarto 1
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{"numero": 101, "categoria": "SIMPLES", "preco": 150.00, "disponibilidade": true}'

# Quarto 2
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{"numero": 102, "categoria": "DUPLO", "preco": 250.00, "disponibilidade": true}'

# Quarto 3
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{"numero": 103, "categoria": "SUITE", "preco": 450.00, "disponibilidade": false}'

# Quarto 4
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{"numero": 104, "categoria": "PRESIDENCIAL", "preco": 750.00, "disponibilidade": true}'

# Quarto 5
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{"numero": 201, "categoria": "DUPLO", "preco": 280.00, "disponibilidade": false}'

# Quarto 6
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{"numero": 202, "categoria": "SIMPLES", "preco": 180.00, "disponibilidade": true}'

# Quarto 7
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{"numero": 203, "categoria": "SUITE", "preco": 500.00, "disponibilidade": true}'

# Quarto 8
curl -X POST http://localhost:8080/api/quartos \
  -H "Content-Type: application/json" \
  -d '{"numero": 204, "categoria": "PRESIDENCIAL", "preco": 800.00, "disponibilidade": false}'
```

---

## 🧪 Resposta Exemplo - Filtros com Paginação

Ao fazer a requisição:
```bash
curl -X GET "http://localhost:8080/api/quartos/filtrados/buscar?categoria=DUPLO&disponibilidade=true&sort=preco,asc&page=0&size=10"
```

Você recebe:
```json
{
  "conteudo": [
    {
      "id": 2,
      "numero": 102,
      "categoria": "DUPLO",
      "preco": 250.00,
      "disponibilidade": true
    },
    {
      "id": 5,
      "numero": 201,
      "categoria": "DUPLO",
      "preco": 280.00,
      "disponibilidade": true
    }
  ],
  "paginaAtual": 0,
  "totalPorPagina": 10,
  "totalElementos": 2,
  "totalPaginas": 1,
  "ehUltimaPagina": true,
  "ehPrimeiraPagina": true
}
```

---

## 🔐 Validações HTTP

| Cenário | Status Esperado |
|---------|-----------------|
| GET válido | 200 OK |
| GET inexistente | 404 Not Found |
| POST válido | 201 Created |
| POST inválido | 400 Bad Request |
| PUT válido | 200 OK |
| PUT inexistente | 404 Not Found |
| DELETE válido | 204 No Content |
| DELETE inexistente | 404 Not Found |

---

## 💡 Dicas

1. Use `curl` para testes rápidos
2. Use Postman ou Insomnia para interface gráfica
3. Sempre verifique o banco com `/h2-console`
4. Os filtros são case-sensitive para ENUM
5. As ordenações reconhecem: `id`, `numero`, `categoria`, `preco`, `disponibilidade`
6. Direções de ordenação: `asc` ou `desc` (padrão: asc)

---

**Desenvolvido por**: Integrante 2
**Data**: 2024
