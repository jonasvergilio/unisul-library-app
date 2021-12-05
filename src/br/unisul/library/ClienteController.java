package br.unisul.library;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.unisul.library.model.Cliente;
import br.unisul.library.util.Conexao;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ClienteController {
	@FXML
	TextField txtNome;
	@FXML
	TextField txtCpf;
	@FXML
	TextField txtNascimento;
	@FXML
	TextField txtTelefone;
	@FXML
	TextField txtEmail;

	@FXML
	private void salva() {
		insertCliente();
	}

	private void insertCliente() {
		String sql = "INSERT INTO cliente (nome, cpf, nascimento, telefone, email) VALUES (?, ?, ?, ?, ?)";
		
		try {
			Connection connection = Conexao.conectaSqlite();
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ps.setString(1, txtNome.getText());
			ps.setString(2, txtCpf.getText());
			ps.setString(3, txtNascimento.getText());
			ps.setString(4, txtTelefone.getText());
			ps.setString(5, txtEmail.getText());
			
			ps.executeUpdate();
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
