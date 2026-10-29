package com.example.sigai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class ImovelRequestDTO {
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private Double valorAluguel;
    private String descricao;
}
