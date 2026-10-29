package com.example.sigai.servicoimoveis.service;

import org.springframework.stereotype.Service;

import com.example.sigai.DTO.FotoRequestDTO;
import com.example.sigai.DTO.FotoResponseDTO;
import com.example.sigai.servicoimoveis.exception.FotoNaoEncontradaException;
import com.example.sigai.servicoimoveis.exception.ImovelNaoEncontradoException;
import com.example.sigai.servicoimoveis.model.Foto;
import com.example.sigai.servicoimoveis.model.Imovel;
import com.example.sigai.servicoimoveis.repository.FotoRepository;
import com.example.sigai.servicoimoveis.repository.ImovelRepository;

@Service
public class FotoService {
    private final FotoRepository fotoRepository;
    private final ImovelRepository imovelRepository;

    public FotoService(FotoRepository fotoRepository, ImovelRepository imovelRepository) {
        this.fotoRepository = fotoRepository;
        this.imovelRepository = imovelRepository;
    }

    public FotoResponseDTO criar(FotoRequestDTO dto) {
        return toResponseDTO(fotoRepository.save(toEntity(dto)));
    }

    public FotoResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    public FotoResponseDTO atualizar(Long id, FotoRequestDTO dto) {
        Foto foto = buscarEntidadePorId(id);
        foto.setImovel(buscarImovelPorId(dto.getImovelId()));
        foto.setUrl(dto.getUrl());
        foto.setLegenda(dto.getLegenda());
        return toResponseDTO(fotoRepository.save(foto));
    }

    public void deletar(Long id) {
        buscarEntidadePorId(id);
        fotoRepository.deleteById(id);
    }

    private Foto buscarEntidadePorId(Long id) {
        return fotoRepository.findById(id)
                .orElseThrow(() -> new FotoNaoEncontradaException(id));
    }

    private Imovel buscarImovelPorId(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new ImovelNaoEncontradoException(id));
    }

    private Foto toEntity(FotoRequestDTO dto) {
        return new Foto(null, buscarImovelPorId(dto.getImovelId()), dto.getUrl(), dto.getLegenda());
    }

    private FotoResponseDTO toResponseDTO(Foto foto) {
        return new FotoResponseDTO(foto.getId(), foto.getImovel().getId(), foto.getUrl(), foto.getLegenda());
    }
}
