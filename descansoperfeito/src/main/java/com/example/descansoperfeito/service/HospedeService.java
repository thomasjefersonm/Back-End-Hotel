package com.example.descansoperfeito.service;

import com.example.descansoperfeito.model.Hospede; // Importa o seu Model
import com.example.descansoperfeito.repository.HospedeRepository; // Importa o seu Repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository repository;

    public List<Hospede> listarTodos() {
        return repository.findAll();
    }

    public Hospede buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóspede com ID " + id + " não encontrado."));
    }

    public Hospede salvar(Hospede hospede) {
        return repository.save(hospede);
    }

    public Hospede atualizar(Long id, Hospede dadosAtualizados) {
        Hospede hospedeExistente = buscarPorId(id);

        hospedeExistente.setNome(dadosAtualizados.getNome());
        hospedeExistente.setEmail(dadosAtualizados.getEmail());
        hospedeExistente.setTelefone(dadosAtualizados.getTelefone());
        hospedeExistente.setCpf(dadosAtualizados.getCpf());

        return repository.save(hospedeExistente);
    }

    public void deletar(Long id) {
        Hospede hospede = buscarPorId(id);
        repository.delete(hospede);
    }
}