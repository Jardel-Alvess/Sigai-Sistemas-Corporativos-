
package com.example.sigai.servicoimoveis.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sigai.DTO.ImovelRequestDTO;
import com.example.sigai.DTO.ImovelResponseDTO;
import com.example.sigai.servicoimoveis.service.ImovelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    private final ImovelService imovelService;

    public ImovelController(ImovelService imovelService) {
        this.imovelService = imovelService;
    }

    @GetMapping
    public List<ImovelResponseDTO> listar() {
        return imovelService.listar();
    }

    @PostMapping
    public ResponseEntity<ImovelResponseDTO> criar(@Valid @RequestBody ImovelRequestDTO dto) {
        ImovelResponseDTO imovelSalvo = imovelService.criar(dto);
        URI location = URI.create("/imoveis/" + imovelSalvo.getId());
        return ResponseEntity.created(location).body(imovelSalvo);
    }

    @GetMapping("/{id}")
    public ImovelResponseDTO buscarPorId(@PathVariable Long id) {
        return imovelService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ImovelResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ImovelRequestDTO dto) {
        return imovelService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        imovelService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}