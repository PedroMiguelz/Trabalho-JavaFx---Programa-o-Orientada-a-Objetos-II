package br.unicentro.poo.projetopoo.Controller.TabConsulta;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Service.AnimalService;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

public class TabConsultaAnimalController {

    private final AnimalService animalService = new AnimalService();

    @FXML
    private Button btnLimparAnimal;

    @FXML
    private Button btnConsultarAnimal;

    @FXML
    private TextField txtConsultarIdAnimal;

    @FXML
    private TextField txtConsultarNomeAnimal;

    @FXML
    private TextField txtResultadoClienteAnimal;

    @FXML
    private TextField txtResultadoEspecieAnimal;

    @FXML
    private TextField txtResultadoIdAnimal;

    @FXML
    private TextField txtResultadoNomeAnimal;

    @FXML
    private TextField txtResultadoRacaAnimal;

    @FXML
    private TextField txtResultadoSexoAnimal;


    @FXML
    void btnConsultarAnimalOnAction(ActionEvent event) {
        String buscaId = txtConsultarIdAnimal.getText().trim();
        String buscaNome = txtConsultarNomeAnimal.getText().trim();

        try {
            Animal a = null;

            if (!buscaId.isEmpty()) {
                int id;
                try {
                    id = Integer.parseInt(buscaId);
                } catch (NumberFormatException e) {
                    Alertas.mostrarErro("O ID deve ser um número inteiro válido.");
                    return;
                }
                a = animalService.listarAnimalId(id);
            }
            else if (!buscaNome.isEmpty()) {
                List<Animal> animais = animalService.listarAnimal(buscaNome);
                if (!animais.isEmpty()) {
                    a = animais.get(0);
                }
            }
            else {
                Alertas.mostrarAviso("Insira o ID ou o Nome do animal para consultar");
                return;
            }

            if (a == null) {
                Alertas.mostrarAviso("Nenhum animal encontrado");
                limparCampos();
                return;
            }

            txtResultadoIdAnimal.setText(String.valueOf(a.getId()));
            txtResultadoNomeAnimal.setText(a.getNome());
            txtResultadoEspecieAnimal.setText(a.getEspecie());
            txtResultadoRacaAnimal.setText(a.getRaca());
            txtResultadoSexoAnimal.setText(a.getSexo());

            if (a.getCliente() != null) {
                txtResultadoClienteAnimal.setText(a.getCliente().getCpf());
            } else {
                txtResultadoClienteAnimal.setText("");
            }

            Alertas.mostrarSucesso("Animal encontrado!");

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao consultar animal: " + e.getMessage());
        }
    }

    @FXML
    void btnLimparAnimalOnAction(ActionEvent event) {
        limparCampos();
    }

    private void limparCampos() {
        txtConsultarIdAnimal.clear();
        txtConsultarNomeAnimal.clear();
        txtResultadoIdAnimal.clear();
        txtResultadoNomeAnimal.clear();
        txtResultadoEspecieAnimal.clear();
        txtResultadoRacaAnimal.clear();
        txtResultadoSexoAnimal.clear();
        txtResultadoClienteAnimal.clear();
    }

}
