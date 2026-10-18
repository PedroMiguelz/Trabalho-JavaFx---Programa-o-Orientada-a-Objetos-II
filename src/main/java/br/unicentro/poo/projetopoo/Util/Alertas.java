package br.unicentro.poo.projetopoo.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {
    public static void mostrarAviso(String mensagem){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void mostrarErro(String mensagem){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void mostrarSucesso(String mensagem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
