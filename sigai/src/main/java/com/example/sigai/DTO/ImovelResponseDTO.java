package com.example.sigai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class ImovelResponseDTO {
    private Long id;
    private String endereco;
    private Double valorAluguel;
}
