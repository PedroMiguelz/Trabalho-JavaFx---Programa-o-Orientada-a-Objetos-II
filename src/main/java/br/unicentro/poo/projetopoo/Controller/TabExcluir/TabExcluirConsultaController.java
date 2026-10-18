package br.unicentro.poo.projetopoo.Controller.TabExcluir;

import br.unicentro.poo.projetopoo.Controller.Entidades.ConsultaController;
import br.unicentro.poo.projetopoo.Model.Consulta;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Optional;

public class TabExcluirConsultaController {

    private Consulta consultaAtual;

    @FXML
    private Button btnConsultarConsulta;

    @FXML
    private Button btnExcluirConsulta;

    @FXML
    private DatePicker dpResultadoDataConsulta;

    @FXML
    private TextField txtConsultarIdConsulta;

    @FXML
    private TextField txtResultadoCrmvConsulta;

    @FXML
    private TextField txtResultadoIdAnimal;

    @FXML
    private TextField txtResultadoIdConsulta;

    @FXML
    private TextField txtResultadoValorConsulta;

    @FXML
    void btnConsultarConsultaOnAction(ActionEvent event) {
        String buscaIdStr = txtConsultarIdConsulta.getText().trim();

        if (buscaIdStr.isEmpty()) {
            Alertas.mostrarAviso("Insira o ID da consulta para buscar");
            txtConsultarIdConsulta.requestFocus();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(buscaIdStr);
        } catch (NumberFormatException e) {
            Alertas.mostrarErro("O ID deve ser um número inteiro válido.");
            txtConsultarIdConsulta.requestFocus();
            return;
        }

        try {
            ConsultaController consultaController = new ConsultaController();
            List<Consulta> consultas = consultaController.buscarConsultas(String.valueOf(id));

            consultaAtual = consultas.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (consultaAtual == null) {
                Alertas.mostrarAviso("Nenhuma consulta encontrada com o ID: " + id);
                limparCampos();
                return;
            }

            txtResultadoIdConsulta.setText(String.valueOf(consultaAtual.getId()));
            dpResultadoDataConsulta.setValue(consultaAtual.getData());
            txtResultadoValorConsulta.setText(consultaAtual.getValor() != null ? consultaAtual.getValor().toString() : "");

            if (consultaAtual.getAnimal() != null) {
                txtResultadoIdAnimal.setText(String.valueOf(consultaAtual.getAnimal().getId()));
            } else {
                txtResultadoIdAnimal.setText("");
            }

            if (consultaAtual.getVeterinario() != null) {
                txtResultadoCrmvConsulta.setText(consultaAtual.getVeterinario().getCrmv());
            } else {
                txtResultadoCrmvConsulta.setText("");
            }

            Alertas.mostrarSucesso( "Consulta encontrada!");

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao buscar consulta: " + e.getMessage());
        }
    }

    @FXML
    void btnExcluirConsultaOnAction(ActionEvent event) {
        if (consultaAtual == null) {
            Alertas.mostrarAviso("Nenhuma consulta selecionada para excluir. Faça a busca primeiro.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir esta consulta?");
        confirmacao.setContentText("ID da Consulta: " + consultaAtual.getId());

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                ConsultaController consultaController = new ConsultaController();
                consultaController.excluirConsulta(consultaAtual.getId());

                Alertas.mostrarSucesso("Consulta excluída com sucesso!");
                limparCampos();

            } catch (Exception e) {
                Alertas.mostrarErro("Erro ao excluir consulta: " + e.getMessage());
            }
        }
    }

    private void limparCampos() {
        consultaAtual = null;
        txtConsultarIdConsulta.clear();
        txtResultadoIdConsulta.clear();
        dpResultadoDataConsulta.setValue(null);
        txtResultadoValorConsulta.clear();
        txtResultadoIdAnimal.clear();
        txtResultadoCrmvConsulta.clear();
        txtConsultarIdConsulta.requestFocus();
    }
}