package br.unicentro.poo.projetopoo.Controller.TabCadastro;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Consulta;
import br.unicentro.poo.projetopoo.Model.Veterinario;
import br.unicentro.poo.projetopoo.Service.ConsultaService;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TabCadastroConsultaController {

    private final ConsultaService consultaService = new ConsultaService();

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

        Animal animal = new Animal();
        animal.setId(idAnimal);

        Veterinario veterinario = new Veterinario();
        veterinario.setCrmv(crmvVet);

        Consulta consulta = new Consulta();
        consulta.setData(data);
        consulta.setValor(valorConsulta);
        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);

        try {
            consultaService.cadastrarConsulta(consulta);
            limparCampos();
            Alertas.mostrarSucesso("Sucesso ao cadastrar consulta");
        } catch (ServiceException e){
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
