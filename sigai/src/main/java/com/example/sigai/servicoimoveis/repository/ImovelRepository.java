package com.example.sigai.servicoimoveis.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.sigai.servicoimoveis.model.Imovel;

@Repository
public class ImovelRepository {
    private final List<Imovel> imoveis = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public List<Imovel> findAll() {
        return new ArrayList<>(imoveis);
    }

    public Optional<Imovel> findById(Long id) {
        return imoveis.stream()
                .filter(imovel -> imovel.getId().equals(id))
                .findFirst();
    }

    public Imovel save(Imovel imovel) {
        if (imovel.getId() == null) {
            imovel.setId(proximoId.getAndIncrement());
        } else {
            deleteById(imovel.getId());
        }
        imoveis.add(imovel);
        return imovel;
    }

    public void deleteById(Long id) {
        imoveis.removeIf(imovel -> imovel.getId().equals(id));
    }
}