package com.example.sigai.servicoimoveis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sigai.DTO.ImovelRequestDTO;
import com.example.sigai.DTO.ImovelResponseDTO;
import com.example.sigai.servicoimoveis.exception.ImovelNaoEncontradoException;
import com.example.sigai.servicoimoveis.model.Imovel;
import com.example.sigai.servicoimoveis.repository.ImovelRepository;

@Service 
public class ImovelService {
    private final ImovelRepository imovelRepository;

    public ImovelService(ImovelRepository imovelRepository) {
        this.imovelRepository = imovelRepository;
    }

    public List<ImovelResponseDTO> listar() {
        return imovelRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ImovelResponseDTO criar(ImovelRequestDTO dto) {
        Imovel salvo = imovelRepository.save(toEntity(dto));
        return toResponseDTO(salvo);
    }

    public ImovelResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    public ImovelResponseDTO atualizar(Long id, ImovelRequestDTO dto) {
        Imovel imovel = buscarEntidadePorId(id);
        imovel.setEndereco(dto.getEndereco());
        imovel.setValorAluguel(dto.getValorAluguel());
        imovel.setDescricao(dto.getDescricao());
        return toResponseDTO(imovelRepository.save(imovel));
    }

    public void excluir(Long id) {
        buscarEntidadePorId(id);
        imovelRepository.deleteById(id);
    }

    private Imovel buscarEntidadePorId(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new ImovelNaoEncontradoException(id));
    }

    private Imovel toEntity(ImovelRequestDTO dto) {
        return new Imovel(null, dto.getEndereco(), dto.getValorAluguel(), dto.getDescricao());
    }

    private ImovelResponseDTO toResponseDTO(Imovel imovel) {
        return new ImovelResponseDTO(imovel.getId(), imovel.getEndereco(), imovel.getValorAluguel());
    }
}
