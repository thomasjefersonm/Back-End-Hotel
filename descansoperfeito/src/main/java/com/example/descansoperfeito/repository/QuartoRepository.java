package com.example.descansoperfeito.repository;

import com.example.descansoperfeito.model.Quarto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    Page<Quarto> findByDisponibilidade(Boolean disponibilidade, Pageable pageable);

    Page<Quarto> findByCategoria(Quarto.CategoriaQuarto categoria, Pageable pageable);

    Page<Quarto> findByCategoriaAndDisponibilidade(
            Quarto.CategoriaQuarto categoria,
            Boolean disponibilidade,
            Pageable pageable
    );

    Quarto findByNumero(Integer numero);

    @Query("SELECT q FROM Quarto q WHERE " +
            "(:categoria IS NULL OR q.categoria = :categoria) AND " +
            "(:disponibilidade IS NULL OR q.disponibilidade = :disponibilidade)")
    Page<Quarto> buscarComFiltros(
            @Param("categoria") Quarto.CategoriaQuarto categoria,
            @Param("disponibilidade") Boolean disponibilidade,
            Pageable pageable
    );
}