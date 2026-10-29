package com.example.sigai;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import com.example.sigai.servicoimoveis.SigaiApplication;
import com.example.sigai.servicoimoveis.repository.FotoRepository;
import com.example.sigai.servicoimoveis.repository.ImovelRepository;

@SpringBootTest(classes = SigaiApplication.class)
@AutoConfigureMockMvc
class SigaiApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FotoRepository fotoRepository;

	@Autowired
	private ImovelRepository imovelRepository;

	@BeforeEach
	void limparDados() {
		fotoRepository.deleteAll();
		imovelRepository.deleteAll();
	}

	@Test
	void crudDeImovelContinuaDisponivel() throws Exception {
		MvcResult criado = mockMvc.perform(post("/imoveis")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"endereco":"Rua das Flores, 123","valorAluguel":1200.0,"descricao":"Apartamento"}
						"""))
				.andExpect(status().isCreated())
				.andReturn();
		long imovelId = idDaLocation(criado);

		mockMvc.perform(get("/imoveis"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].endereco").value("Rua das Flores, 123"));
		mockMvc.perform(get("/imoveis/{id}", imovelId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(imovelId));
		mockMvc.perform(put("/imoveis/{id}", imovelId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"endereco":"Rua Nova, 456","valorAluguel":1500.0,"descricao":"Atualizado"}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.endereco").value("Rua Nova, 456"));
		mockMvc.perform(delete("/imoveis/{id}", imovelId))
				.andExpect(status().isNoContent());
		mockMvc.perform(get("/imoveis/{id}", imovelId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));
	}

	@Test
	void crudDeFotoMantemRelacionamentoComImovel() throws Exception {
		long imovelId = criarImovel();
		MvcResult criada = mockMvc.perform(post("/fotos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"imovelId":%d,"url":"https://exemplo.test/foto.jpg","legenda":"Sala"}
						""".formatted(imovelId)))
				.andExpect(status().isCreated())
				.andReturn();
		long fotoId = idDaLocation(criada);

		mockMvc.perform(get("/fotos/{id}", fotoId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.imovelId").value(imovelId))
				.andExpect(jsonPath("$.url").value("https://exemplo.test/foto.jpg"));
		mockMvc.perform(put("/fotos/{id}", fotoId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"imovelId":%d,"url":"https://exemplo.test/atualizada.jpg","legenda":"Quarto"}
						""".formatted(imovelId)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.legenda").value("Quarto"));
		mockMvc.perform(delete("/fotos/{id}", fotoId))
				.andExpect(status().isNoContent());
		mockMvc.perform(get("/fotos/{id}", fotoId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));
	}

	@Test
	void errosDeFotoUsamProblemDetailEValidacaoDeEntrada() throws Exception {
		mockMvc.perform(post("/fotos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"imovelId":9999,"url":"https://exemplo.test/foto.jpg"}
						"""))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));

		mockMvc.perform(post("/fotos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"url":" ","legenda":"%s"}
						""".formatted("x".repeat(201))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors.imovelId").exists())
				.andExpect(jsonPath("$.errors.url").exists())
				.andExpect(jsonPath("$.errors.legenda").exists());
	}

	private long criarImovel() throws Exception {
		MvcResult criado = mockMvc.perform(post("/imoveis")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"endereco":"Rua das Flores, 123","valorAluguel":1200.0}
						"""))
				.andExpect(status().isCreated())
				.andReturn();
		return idDaLocation(criado);
	}

	private long idDaLocation(MvcResult result) {
		String location = result.getResponse().getHeader("Location");
		return Long.parseLong(location.substring(location.lastIndexOf('/') + 1));
	}

}
