package br.unicentro.poo.projetopoo.Controller.TabConsulta;

import br.unicentro.poo.projetopoo.Controller.Entidades.VeterinarioController;
import br.unicentro.poo.projetopoo.Model.Veterinario;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.List;

public class TabConsultaVeterinarioController {

    @FXML
    private Button btnConsultarVeterinario;

    @FXML
    private Button btnLimparVeterinario;

    @FXML
    private DatePicker dpResultadoDataVeterinario;

    @FXML
    private TextField txtConsultarCrmvVeterinario;

    @FXML
    private TextField txtConsultarNomeVeterinario;

    @FXML
    private TextField txtResultadoCrmvVeterinario;

    @FXML
    private TextField txtResultadoEspecializacaoVeterinario;

    @FXML
    private TextField txtResultadoNomeVeterinario;

    @FXML
    private TextField txtResultadoTelefoneVeterinario;

    @FXML
    void btnConsultarVeterinarioOnAction(ActionEvent event) {
        String buscaCrmv = txtConsultarCrmvVeterinario.getText().trim();
        String buscaNome = txtConsultarNomeVeterinario.getText().trim();

        if (buscaCrmv.isEmpty() && buscaNome.isEmpty()) {
            Alertas.mostrarAviso("Insira o CRMV ou o Nome para consultar o veterinário");
            txtConsultarCrmvVeterinario.requestFocus();
            return;
        }

        try {
            VeterinarioController veterinarioController = new VeterinarioController();
            Veterinario v = null;

            if (!buscaCrmv.isEmpty()) {
                List<Veterinario> veterinarios = veterinarioController.buscarVeterinarios(buscaCrmv);
                v = veterinarios.stream()
                        .filter(vet -> vet.getCrmv().equalsIgnoreCase(buscaCrmv))
                        .findFirst()
                        .orElse(null);
            }
            else if (!buscaNome.isEmpty()) {
                List<Veterinario> veterinarios = veterinarioController.buscarVeterinarios(buscaNome);
                v = veterinarios.stream()
                        .filter(vet -> vet.getNome().toLowerCase().contains(buscaNome.toLowerCase()))
                        .findFirst()
                        .orElse(null);
            }

            if (v == null) {
                Alertas.mostrarAviso("Nenhum veterinário encontrado");
                limparCampos();
                return;
            }

            txtResultadoCrmvVeterinario.setText(v.getCrmv());
            txtResultadoNomeVeterinario.setText(v.getNome());
            dpResultadoDataVeterinario.setValue(v.getDataEntrada());
            txtResultadoEspecializacaoVeterinario.setText(v.getEspecializacao());
            txtResultadoTelefoneVeterinario.setText(v.getTelefone());

            Alertas.mostrarSucesso("Veterinário encontrado!");

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao consultar veterinário: " + e.getMessage());
        }
    }

        @FXML
        void btnLimparVeterinarioOnAction(ActionEvent event) {
            limparCampos();
        }

        private void limparCampos() {
            txtConsultarCrmvVeterinario.clear();
            txtConsultarNomeVeterinario.clear();
            txtResultadoCrmvVeterinario.clear();
            txtResultadoNomeVeterinario.clear();
            dpResultadoDataVeterinario.setValue(null);
            txtResultadoEspecializacaoVeterinario.clear();
            txtResultadoTelefoneVeterinario.clear();
            txtConsultarCrmvVeterinario.requestFocus();
        }
}
