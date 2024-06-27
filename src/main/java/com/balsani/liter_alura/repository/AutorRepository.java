package com.balsani.liter_alura.repository;

import com.balsani.liter_alura.domain.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.nomeAutor = :nomeAutor")
    Autor findByNomeAutor(String nomeAutor);

    @Query("SELECT a FROM Autor a WHERE a.anoFalecimento >= :ano AND :ano >= a.anoNascimento")
    List<Autor> findLivingAuthorsInYear(@Param("ano") Integer ano);

    @Query("SELECT l.autor FROM Livro l")
    List<Autor> findAllAutors();
}
