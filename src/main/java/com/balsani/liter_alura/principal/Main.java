package com.balsani.liter_alura.principal;


import com.balsani.liter_alura.domain.model.Autor;
import com.balsani.liter_alura.domain.model.Livro;

import com.balsani.liter_alura.domain.model.dto.AutorDTO;
import com.balsani.liter_alura.domain.model.dto.LivroDTO;
import com.balsani.liter_alura.repository.AutorRepository;
import com.balsani.liter_alura.repository.LivroRepository;
import com.balsani.liter_alura.services.ApiConsumer;
import com.balsani.liter_alura.services.ConvertDataIMPL;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static Scanner SCANNER = new Scanner(System.in);
    private ApiConsumer consumer = new ApiConsumer();
    private ConvertDataIMPL convert = new ConvertDataIMPL();
    private final String ADDRESS = "https://gutendex.com/books?search=";

    private final LivroRepository livroRepository;
    private final AutorRepository authorRepository;

    public Main(LivroRepository livroRepository, AutorRepository authorRepository) {
        this.livroRepository = livroRepository;
        this.authorRepository = authorRepository;
    }

    public void getMenu() {
        String menu = """
                ***********************************************
                    
                    1 - Buscar um livro
                    2 - Mostrar livros registrados
                    3 - Mostrar autores cadastrados
                    4 - Listar Autores vivos em determinado ano
                    5 - Listar Livros em determinado Idioma
                    6 - Exibir a quantidade de livros em um idioma.
                
                    0 - Exit
                ***********************************************
                """;
        int option = -1;

        while ( option != 0 ) {
            System.out.println(menu);
            option = SCANNER.nextInt();
            SCANNER.nextLine();

            switch ( option ) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    autoresVivosNoAno();
                    break;
                case 5:
                    listarLivrosEmUmDeterminadoIdioma();
                    break;

                case 0:
                    System.out.println("bye bye!");
                    break;
                default:
                    System.out.println("Invalid option, try again...");
            }
        }

    }


    public void buscarLivro() {
        System.out.println("Digite o nome do Livro: ");
        var nomeLivro = SCANNER.nextLine();
        System.out.println("Buscando...");
        String enderecoBusca = ADDRESS.concat(nomeLivro
                .replace(" ", "+").toLowerCase().trim());

        String json = consumer.getApiData(enderecoBusca);
        String jsonLivro = convert.extraiObjetoJson(json, "results");

        List<LivroDTO> livrosDTO = convert.obterLista(jsonLivro, LivroDTO.class);

        if (!livrosDTO.isEmpty()) {
            LivroDTO livroDTO = livrosDTO.get(0);


            for (AutorDTO autorDTO : livroDTO.autores()) {
                Autor autor = authorRepository.findByNomeAutor(autorDTO.nomeAutor());
                if (autor == null) {
                    autor = new Autor(autorDTO);
                    authorRepository.save(autor);
                }
            }


            Livro livro = new Livro(livroDTO);
            for (AutorDTO autorDTO : livroDTO.autores()) {
                Autor autor = authorRepository.findByNomeAutor(autorDTO.nomeAutor());
                livro.setAutor(autor);
                autor.getLivros().add(livro);
            }
            livroRepository.save(livro);

            System.out.println("Livro salvo com sucesso: " + livro);
        } else {
            System.out.println("Livro não encontrado");
        }
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = authorRepository.findAllAutors();
        autores.forEach(System.out::println);
    }

    private void autoresVivosNoAno() {
        try {
            System.out.println("Digite o ano:");
            Integer ano = SCANNER.nextInt();
            SCANNER.nextLine();

            List<Autor> autores = authorRepository.findLivingAuthorsInYear(ano);
            autores.forEach(System.out::println);
        }catch (InputMismatchException e){
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            SCANNER.nextLine();
        }
    }
    public void listarLivrosEmUmDeterminadoIdioma() {
        System.out.println("Digite o idioma");
        var idioma = SCANNER.nextLine();
        List<Livro> livros = livroRepository.findBookByIdioma(idioma);
        livros.forEach(System.out::println);


    }




}
