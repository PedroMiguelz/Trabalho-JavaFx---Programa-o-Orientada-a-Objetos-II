package br.unicentro.poo.projetopoo.Controller.TabExcluir;

import br.unicentro.poo.projetopoo.Controller.Entidades.VeterinarioController;
import br.unicentro.poo.projetopoo.Model.Veterinario;
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

public class TabExcluirVeterinarioController {

    private Veterinario veterinarioAtual;

    @FXML
    private Button btnConsultarVeterinario;

    @FXML
    private Button btnExcluirVeterinario;

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

        String termoBusca = !buscaCrmv.isEmpty() ? buscaCrmv : buscaNome;

        if (termoBusca.isEmpty()) {
            Alertas.mostrarAviso("Insira o CRMV ou o Nome para buscar o veterinário");
            txtConsultarCrmvVeterinario.requestFocus();
            return;
        }

        try {
            VeterinarioController veterinarioController = new VeterinarioController();
            List<Veterinario> veterinarios = veterinarioController.buscarVeterinarios(termoBusca);

            Veterinario v = veterinarios.stream()
                    .filter(vet -> (!buscaCrmv.isEmpty() && vet.getCrmv().equalsIgnoreCase(buscaCrmv)) ||
                            (!buscaNome.isEmpty() && vet.getNome().toLowerCase().contains(buscaNome.toLowerCase())))
                    .findFirst()
                    .orElse(!veterinarios.isEmpty() ? veterinarios.get(0) : null);

            if (v == null) {
                Alertas.mostrarAviso("Nenhum veterinário encontrado");
                limparCampos();
                return;
            }

            veterinarioAtual = v;
            txtResultadoCrmvVeterinario.setText(v.getCrmv());
            txtResultadoNomeVeterinario.setText(v.getNome());
            dpResultadoDataVeterinario.setValue(v.getDataEntrada());
            txtResultadoEspecializacaoVeterinario.setText(v.getEspecializacao());
            txtResultadoTelefoneVeterinario.setText(v.getTelefone());

            Alertas.mostrarSucesso("Veterinário encontrado!");

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao buscar veterinário: " + e.getMessage());
        }
    }

    @FXML
    void btnExcluirVeterinarioOnAction(ActionEvent event) {
        if (veterinarioAtual == null) {
            Alertas.mostrarAviso("Nenhum veterinário selecionado para excluir. Faça a busca primeiro.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este veterinário?");
        confirmacao.setContentText("Veterinário: " + veterinarioAtual.getNome() + " (CRMV: " + veterinarioAtual.getCrmv() + ")");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                VeterinarioController veterinarioController = new VeterinarioController();
                veterinarioController.excluirVeterinario(veterinarioAtual.getCrmv());

                Alertas.mostrarSucesso("Veterinário excluído com sucesso!");
                limparCampos();

            } catch (Exception e) {
                Alertas.mostrarErro("Erro ao excluir veterinário: " + e.getMessage());
            }
        }
    }

    private void limparCampos() {
        veterinarioAtual = null;
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