package br.unisul.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.unisul.library.model.Cliente;
import br.unisul.library.util.Conexao;
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
		if(selectedCliente == null) {
			insertCliente();
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
		Cliente cliente = new Cliente(txtNome.getText(), txtCpf.getText(), txtNascimento.getText(), txtTelefone.getText(), txtEmail.getText());

		if (selectedCliente != null) {
			cliente.setId(selectedCliente.getId());
		}

		return cliente;
	}
	
	private void insertCliente() {
		String sql = "INSERT INTO cliente (nome, cpf, nascimento, telefone, email) VALUES (?, ?, ?, ?, ?)";
		
		Cliente cliente = getClienteScreen();
		
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
		} catch (Exception e) {
			e.printStackTrace();
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
	        Cliente cliente = new Cliente(result.getInt("id"), result.getString("nome"), result.getString("cpf"), result.getString("nascimento"), result.getString("telefone"), result.getString("email"));

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
