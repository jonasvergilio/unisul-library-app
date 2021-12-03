package br.unisul.library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
	
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cpf = new SimpleStringProperty("");
	private StringProperty nascimento = new SimpleStringProperty("");
	private StringProperty telefone = new SimpleStringProperty("");
	private StringProperty email = new SimpleStringProperty("");
	public final IntegerProperty idProperty() {
		return this.id;
	}
	
	public final int getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	
	public final StringProperty nomeProperty() {
		return this.nome;
	}
	
	public final String getNome() {
		return this.nomeProperty().get();
	}
	
	public final void setNome(final String nome) {
		this.nomeProperty().set(nome);
	}
	
	public final StringProperty cpfProperty() {
		return this.cpf;
	}
	
	public final String getCpf() {
		return this.cpfProperty().get();
	}
	
	public final void setCpf(final String cpf) {
		this.cpfProperty().set(cpf);
	}
	
	public final StringProperty nascimentoProperty() {
		return this.nascimento;
	}
	
	public final String getNascimento() {
		return this.nascimentoProperty().get();
	}
	
	public final void setNascimento(final String nascimento) {
		this.nascimentoProperty().set(nascimento);
	}
	
	public final StringProperty telefoneProperty() {
		return this.telefone;
	}
	
	public final String getTelefone() {
		return this.telefoneProperty().get();
	}
	
	public final void setTelefone(final String telefone) {
		this.telefoneProperty().set(telefone);
	}
	
	public final StringProperty emailProperty() {
		return this.email;
	}
	
	public final String getEmail() {
		return this.emailProperty().get();
	}
	
	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	
	
	
	
}
