package br.unicentro.poo.projetopoo.Controller.TabAtualizar;

import br.unicentro.poo.projetopoo.Controller.Entidades.VeterinarioController;
import br.unicentro.poo.projetopoo.Model.Veterinario;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


import java.time.LocalDate;
import java.util.List;

public class TabAtualizarVeterinarioController {

    private Veterinario veterinarioAtual;
    @FXML
    private Button btnBuscarVeterinario;

    @FXML
    private Button btnSalvarVeterinario;

    @FXML
    private DatePicker dpAtualizarDataVeterinario;

    @FXML
    private TextField txtAtualizarCrmvVeterinario;

    @FXML
    private TextField txtAtualizarEspecializacaoVeterinario;

    @FXML
    private TextField txtAtualizarNomeVeterinario;

    @FXML
    private TextField txtAtualizarTelefoneVeterinario;

    @FXML
    private TextField txtBuscarCrmvVeterinario;

    @FXML
    void btnBuscarClienteOnAction(ActionEvent event) {
        String buscaCrmv = txtBuscarCrmvVeterinario.getText().trim();

        if (buscaCrmv.isEmpty()) {
            Alertas.mostrarAviso("Insira o CRMV para buscar o veterinário");
            txtBuscarCrmvVeterinario.requestFocus();
            return;
        }

        try {
            VeterinarioController veterinarioController = new VeterinarioController();
            List<Veterinario> veterinarios = veterinarioController.buscarVeterinarios(buscaCrmv);

            Veterinario v = veterinarios.stream()
                    .filter(vet -> vet.getCrmv().equalsIgnoreCase(buscaCrmv))
                    .findFirst()
                    .orElse(null);

            if (v == null) {
                Alertas.mostrarAviso("Nenhum veterinário encontrado com o CRMV: " + buscaCrmv);
                limparCampos();
                return;
            }

            veterinarioAtual = v;
            txtAtualizarCrmvVeterinario.setText(v.getCrmv());
            txtAtualizarNomeVeterinario.setText(v.getNome());
            dpAtualizarDataVeterinario.setValue(v.getDataEntrada());
            txtAtualizarEspecializacaoVeterinario.setText(v.getEspecializacao());
            txtAtualizarTelefoneVeterinario.setText(v.getTelefone());

            Alertas.mostrarSucesso("Veterinário encontrado!");

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao buscar veterinário: " + e.getMessage());
        }
    }

    @FXML
    void btnSalvarVeterinarioOnAction(ActionEvent event) {
        if (veterinarioAtual == null) {
            Alertas.mostrarAviso("Nenhum veterinário selecionado para atualizar. Faça a busca primeiro.");
            return;
        }

        try {
            String crmvFixo = veterinarioAtual.getCrmv(); // O CRMV não muda pois é a chave primária
            String nome = txtAtualizarNomeVeterinario.getText().trim();
            LocalDate dataEntrada = dpAtualizarDataVeterinario.getValue();
            String especializacao = txtAtualizarEspecializacaoVeterinario.getText().trim();
            String telefone = txtAtualizarTelefoneVeterinario.getText().trim();

            if (nome.isEmpty()) {
                Alertas.mostrarAviso("O nome do veterinário é obrigatório!");
                return;
            }

            if (dataEntrada == null) {
                Alertas.mostrarAviso("A data de entrada é obrigatória!");
                return;
            }

            VeterinarioController veterinarioController = new VeterinarioController();
            veterinarioController.atualizarVeterinario(crmvFixo, nome, dataEntrada, especializacao, telefone);

            Alertas.mostrarSucesso("Veterinário atualizado com sucesso!");
            limparCampos();

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao atualizar veterinário: " + e.getMessage());
        }
    }

    private void limparCampos() {
        veterinarioAtual = null;
        txtAtualizarCrmvVeterinario.clear();
        txtAtualizarNomeVeterinario.clear();
        dpAtualizarDataVeterinario.setValue(null);
        txtAtualizarEspecializacaoVeterinario.clear();
        txtAtualizarTelefoneVeterinario.clear();
        txtBuscarCrmvVeterinario.clear();
    }

}
