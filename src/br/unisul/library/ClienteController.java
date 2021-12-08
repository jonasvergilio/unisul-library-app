package br.unisul.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.unisul.library.model.Cliente;
import br.unisul.library.util.Conexao;
import br.unisul.library.util.Messages;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	TextField txtFiltro;

	@FXML
	TableView<Cliente> clienteTable;

	@FXML
	TableColumn<Cliente, Number> colCodigo;

	@FXML
	TableColumn<Cliente, String> colNome;

	@FXML
	TableColumn<Cliente, String> colCpf;

	@FXML
	TableColumn<Cliente, String> colNascimento;

	@FXML
	TableColumn<Cliente, String> colTelefone;

	@FXML
	TableColumn<Cliente, String> colEmail;

	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private Cliente selectedCliente = null;

	// Public methods

	@FXML
	public void initialize() {
		colCodigo.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colCpf.setCellValueFactory(cellData -> cellData.getValue().cpfProperty());
		colNascimento.setCellValueFactory(cellData -> cellData.getValue().nascimentoProperty());
		colTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
		colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

		selectAll();
	}

	@FXML
	public void save() {
		if (selectedCliente == null) {
			insert();

			clearClienteScreen();
		} else {
			update();

			clearClienteScreen();
		}
	}

	public void selectCliente() {
		Cliente selectedItem = clienteTable.getSelectionModel().getSelectedItem();

		if (selectedItem != null) {
			if (selectedCliente == null || selectedItem.getId() != selectedCliente.getId()) {
				selectedCliente = selectedItem;

				txtNome.setText(selectedCliente.getNome());
				txtCpf.setText(selectedCliente.getCpf());
				txtNascimento.setText(selectedCliente.getNascimento());
				txtTelefone.setText(selectedCliente.getTelefone());
				txtEmail.setText(selectedCliente.getEmail());

				return;
			}
		}

		clearClienteScreen();
	}

	@FXML
	public void selectClienteFilteringByName() {
		if (txtFiltro.getText().trim() == "") {
			selectAll();
		} else {
			String sqlSelectAllByName = "SELECT * FROM cliente WHERE nome LIKE ? ORDER BY id";

			try {
				Connection connection = Conexao.conectaSqlite();

				PreparedStatement ps = connection.prepareStatement(sqlSelectAllByName);

				ps.setString(1, txtFiltro.getText() + "%");

				ResultSet result = ps.executeQuery();

				clientes = new ArrayList<Cliente>();

				while (result.next()) {
					Cliente cliente = new Cliente(result.getInt("id"), result.getString("nome"),
							result.getString("cpf"), result.getString("nascimento"), result.getString("telefone"),
							result.getString("email"));

					clientes.add(cliente);
				}

				connection.close();

				clienteTable.setItems(FXCollections.observableArrayList(clientes));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Private methods

	private void clearClienteScreen() {
		selectedCliente = null;
		clienteTable.getSelectionModel().clearSelection();

		txtNome.setText("");
		txtCpf.setText("");
		txtNascimento.setText("");
		txtTelefone.setText("");
		txtEmail.setText("");
	}

	private Cliente getClienteScreen() {
		String nome = txtNome.getText().trim(), cpf = txtCpf.getText().trim(),
				nascimento = txtNascimento.getText().trim(), telefone = txtTelefone.getText().trim(),
				email = txtEmail.getText().trim();

		if (nome == "" || cpf == "" || nascimento == "" || telefone == "" || email == "") {
			Messages.errorMessage("Erro", "Preencha todos os dados para salvar o cliente!");

			return null;
		}

		Cliente cliente = new Cliente(nome, cpf, nascimento, telefone, email);

		if (selectedCliente != null) {
			cliente.setId(selectedCliente.getId());
		}

		return cliente;
	}

	private void insert() {
		String sql = "INSERT INTO cliente (nome, cpf, nascimento, telefone, email) VALUES (?, ?, ?, ?, ?)";

		Cliente cliente = getClienteScreen();

		if (cliente != null) {
			try {
				Connection connection = Conexao.conectaSqlite();
				PreparedStatement ps = connection.prepareStatement(sql);

				ps.setString(1, cliente.getNome());
				ps.setString(2, cliente.getCpf());
				ps.setString(3, cliente.getNascimento());
				ps.setString(4, cliente.getTelefone());
				ps.setString(5, cliente.getEmail());

				ps.executeUpdate();

				connection.close();

				selectAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void selectAll() {
		String sqlSelectAll = "SELECT * FROM cliente";

		try {
			Connection connection = Conexao.conectaSqlite();

			PreparedStatement ps = connection.prepareStatement(sqlSelectAll);

			ResultSet result = ps.executeQuery();

			clientes = new ArrayList<Cliente>();

			while (result.next()) {
				Cliente cliente = new Cliente(result.getInt("id"), result.getString("nome"), result.getString("cpf"),
						result.getString("nascimento"), result.getString("telefone"), result.getString("email"));

				clientes.add(cliente);
			}

			connection.close();

			clienteTable.setItems(FXCollections.observableArrayList(clientes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update() {
		String updateSql = "UPDATE cliente SET nome=?, cpf=?, nascimento=?, telefone=?, email=? WHERE id=?";

		Cliente cliente = getClienteScreen();

		if (cliente != null) {
			try {
				Connection connection = Conexao.conectaSqlite();

				PreparedStatement ps = connection.prepareStatement(updateSql);

				ps.setString(1, cliente.getNome());
				ps.setString(2, cliente.getCpf());
				ps.setString(3, cliente.getNascimento());
				ps.setString(4, cliente.getTelefone());
				ps.setString(5, cliente.getEmail());
				ps.setInt(6, cliente.getId());

				ps.executeUpdate();

				connection.close();

				selectAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void delete() {
		String sqlDelete = "DELETE FROM cliente WHERE id=?";

		if (selectedCliente == null) {
			Messages.errorMessage("Erro", "Selecione um cliente antes de excluir!");
			return;
		}
		
		try {
			Connection connection = Conexao.conectaSqlite();
			PreparedStatement ps = connection.prepareStatement(sqlDelete);

			ps.setInt(1, selectedCliente.getId());

			ps.executeUpdate();

			connection.close();

			selectAll();
			
			clearClienteScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
