package com.example.descansoperfeito.controller;

import com.example.descansoperfeito.model.Quarto;
import com.example.descansoperfeito.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quartos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuartoController {

    @Autowired
    private QuartoService service;

    /**
     * GET /api/quartos
     * Listar todos os quartos (sem paginação)
     */
    @GetMapping
    public ResponseEntity<List<Quarto>> listar() {
        List<Quarto> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/quartos/{id}
     * Buscar quarto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarPorId(@PathVariable Long id) {
        try {
            Quarto quarto = service.buscarPorId(id);
            return ResponseEntity.ok(quarto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/quartos/numero/{numero}
     * Buscar quarto por número
     */
    @GetMapping("/numero/{numero}")
    public ResponseEntity<Quarto> buscarPorNumero(@PathVariable Integer numero) {
        try {
            Quarto quarto = service.buscarPorNumero(numero);
            return ResponseEntity.ok(quarto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * POST /api/quartos
     * Criar novo quarto
     */
    @PostMapping
    public ResponseEntity<Quarto> criar(@RequestBody Quarto quarto) {
        try {
            Quarto novoQuarto = service.salvar(quarto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoQuarto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * PUT /api/quartos/{id}
     * Atualizar quarto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizar(@PathVariable Long id, @RequestBody Quarto quarto) {
        try {
            Quarto atualizado = service.atualizar(id, quarto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * DELETE /api/quartos/{id}
     * Deletar quarto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * MISSÃO ESPECIAL: GET /api/quartos/filtrados/buscar
     * Endpoint com Paginação, Ordenação e Filtros
     *
     * Parâmetros de Query:
     * - categoria: filtrar por categoria (SIMPLES, DUPLO, SUITE, PRESIDENCIAL) - opcional
     * - disponibilidade: filtrar por disponibilidade (true/false) - opcional
     * - page: número da página (padrão: 0) - opcional
     * - size: tamanho da página (padrão: 10) - opcional
     * - sort: ordenação (padrão: id,asc) - opcional
     *   Exemplos: "numero,asc" | "preco,desc" | "categoria,asc"
     *
     * Exemplos de uso:
     * GET /api/quartos/filtrados/buscar?page=0&size=5&sort=preco,asc
     * GET /api/quartos/filtrados/buscar?categoria=DUPLO&disponibilidade=true&page=0&size=10
     * GET /api/quartos/filtrados/buscar?disponibilidade=true&sort=numero,asc
     * GET /api/quartos/filtrados/buscar?categoria=SUITE&sort=preco,desc
     */
    @GetMapping("/filtrados/buscar")
    public ResponseEntity<Map<String, Object>> listarComFiltros(
            @RequestParam(required = false) Quarto.CategoriaQuarto categoria,
            @RequestParam(required = false) Boolean disponibilidade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {

        try {
            // Processar o parâmetro de ordenação
            String[] sortParts = sort.split(",");
            String sortField = sortParts[0];
            Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

            // Buscar quartos com filtros
            Page<Quarto> quartos = service.listarComFiltros(categoria, disponibilidade, pageable);

            // Preparar resposta com metadados de paginação
            Map<String, Object> response = new HashMap<>();
            response.put("conteudo", quartos.getContent());
            response.put("paginaAtual", quartos.getNumber());
            response.put("totalPorPagina", quartos.getSize());
            response.put("totalElementos", quartos.getTotalElements());
            response.put("totalPaginas", quartos.getTotalPages());
            response.put("ehUltimaPagina", quartos.isLast());
            response.put("ehPrimeiraPagina", quartos.isFirst());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/quartos/disponiveis
     * Listar apenas quartos disponíveis com paginação
     */
    @GetMapping("/disponiveis")
    public ResponseEntity<Map<String, Object>> listarDisponiveis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "numero,asc") String sort) {

        try {
            String[] sortParts = sort.split(",");
            String sortField = sortParts[0];
            Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
            Page<Quarto> quartos = service.listarPorDisponibilidade(true, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("conteudo", quartos.getContent());
            response.put("paginaAtual", quartos.getNumber());
            response.put("totalPorPagina", quartos.getSize());
            response.put("totalElementos", quartos.getTotalElements());
            response.put("totalPaginas", quartos.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/quartos/categoria/{categoria}
     * Listar quartos por categoria com paginação
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Map<String, Object>> listarPorCategoria(
            @PathVariable Quarto.CategoriaQuarto categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "preco,asc") String sort) {

        try {
            String[] sortParts = sort.split(",");
            String sortField = sortParts[0];
            Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
            Page<Quarto> quartos = service.listarPorCategoria(categoria, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("conteudo", quartos.getContent());
            response.put("paginaAtual", quartos.getNumber());
            response.put("totalPorPagina", quartos.getSize());
            response.put("totalElementos", quartos.getTotalElements());
            response.put("totalPaginas", quartos.getTotalPages());
            response.put("categoria", categoria.getDescricao());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/quartos/estatisticas
     * Retornar estatísticas dos quartos
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> estatisticas() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalQuartos", service.listarTodos().size());
            stats.put("quartosDiponiveis", service.contarDisponíveis());
            stats.put("quartosIndisponíveis", service.contarIndisponíveis());

            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
