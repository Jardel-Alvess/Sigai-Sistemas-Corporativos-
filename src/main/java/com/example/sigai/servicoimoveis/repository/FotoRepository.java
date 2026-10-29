package com.example.sigai.servicoimoveis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sigai.servicoimoveis.model.Foto;

public interface FotoRepository extends JpaRepository<Foto, Long> {
}
