package com.balsani.liter_alura.repository;

import com.balsani.liter_alura.domain.model.Autor;
import com.balsani.liter_alura.domain.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l WHERE l.idoma LIKE %:idioma%")
    List<Livro> findBookByIdioma(@Param("idioma") String idioma);





}