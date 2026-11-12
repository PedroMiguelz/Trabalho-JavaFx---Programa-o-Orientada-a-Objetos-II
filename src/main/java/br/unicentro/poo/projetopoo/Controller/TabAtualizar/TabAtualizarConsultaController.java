package br.unicentro.poo.projetopoo.Controller.TabAtualizar;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Consulta;
import br.unicentro.poo.projetopoo.Model.Veterinario;
import br.unicentro.poo.projetopoo.Service.ConsultaService;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.math.BigDecimal;
import br.unicentro.poo.projetopoo.Facade.ConsultaFacade;


public class TabAtualizarConsultaController {

    private final ConsultaService consultaService = ServiceFactory.criarConsultaService();
    private final ConsultaFacade consultaFacade = new ConsultaFacade();

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
            Alertas.mostrarAviso("Insira o id da consulta");
            txtBuscarIdConsulta.requestFocus();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(buscaIdConsulta);
        } catch (NumberFormatException e) {
            Alertas.mostrarErro("Informe um id válido");
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

        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        } catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
        }
    }

    @FXML
    void btnSalvarConsultaOnAction(ActionEvent event) {
        if (consultaAtual == null) {
            Alertas.mostrarAviso("Nenhuma consulta selecionada para atualizar. Faça a busca primeiro!");
            return;
        }

        try {
            int idConsulta = consultaAtual.getId();
            var data = dpAtualizarDataConsulta.getValue();
            String valorStr = txtAtualizarValorConsulta.getText().trim();
            String idAnimalStr = txtAtualizarIdAnimal.getText().trim();
            String crmvVet = txtAtualizarCrmvConsulta.getText().trim();

            if (crmvVet.isEmpty()) {
                Alertas.mostrarAviso("Informe o CRMV do veterinário!");
                txtAtualizarCrmvConsulta.requestFocus();
                return;
            }

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

            Consulta consulta = Consulta.builder()
                    .id(idConsulta)
                    .data(data)
                    .valor(valor)
                    .animal(Animal.builder().id(idAnimal).build())
                    .veterinario(Veterinario.builder().crmv(crmvVet).build())
                    .build();

            consultaFacade.atualizarConsulta(consulta);
            Alertas.mostrarSucesso("Consulta atualizada com sucesso!");
            limparCampos();

        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        } catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
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
