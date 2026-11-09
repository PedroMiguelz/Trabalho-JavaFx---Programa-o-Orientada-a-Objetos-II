package br.unicentro.poo.projetopoo.Controller.TabAtualizar;

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


public class TabAtualizarConsultaController {

    private final ConsultaService consultaService = new ConsultaService();

    private Consulta consultaAtual;

    @FXML
    private Button btnBuscarConsulta;

    @FXML
    private Button btnSalvarConsulta;

    @FXML
    private DatePicker dpAtualizarDataConsulta;

    @FXML
    private TextField txtAtualizarCrmvConsulta;

    @FXML
    private TextField txtAtualizarIdAnimal;

    @FXML
    private TextField txtAtualizarIdConsulta;

    @FXML
    private TextField txtAtualizarValorConsulta;

    @FXML
    private TextField txtBuscarIdConsulta;

    @FXML
    void btnBuscarConsultaOnAction(ActionEvent event) {
        String buscaIdConsulta = txtBuscarIdConsulta.getText().trim();

        if(buscaIdConsulta.isEmpty()){
            Alertas.mostrarAviso("Insira o ID da consulta");
            txtBuscarIdConsulta.requestFocus();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(buscaIdConsulta);
        } catch (NumberFormatException e) {
            Alertas.mostrarErro("Informe um Id válido");
            txtBuscarIdConsulta.requestFocus();
            return;
        }

        try {
            Consulta c = consultaService.listarConsulta(id).orElse(null);

            if (c == null) {
                Alertas.mostrarAviso("Nenhuma consulta encontrada");
                limparCampos();
                return;
            }

            consultaAtual = c;
            txtAtualizarIdConsulta.setText(String.valueOf(c.getId()));
            dpAtualizarDataConsulta.setValue(c.getData());
            txtAtualizarValorConsulta.setText(c.getValor() != null ? c.getValor().toString() : "");
            txtAtualizarIdAnimal.setText(String.valueOf(c.getAnimal().getId()));
            txtAtualizarCrmvConsulta.setText(c.getVeterinario().getCrmv());

            Alertas.mostrarSucesso("Consulta encontrada!");

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao buscar consulta: " + e.getMessage());
        }
    }

    @FXML
    void btnSalvarConsultaOnAction(ActionEvent event) {
        if (consultaAtual == null) {
            Alertas.mostrarAviso("Nenhuma consulta selecionada para atualizar. Faça a busca primeiro.");
            return;
        }

        try {
            int idConsulta = consultaAtual.getId();
            var data = dpAtualizarDataConsulta.getValue();
            String valorStr = txtAtualizarValorConsulta.getText().trim();
            String idAnimalStr = txtAtualizarIdAnimal.getText().trim();
            String crmvVet = txtAtualizarCrmvConsulta.getText().trim();

            BigDecimal valor;
            try {
                valor = new BigDecimal(valorStr);
            } catch (NumberFormatException e) {
                Alertas.mostrarErro("Valor da consulta inválido!");
                return;
            }

            int idAnimal;
            try {
                idAnimal = Integer.parseInt(idAnimalStr);
            } catch (NumberFormatException e) {
                Alertas.mostrarErro("Id do animal inválido!");
                return;
            }

            Animal animal = new Animal();
            animal.setId(idAnimal);

            Veterinario veterinario = new Veterinario();
            veterinario.setCrmv(crmvVet);

            Consulta consulta = new Consulta();
            consulta.setId(idConsulta);
            consulta.setData(data);
            consulta.setValor(valor);
            consulta.setAnimal(animal);
            consulta.setVeterinario(veterinario);

            consultaService.atualizarConsulta(consulta);

            Alertas.mostrarSucesso("Consulta atualizada com sucesso!");
            limparCampos();

        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        } catch (ServiceException e) {
            Alertas.mostrarErro("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    private void limparCampos() {
        consultaAtual = null;
        txtAtualizarIdConsulta.clear();
        dpAtualizarDataConsulta.setValue(null);
        txtAtualizarValorConsulta.clear();
        txtAtualizarIdAnimal.clear();
        txtAtualizarCrmvConsulta.clear();
        txtBuscarIdConsulta.clear();
    }

}
