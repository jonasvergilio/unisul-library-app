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
		Cliente cliente = lerTela();
		String sql = "insert into cliente (nome, cpf, nascimento, telefone, email) values (?,?,?,?,?)";
		try {
			Connection con = Conexao.conectaSqlite();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getCpf());
			ps.setString(3, cliente.getNascimento());
			ps.setString(4, cliente.getTelefone());
			ps.setString(5, cliente.getEmail());
			
			ps.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Cliente lerTela() {
		Cliente cliente = new Cliente();
		cliente.setNome(txtNome.getText());
		cliente.setCpf(txtCpf.getText());
		cliente.setNascimento(txtNascimento.getText());
		cliente.setTelefone(txtTelefone.getText());
		cliente.setEmail(txtEmail.getText());
		return cliente;
	}
}
