package com.example.descansoperfeito.controller;

import com.example.descansoperfeito.dto.ReservaDTO;
import com.example.descansoperfeito.model.Reserva;
import com.example.descansoperfeito.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaService service;

    @GetMapping
    public ResponseEntity<Page<Reserva>> listar(@PageableDefault(size = 10, sort = "dataCheckin") Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @PostMapping
    public ResponseEntity<Reserva> criar(@RequestBody ReservaDTO dto) {
        return ResponseEntity.status(201).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(@PathVariable Long id, @RequestBody ReservaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}