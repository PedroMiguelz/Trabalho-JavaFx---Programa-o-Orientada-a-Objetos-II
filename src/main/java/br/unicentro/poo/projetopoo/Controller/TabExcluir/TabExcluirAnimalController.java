package br.unicentro.poo.projetopoo.Controller.TabExcluir;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Service.AnimalService;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import br.unicentro.poo.projetopoo.Facade.AnimalFacade;

import java.util.List;
import java.util.Optional;

public class TabExcluirAnimalController {

   private final AnimalService animalService = ServiceFactory.criarAnimalService();
    private final AnimalFacade animalFacade = new AnimalFacade();
    private Animal animalAtual;

    @FXML
    private Button btnBuscarExcluirAnimal;

    @FXML
    private Button btnExcluirAnimal;

    @FXML
    private TextField txtExcluirIdAnimal;

    @FXML
    private TextField txtExcluirNomeAnimal;

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
    void btnBuscarExcluirAnimalOnAction(ActionEvent event) {
        String buscaIdStr = txtExcluirIdAnimal.getText().trim();
        String buscaNome = txtExcluirNomeAnimal.getText().trim();

        try {
            Animal a = null;

            if (!buscaIdStr.isEmpty()) {
                int id;
                try {
                    id = Integer.parseInt(buscaIdStr);
                } catch (NumberFormatException e) {
                    Alertas.mostrarErro("O id deve ser um número válido!");
                    txtExcluirIdAnimal.requestFocus();
                    return;
                }
                a = animalService.listarAnimalId(id);
            } else if (!buscaNome.isEmpty()) {
                List<Animal> animais = animalService.listarAnimal(buscaNome);
                if (!animais.isEmpty()) {
                    a = animais.get(0);
                }
            } else {
                Alertas.mostrarAviso("Insira o id ou o nome do animal para buscar!");
                txtExcluirIdAnimal.requestFocus();
                return;
            }

            if (a == null) {
                Alertas.mostrarAviso("Nenhum animal encontrado!");
                limparCampos();
                return;
            }

            animalAtual = a;
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

        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        }
        catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
        }
    }

    @FXML
    void btnExcluirAnimalOnAction(ActionEvent event) {
        if (animalAtual == null) {
            Alertas.mostrarAviso("Nenhum animal selecionado para excluir. Faça a busca primeiro!");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este animal?");
        confirmacao.setContentText("Animal: " + animalAtual.getNome() + " (ID: " + animalAtual.getId() + ")");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                animalFacade.excluirAnimal(animalAtual);
                Alertas.mostrarSucesso("Animal excluído com sucesso!");
                limparCampos();

            }catch (IllegalArgumentException e){
                Alertas.mostrarAviso(e.getMessage());
            }
            catch (ServiceException e) {
                Alertas.mostrarErro(e.getMessage());
            }
        }
    }

    private void limparCampos() {
        animalAtual = null;
        txtExcluirIdAnimal.clear();
        txtExcluirNomeAnimal.clear();
        txtResultadoIdAnimal.clear();
        txtResultadoNomeAnimal.clear();
        txtResultadoEspecieAnimal.clear();
        txtResultadoRacaAnimal.clear();
        txtResultadoSexoAnimal.clear();
        txtResultadoClienteAnimal.clear();
        txtExcluirIdAnimal.requestFocus();
    }

}
