package com.example.descansoperfeito.controller;

import com.example.descansoperfeito.model.Hospede;
import com.example.descansoperfeito.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {

    @Autowired
    private HospedeService service;

    @GetMapping
    public ResponseEntity<List<Hospede>> listar() {
        List<Hospede> lista = service.listarTodos();
        return ResponseEntity.ok(lista); // Status 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospede> buscarPorId(@PathVariable Long id) {
        try {
            Hospede hospede = service.buscarPorId(id);
            return ResponseEntity.ok(hospede); // Status 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Status 404 Not Found
        }
    }

    @PostMapping
    public ResponseEntity<Hospede> criar(@RequestBody Hospede hospede) { // Uso de @RequestBody
        try {
            Hospede novoHospede = service.salvar(hospede);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoHospede); // Status 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Status 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospede> atualizar(@PathVariable Long id, @RequestBody Hospede hospede) {
        try {
            Hospede atualizado = service.atualizar(id, hospede);
            return ResponseEntity.ok(atualizado); // Status 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Status 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Status 400 Bad Request
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build(); // Status 204 No Content (Sucesso para Delete)
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Status 404 Not Found
        }
    }
}