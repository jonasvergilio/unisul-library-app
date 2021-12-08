package br.unisul.library.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class Messages {
  private static Alert message(String header, String message, AlertType type) {
    Alert alert = new Alert(type);

    alert.setHeaderText(header);
    alert.setContentText(message);
    alert.initStyle(StageStyle.UNDECORATED);
    alert.getDialogPane().setStyle("-fx-border-color: black; -fx-border-width: 3;");

    return alert;
  }

  public static void errorMessage(String cabecalho, String msg) {
    message(cabecalho, msg, AlertType.ERROR).showAndWait();
  }

  public static void attentionMessage(String cabecalho, String msg) {
    message(cabecalho, msg, AlertType.WARNING).showAndWait();
  }

  public static void infoMessage(String cabecalho, String msg) {
    message(cabecalho, msg, AlertType.INFORMATION).showAndWait();
  }

  public static void confirmedMessage(String cabecalho, String msg) {
    message(cabecalho, msg, AlertType.CONFIRMATION).showAndWait();
  }

  public static ButtonType confirmationMessage(String cabecalho, String msg) {
    Optional<ButtonType> r = message(cabecalho, msg, AlertType.CONFIRMATION).showAndWait();
    return r.get();
  }
}
