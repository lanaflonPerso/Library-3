package ru.otus.boljshoj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.boljshoj.domain.Author;
import ru.otus.boljshoj.domain.Book;
import ru.otus.boljshoj.domain.Comment;
import ru.otus.boljshoj.domain.Genre;
import ru.otus.boljshoj.repos.AuthorRepository;
import ru.otus.boljshoj.repos.BookRepository;
import ru.otus.boljshoj.repos.CommentRepository;
import ru.otus.boljshoj.repos.GenreRepository;

@Service
public class EntityServiceImpl implements EntityService{
    private AuthorRepository authorRepository;
    private GenreRepository genreRepository;
    private BookRepository bookRepository;
    private CommentRepository commentRepository;
    private IOService ioService;

    @Autowired
    public EntityServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository, CommentRepository commentRepository, IOService ioService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
        this.ioService = ioService;
    }

    public void count(String entity){
        String paramToPrinter = "Count of %s: %d";
        switch (entity) {
            case "authors":
                ioService.printMessage(paramToPrinter, entity, authorRepository.count());
                break;
            case "books":
                ioService.printMessage(paramToPrinter, entity, bookRepository.count());
                break;
            case "genres":
                ioService.printMessage(paramToPrinter, entity, genreRepository.count());
                break;
            case "comments":
                ioService.printMessage(paramToPrinter, entity, commentRepository.count());
                break;
            default:
                ioService.printMessage("You must choose entity: authors, books, genres or comments");
        }
    }

    @Override
    public void get(String entity, Long id) {
        switch (entity) {
            case "author":
                ioService.printMessage(authorRepository.findById(id).get().toString());
                break;
            case "book":
                ioService.printMessage(bookRepository.findById(id).get().toString());
                break;
            case "genre":
                ioService.printMessage(genreRepository.findById(id).get().toString());
                break;
            case "comment":
                ioService.printMessage(commentRepository.findById(id).get().toString());
                break;
            default:
                ioService.printMessage("You must choose entity: author, book, genre or comment");
        }
    }

    @Override
    public void all(String entity) {
        switch (entity) {
            case "authors":
                ioService.printMessage(authorRepository.findAll().toString());
                break;
            case "books":
                ioService.printMessage(bookRepository.findAll().toString());
                break;
            case "genres":
                ioService.printMessage(genreRepository.findAll().toString());
                break;
            case "comments":
                ioService.printMessage(commentRepository.findAll().toString());
                break;
            default:
                ioService.printMessage("You must choose entity: authors, books, genres or comments");
        }
    }

    @Override
    public void delete(String entity, Long id) {
        String paramToPrinter = "%s with id %d was succesfully deleted";
        switch (entity) {
            case "author":
                authorRepository.deleteById(id);
                ioService.printMessage(paramToPrinter, entity, id);
                break;
            case "book":
                bookRepository.deleteById(id);
                ioService.printMessage(paramToPrinter, entity, id);
                break;
            case "genre":
                genreRepository.deleteById(id);
                ioService.printMessage(paramToPrinter, entity, id);
                break;
            case "comment":
                commentRepository.deleteById(id);
                ioService.printMessage(paramToPrinter, entity, id);
                break;
            default:
                ioService.printMessage("You must choose entity: author, book, genre or comment");
        }
    }

    @Override
    public void add(String entity) {
        String paramToPrinter = "%s was succesfully added";
        switch (entity) {
            case "author":
                ioService.printMessage("Input %s name", entity);
                String nameOfNewAuthor = ioService.getStringFromUser();
                ioService.printMessage("Input %s surname", entity);
                String surnameOfNewAuthor = ioService.getStringFromUser();
                authorRepository.save(new Author(nameOfNewAuthor, surnameOfNewAuthor));
                ioService.printMessage(paramToPrinter, entity);
                break;
            case "book":
                ioService.printMessage("Input author id");
                Author author = authorRepository.findById(Long.parseLong(ioService.getStringFromUser())).get();
                ioService.printMessage("Input genre id");
                Genre genre = genreRepository.findById(Long.parseLong(ioService.getStringFromUser())).get();
                ioService.printMessage("Input %s name", entity);
                String nameOfNewBook = ioService.getStringFromUser();
                bookRepository.save(new Book(author, genre, nameOfNewBook));
                ioService.printMessage(paramToPrinter, entity);
                break;
            case "genre":
                ioService.printMessage("Input %s name", entity);
                String nameOfNewGenre = ioService.getStringFromUser();
                genreRepository.save(new Genre(nameOfNewGenre));
                ioService.printMessage(paramToPrinter, entity);
                break;
            case "comment":
                ioService.printMessage("Input bookId for comment");
                Book book = bookRepository.findById(Long.parseLong(ioService.getStringFromUser())).get();
                ioService.printMessage("Input %s for book %s:", entity, book.getTitle());
                String newComment = ioService.getStringFromUser();
                commentRepository.save(new Comment(book, newComment));
                ioService.printMessage(paramToPrinter, entity);
                break;
            default:
                ioService.printMessage("You must choose entity: author, book, genre or comment");
        }
    }

    @Override
    public void getBookByAuthorID(Long id) {
        ioService.printMessage(bookRepository.findBooksByAuthorId(id).toString());
    }

    @Override
    public void getCommentsByBookID(Long id) {
        ioService.printMessage(commentRepository.findCommentsByBookId(id).toString());
    }
}