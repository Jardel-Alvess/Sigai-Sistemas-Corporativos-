package com.example.sigai.servicoimoveis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sigai.servicoimoveis.model.Imovel;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
}