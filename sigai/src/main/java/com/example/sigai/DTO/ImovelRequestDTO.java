package com.example.sigai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class ImovelRequestDTO {
    private String endereco;
    private Double valorAluguel;
    private String descricao;
}
