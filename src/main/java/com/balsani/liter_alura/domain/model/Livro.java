package com.balsani.liter_alura.domain.model;


import com.balsani.liter_alura.domain.model.dto.LivroDTO;
import jakarta.persistence.*;


@Entity
@Table(name = "tb_livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "id_seq")
    private Long id;
    @Column(length = 512)
    private String titulo;


    private String idoma;
    @Column(name = "numero_download")
    private Integer numeroDownload;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {
    }

    public Livro(LivroDTO livroDTO) {
        this.titulo = livroDTO.titulo();
        this.idoma = String.join(",", livroDTO.idioma());
        this.numeroDownload = livroDTO.numeroDownload();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



    public String getIdoma() {
        return idoma;
    }

    public void setIdoma(String idoma) {
        this.idoma = idoma;
    }

    public Integer getNumeroDownload() {
        return numeroDownload;
    }

    public void setNumeroDownload(Integer numeroDownload) {
        this.numeroDownload = numeroDownload;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idoma='" + idoma + '\'' +
                ", numeroDownload=" + numeroDownload +
                ", autor=" + autor +
                '}';
    }
}
