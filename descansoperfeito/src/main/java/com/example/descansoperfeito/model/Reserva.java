package com.example.descansoperfeito.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Hospede hospede;

    @ManyToOne
    private Quarto quarto;

    private LocalDate dataCheckin;
    private LocalDate dataCheckout;

    public Reserva() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Hospede getHospede() { return hospede; }
    public void setHospede(Hospede hospede) { this.hospede = hospede; }
    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }
    public LocalDate getDataCheckin() { return dataCheckin; }
    public void setDataCheckin(LocalDate dataCheckin) { this.dataCheckin = dataCheckin; }
    public LocalDate getDataCheckout() { return dataCheckout; }
    public void setDataCheckout(LocalDate dataCheckout) { this.dataCheckout = dataCheckout; }
}
