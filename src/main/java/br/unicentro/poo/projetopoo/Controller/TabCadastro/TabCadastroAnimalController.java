package br.unicentro.poo.projetopoo.Controller.TabCadastro;

import br.unicentro.poo.projetopoo.Controller.Entidades.AnimalController;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class TabCadastroAnimalController {

    @FXML
    private Button btnAceitarAnimal;

    @FXML
    private Button btnCancelarAnimal;

    // Para escolher o sexo do animal
    @FXML
    private ComboBox<String> cbSexo;

    @FXML
    private TextField txtCpfClienteAnimal;

    @FXML
    private TextField txtEspecieAnimal;

    @FXML
    private TextField txtNomeAnimal;

    @FXML
    private TextField txtRacaAnimal;


    @FXML
    private void btnAceitarAnimalOnAction(ActionEvent event) {
        String nome = txtNomeAnimal.getText();
        String raca = txtRacaAnimal.getText();
        String sexo = cbSexo.getValue();
        String especie = txtEspecieAnimal.getText();
        String cpfDono = txtCpfClienteAnimal.getText();

        if(nome.isEmpty()){
            Alertas.mostrarAviso("Insira o nome do animal");
            txtNomeAnimal.requestFocus();
            return;
        }

        if(raca.isEmpty()){
            Alertas.mostrarAviso("Insira a raca do animal");
            txtRacaAnimal.requestFocus();
            return;
        }

        if(sexo == null || sexo.isEmpty()){
            Alertas.mostrarAviso("Selecione o sexo do animal");
            cbSexo.requestFocus();
            return;
        }

        if(especie.isEmpty()){
            Alertas.mostrarAviso("Insira a espécie do animal");
            txtEspecieAnimal.requestFocus();
            return;
        }

        if(cpfDono.isEmpty()){
            Alertas.mostrarAviso("Insira o cpf do dono do animal");
            txtCpfClienteAnimal.requestFocus();
            return;
        }

        try{
            AnimalController animalController = new AnimalController();
            animalController.cadastrarAnimal(nome, especie, raca, sexo, cpfDono);

            Alertas.mostrarSucesso("Sucesso ao cadastrar o animal");

            limparCampos();

        } catch (SQLException e) {
            e.printStackTrace();
            Alertas.mostrarErro("Erro ao cadastrar animal: " + e.getMessage());;
        } catch (Exception e) {
            e.printStackTrace();
            Alertas.mostrarErro("Erro ao processar animal: " + e.getMessage());
        }
    }

    @FXML
    private void btnCancelarAnimalOnAction(ActionEvent event) {
        limparCampos();

        Alertas.mostrarAviso("Operação cancelada");
    }

    //do sexo do animal
    @FXML
    public void initialize() {
        cbSexo.getItems().addAll("M", "F");
    }

    @FXML
    private void salvarAnimal(ActionEvent event) {
        String sexo = cbSexo.getValue();
    }

    private void limparCampos(){
        txtNomeAnimal.clear();
        txtRacaAnimal.clear();
        cbSexo.setValue(null);
        txtEspecieAnimal.clear();
        txtCpfClienteAnimal.clear();
    }
}


