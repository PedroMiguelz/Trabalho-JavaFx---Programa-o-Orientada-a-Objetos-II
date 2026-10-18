package br.unicentro.poo.projetopoo.Controller.TabCadastro;

import br.unicentro.poo.projetopoo.Controller.Entidades.ConsultaController;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TabCadastroConsultaController {

    @FXML
    private Button btnAceitarConsulta;

    @FXML
    private Button btnCancelarConsulta;

    @FXML
    private DatePicker dpDataConsulta;

    @FXML
    private TextField txtCrmvVeterinarioConsulta;

    @FXML
    private TextField txtIdAnimalConsulta;

    @FXML
    private TextField txtValorConsulta;

    @FXML
    void btnAceitarConsultaOnAction(ActionEvent event) {
        LocalDate data = dpDataConsulta.getValue();
        String valor = txtValorConsulta.getText().trim();
        BigDecimal valorConsulta;
        try {
            valorConsulta = new BigDecimal(valor.replace(",", "."));
        } catch (NumberFormatException e) {
            Alertas.mostrarAviso("Digite um valor válido");
            txtValorConsulta.requestFocus();
            return;
        }

        String idAnimalt = txtIdAnimalConsulta.getText().trim();
        int idAnimal;
        try {
            idAnimal = Integer.parseInt(idAnimalt);
        } catch (NumberFormatException e) {
            Alertas.mostrarAviso("O id do animal deve ser um número");
            txtIdAnimalConsulta.requestFocus();
            return;
        }
        String crmvVet = txtCrmvVeterinarioConsulta.getText();


        if(data == null){
            Alertas.mostrarAviso("Informe a data da consulta");
            dpDataConsulta.requestFocus();
            return;
        }

        if(valor.isEmpty()){
            Alertas.mostrarAviso("Informe o valor da consulta");
            txtValorConsulta.requestFocus();
            return;
        }

        if(idAnimalt.isEmpty()){
            Alertas.mostrarAviso("Informe o id do animal");
            txtIdAnimalConsulta.requestFocus();
            return;
        }

        if(crmvVet.isEmpty()){
            Alertas.mostrarAviso("Informe o crmv do veterinário");
            txtCrmvVeterinarioConsulta.requestFocus();
            return;
        }

        //criar o controller
        try {
            ConsultaController consultaController = new ConsultaController();
            consultaController.cadastrarConsulta(data,valorConsulta,idAnimal,crmvVet);

            limparCampos();

            Alertas.mostrarSucesso("Sucesso ao cadastrar");

        } catch(Exception e){
            e.printStackTrace();
            Alertas.mostrarErro("Erro ao cadastrar consulta: " + e.getMessage());
        }
    }

    @FXML
    private void btnCancelarConsultaOnAction(ActionEvent event) {
        limparCampos();

        Alertas.mostrarAviso("Operação cancelada");
    }

    private void limparCampos(){
        dpDataConsulta.setValue(null);
        txtValorConsulta.clear();
        txtCrmvVeterinarioConsulta.clear();
    }

}
