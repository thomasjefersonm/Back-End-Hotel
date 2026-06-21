package com.example.descansoperfeito.service;

import com.example.descansoperfeito.dto.ReservaDTO;
import com.example.descansoperfeito.model.Reserva;
import com.example.descansoperfeito.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    public Page<Reserva> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Reserva salvar(ReservaDTO dto) {
        if (!repository.findConflitos(dto.quartoId(), dto.dataCheckin(), dto.dataCheckout()).isEmpty()) {
            throw new RuntimeException("Quarto indisponível para o período selecionado (Overbooking).");
        }
        Reserva reserva = new Reserva();
        reserva.setDataCheckin(dto.dataCheckin());
        reserva.setDataCheckout(dto.dataCheckout());
        return repository.save(reserva);
    }

    public Reserva atualizar(Long id, ReservaDTO dto) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        reserva.setDataCheckin(dto.dataCheckin());
        reserva.setDataCheckout(dto.dataCheckout());
        return repository.save(reserva);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Reserva não encontrada");
        }
        repository.deleteById(id);
    }
}
