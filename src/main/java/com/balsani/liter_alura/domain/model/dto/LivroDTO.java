package com.balsani.liter_alura.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(@JsonAlias("title") String titulo,
                       @JsonAlias("authors") List<AutorDTO> autores,
                       @JsonAlias("languages") List<String> idioma,
                       @JsonAlias("download_count") Integer numeroDownload) {
}

