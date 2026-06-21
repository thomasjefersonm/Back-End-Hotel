package com.example.descansoperfeito.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Quarto extends EntidadeBase {

    private Integer numero;

    @Enumerated(EnumType.STRING)
    private CategoriaQuarto categoria;

    private Double preco;

    private Boolean disponibilidade;

    public Quarto() {
    }

    public Quarto(Integer numero, CategoriaQuarto categoria, Double preco, Boolean disponibilidade) {
        this.numero = numero;
        this.categoria = categoria;
        this.preco = preco;
        this.disponibilidade = disponibilidade;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public CategoriaQuarto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaQuarto categoria) {
        this.categoria = categoria;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public enum CategoriaQuarto {
        SIMPLES("Simples"),
        DUPLO("Duplo"),
        SUITE("Suite"),
        PRESIDENCIAL("Presidencial");

        private final String descricao;

        CategoriaQuarto(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
