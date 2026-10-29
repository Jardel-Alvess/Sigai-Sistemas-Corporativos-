package com.example.sigai.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoRequestDTO {
    @NotNull
    private Long imovelId;
    @NotBlank
    private String url;
    @Size(max = 200)
    private String legenda;
}
