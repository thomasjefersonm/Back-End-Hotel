package com.example.descansoperfeito.service;

import com.example.descansoperfeito.model.Quarto;
import com.example.descansoperfeito.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository repository;

    /**
     * Listar todos os quartos (sem paginação)
     */
    public List<Quarto> listarTodos() {
        return repository.findAll();
    }

    /**
     * Buscar quarto por ID
     */
    public Quarto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarto com ID " + id + " não encontrado."));
    }

    /**
     * Buscar quarto por número
     */
    public Quarto buscarPorNumero(Integer numero) {
        Quarto quarto = repository.findByNumero(numero);
        if (quarto == null) {
            throw new RuntimeException("Quarto com número " + numero + " não encontrado.");
        }
        return quarto;
    }

    /**
     * Salvar novo quarto
     */
    public Quarto salvar(Quarto quarto) {
        return repository.save(quarto);
    }

    /**
     * Atualizar quarto existente
     */
    public Quarto atualizar(Long id, Quarto dadosAtualizados) {
        Quarto quartoExistente = buscarPorId(id);

        quartoExistente.setNumero(dadosAtualizados.getNumero());
        quartoExistente.setCategoria(dadosAtualizados.getCategoria());
        quartoExistente.setPreco(dadosAtualizados.getPreco());
        quartoExistente.setDisponibilidade(dadosAtualizados.getDisponibilidade());

        return repository.save(quartoExistente);
    }

    /**
     * Deletar quarto
     */
    public void deletar(Long id) {
        Quarto quarto = buscarPorId(id);
        repository.delete(quarto);
    }

    /**
     * MISSÃO ESPECIAL: Listar quartos com paginação, ordenação e filtros
     * Parâmetros:
     * - categoria: filtrar por categoria (opcional)
     * - disponibilidade: filtrar por disponibilidade (opcional)
     * - pageable: contém informações de página, tamanho e ordenação
     */
    public Page<Quarto> listarComFiltros(
            Quarto.CategoriaQuarto categoria,
            Boolean disponibilidade,
            Pageable pageable) {

        // Se nenhum filtro foi informado, retorna todos com paginação
        if (categoria == null && disponibilidade == null) {
            return repository.findAll(pageable);
        }

        // Se apenas categoria foi informada
        if (categoria != null && disponibilidade == null) {
            return repository.findByCategoria(categoria, pageable);
        }

        // Se apenas disponibilidade foi informada
        if (categoria == null && disponibilidade != null) {
            return repository.findByDisponibilidade(disponibilidade, pageable);
        }

        // Se ambos os filtros foram informados
        return repository.findByCategoriaAndDisponibilidade(categoria, disponibilidade, pageable);
    }

    /**
     * Listar quartos por disponibilidade com paginação
     */
    public Page<Quarto> listarPorDisponibilidade(Boolean disponibilidade, Pageable pageable) {
        return repository.findByDisponibilidade(disponibilidade, pageable);
    }

    /**
     * Listar quartos por categoria com paginação
     */
    public Page<Quarto> listarPorCategoria(Quarto.CategoriaQuarto categoria, Pageable pageable) {
        return repository.findByCategoria(categoria, pageable);
    }

    /**
     * Contar quartos disponíveis
     */
    public long contarDisponíveis() {
        return listarTodos().stream()
                .filter(Quarto::getDisponibilidade)
                .count();
    }

    /**
     * Contar quartos indisponíveis
     */
    public long contarIndisponíveis() {
        return listarTodos().stream()
                .filter(q -> !q.getDisponibilidade())
                .count();
    }
}
