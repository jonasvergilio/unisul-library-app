package br.unisul.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.unisul.library.model.Livro;
import br.unisul.library.util.Conexao;
import br.unisul.library.util.Messages;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LivroController {

	@FXML
	TextField txtTitulo;
	@FXML
	TextField txtAutor;
	@FXML
	TextField txtIsbn;
	@FXML
	TextField txtQuantidade;

	@FXML
	TextField txtFiltro;

	@FXML
	TableView<Livro> livroTable;

	@FXML
	TableColumn<Livro, Number> colCodigo;

	@FXML
	TableColumn<Livro, String> colTitulo;

	@FXML
	TableColumn<Livro, String> colAutor;

	@FXML
	TableColumn<Livro, String> colIsbn;

	@FXML
	TableColumn<Livro, Number> colQuantidade;

	private ArrayList<Livro> livros = new ArrayList<Livro>();
	private Livro selectedLivro = null;

	// Public methods

	@FXML
	public void initialize() {
		colCodigo.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
		colAutor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
		colIsbn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
		colQuantidade.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty());

		selectAll();
	}

	@FXML
	public void save() {
		if (selectedLivro == null) {
			insert();

			clearLivroScreen();
		} else {
			update();

			clearLivroScreen();
		}
	}

	public void selectLivro() {
		Livro selectedItem = livroTable.getSelectionModel().getSelectedItem();

		if (selectedItem != null) {
			if (selectedLivro == null || selectedItem.getId() != selectedLivro.getId()) {
				selectedLivro = selectedItem;

				txtTitulo.setText(selectedLivro.getTitulo());
				txtAutor.setText(selectedLivro.getAutor());
				txtIsbn.setText(selectedLivro.getIsbn());
				txtQuantidade.setText(selectedLivro.getQuantidade() + "");

				return;
			}
		}

		clearLivroScreen();
	}

	@FXML
	public void selectLivroFilteringByName() {
		if (txtFiltro.getText().trim() == "") {
			selectAll();
		} else {
			String sqlSelectAllByName = "SELECT * FROM livro WHERE titulo LIKE ? ORDER BY id";

			try {
				Connection connection = Conexao.conectaSqlite();

				PreparedStatement ps = connection.prepareStatement(sqlSelectAllByName);

				ps.setString(1, txtFiltro.getText() + "%");

				ResultSet result = ps.executeQuery();

				livros = new ArrayList<Livro>();

				while (result.next()) {
					Livro livro = new Livro(result.getInt("id"), result.getString("titulo"), result.getString("autor"),
							result.getString("isbn"), result.getInt("quantidade"));

					livros.add(livro);
				}

				connection.close();

				livroTable.setItems(FXCollections.observableArrayList(livros));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Private methods

	private void clearLivroScreen() {
		selectedLivro = null;
		livroTable.getSelectionModel().clearSelection();

		txtTitulo.setText("");
		txtAutor.setText("");
		txtIsbn.setText("");
		txtQuantidade.setText("");
	}

	private Livro getLivroScreen() {
		String titulo = txtTitulo.getText().trim(), autor = txtAutor.getText().trim(), isbn = txtIsbn.getText().trim(),
				quantidade = txtQuantidade.getText().trim();

		if (titulo == "" || autor == "" || isbn == "" || quantidade == "") {

			Messages.errorMessage("Erro", "Preencha todos os dados para salvar o livro!");

			return null;
		}

		Livro livro = new Livro(titulo, autor, isbn, Integer.parseInt(quantidade));

		if (selectedLivro != null) {
			livro.setId(selectedLivro.getId());
		}

		return livro;
	}

	private void insert() {
		String sql = "INSERT INTO livro (titulo, autor, isbn, quantidade) VALUES (?, ?, ?, ?)";

		Livro livro = getLivroScreen();

		if (livro != null) {
			try {
				Connection connection = Conexao.conectaSqlite();
				PreparedStatement ps = connection.prepareStatement(sql);

				ps.setString(1, livro.getTitulo());
				ps.setString(2, livro.getAutor());
				ps.setString(3, livro.getIsbn());
				ps.setInt(4, livro.getQuantidade());

				ps.executeUpdate();

				connection.close();

				selectAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void selectAll() {
		String sqlSelectAll = "SELECT * FROM livro";

		try {
			Connection connection = Conexao.conectaSqlite();

			PreparedStatement ps = connection.prepareStatement(sqlSelectAll);

			ResultSet result = ps.executeQuery();

			livros = new ArrayList<Livro>();

			while (result.next()) {
				Livro livro = new Livro(result.getInt("id"), result.getString("titulo"), result.getString("autor"),
						result.getString("isbn"), result.getInt("quantidade"));

				livros.add(livro);
			}

			connection.close();

			livroTable.setItems(FXCollections.observableArrayList(livros));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update() {
		String updateSql = "UPDATE livro SET titulo=?, autor=?, isbn=?, quantidade=? WHERE id=?";

		Livro livro = getLivroScreen();

		if (livro != null) {
			try {
				Connection connection = Conexao.conectaSqlite();

				PreparedStatement ps = connection.prepareStatement(updateSql);

				ps.setString(1, livro.getTitulo());
				ps.setString(2, livro.getAutor());
				ps.setString(3, livro.getIsbn());
				ps.setInt(4, livro.getQuantidade());
				ps.setInt(5, livro.getId());

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

		if (selectedLivro == null) {
			Messages.errorMessage("Erro", "Selecione um livro antes de excluir!");
			return;
		}
		String deleteConfirmation = Messages.confirmationMessage("Confirmação", "Deseja Excluir?").getText();
		if (!deleteConfirmation.equals("OK")) {
			return;
		}
		try {
			PreparedStatement ps = null;
			Connection connection = Conexao.conectaSqlite();

			if (selectedLivro.getQuantidade() > 1) {

				String sql = "UPDATE livro SET quantidade =" + (selectedLivro.getQuantidade() - 1) + " WHERE id=?";

				ps = connection.prepareStatement(sql);

			} else {
				String sqlDelete = "delete from livro where id=?";

				ps = connection.prepareStatement(sqlDelete);

			}

			ps.setInt(1, selectedLivro.getId());

			ps.executeUpdate();

			connection.close();

			selectAll();

			clearLivroScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
