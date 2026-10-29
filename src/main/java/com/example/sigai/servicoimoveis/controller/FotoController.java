package com.example.sigai.servicoimoveis.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sigai.DTO.FotoRequestDTO;
import com.example.sigai.DTO.FotoResponseDTO;
import com.example.sigai.servicoimoveis.service.FotoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fotos")
public class FotoController {
    private final FotoService fotoService;

    public FotoController(FotoService fotoService) {
        this.fotoService = fotoService;
    }

    @PostMapping
    public ResponseEntity<FotoResponseDTO> criar(@Valid @RequestBody FotoRequestDTO dto) {
        FotoResponseDTO fotoSalva = fotoService.criar(dto);
        URI location = URI.create("/fotos/" + fotoSalva.getId());
        return ResponseEntity.created(location).body(fotoSalva);
    }

    @GetMapping("/{id}")
    public FotoResponseDTO buscarPorId(@PathVariable Long id) {
        return fotoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public FotoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody FotoRequestDTO dto) {
        return fotoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fotoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
