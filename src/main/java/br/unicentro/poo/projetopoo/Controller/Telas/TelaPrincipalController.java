package br.unicentro.poo.projetopoo.Controller.Telas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class TelaPrincipalController {

    public BorderPane TelaPrincipal;
    @FXML
    private void carregarTela(String nomeFxml) {
        try {
            Parent tela = FXMLLoader.load( //intelij completou
                    Objects.requireNonNull(getClass().getResource("/br/unicentro/poo/projetopoo/View/Telas/" + nomeFxml))
            );
            TelaPrincipal.setCenter(tela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * A tela principal é contada como uma tela separada das demais
    * para que ela não carregasse vazia ela carrega a tela de cadastro (já que é o primeiro botão)
    * */
    @FXML
    public void initialize(){
        carregarTela("TelaCadastro.fxml");
    }

    @FXML
    private Button bntSair;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCadastro;

    @FXML
    private Button btnConsulta;

    @FXML
    private Button btnExcluir;

    @FXML
    void btnAtualizarOnAction(ActionEvent event) {
        carregarTela("TelaAtualizar.fxml");
    }

    @FXML
    void btnCadastroOnAction(ActionEvent event) {
        carregarTela("TelaCadastro.fxml");
    }

    @FXML
    void btnConsultaOnAction(ActionEvent event) {
        carregarTela("TelaConsulta.fxml");
    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
        carregarTela("TelaExcluir.fxml");
    }

    @FXML
    void btnSairOnAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void btnVeterinariosOnAction() { carregarTela("TelaVeterinario.fxml"); }

    @FXML
    void btnConsultasOnAction() { carregarTela("TelaConsulta.fxml"); }
}
