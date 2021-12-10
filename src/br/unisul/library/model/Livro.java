package br.unisul.library.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Livro {
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty titulo = new SimpleStringProperty("");
	private StringProperty autor = new SimpleStringProperty("");
	private StringProperty isbn = new SimpleStringProperty("");
	private IntegerProperty quantidade = new SimpleIntegerProperty(0);

	public Livro() {
	}

	public Livro(int id, String titulo, String autor, String isbn, Integer quantidade) {
		setId(id);
		setTitulo(titulo);
		setAutor(autor);
		setIsbn(isbn);
		setQuantidade(quantidade);
	}

	public Livro(String titulo, String autor, String isbn, Integer quantidade) {
		setTitulo(titulo);
		setAutor(autor);
		setIsbn(isbn);
		setQuantidade(quantidade);
	}

	public final StringProperty tituloProperty() {
		return this.titulo;
	}

	public final String getTitulo() {
		return this.tituloProperty().get();
	}

	public final void setTitulo(final String titulo) {
		this.tituloProperty().set(titulo);
	}

	public final StringProperty autorProperty() {
		return this.autor;
	}

	public final String getAutor() {
		return this.autorProperty().get();
	}

	public final void setAutor(final String autor) {
		this.autorProperty().set(autor);
	}

	public final StringProperty isbnProperty() {
		return this.isbn;
	}

	public final String getIsbn() {
		return this.isbnProperty().get();
	}

	public final void setIsbn(final String isbn) {
		this.isbnProperty().set(isbn);
	}

	public final IntegerProperty quantidadeProperty() {
		return this.quantidade;
	}

	public final int getQuantidade() {
		return this.quantidadeProperty().get();
	}

	public final void setQuantidade(final int quantidade) {
		this.quantidadeProperty().set(quantidade);
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}

	public final int getId() {
		return this.idProperty().get();
	}

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

}