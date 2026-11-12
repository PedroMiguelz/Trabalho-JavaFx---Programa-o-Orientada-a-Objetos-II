package br.unicentro.poo.projetopoo.Controller.TabCadastro;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Service.AnimalService;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import br.unicentro.poo.projetopoo.Util.ValidadorUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import br.unicentro.poo.projetopoo.Facade.AnimalFacade;


public class TabCadastroAnimalController {

    private final AnimalService animalService = ServiceFactory.criarAnimalService();
    private final AnimalFacade animalFacade = new AnimalFacade();

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
            Alertas.mostrarAviso("Insira o nome do animal!");
            txtNomeAnimal.requestFocus();
            return;
        }

        if(raca.isEmpty()){
            Alertas.mostrarAviso("Insira a raça do animal!");
            txtRacaAnimal.requestFocus();
            return;
        }

        if(sexo == null || sexo.isEmpty()){
            Alertas.mostrarAviso("Selecione o sexo do animal!");
            cbSexo.requestFocus();
            return;
        }

        if(especie.isEmpty()){
            Alertas.mostrarAviso("Insira a espécie do animal!");
            txtEspecieAnimal.requestFocus();
            return;
        }

        if(cpfDono.isEmpty()){
            Alertas.mostrarAviso("Insira o CPF do dono do animal!");
            txtCpfClienteAnimal.requestFocus();
            return;
        }

        if(!ValidadorUtil.isCpfValido(cpfDono)){
            Alertas.mostrarAviso("Digite um CPF válido!");
            txtCpfClienteAnimal.requestFocus();
            return;
        }


        Animal animal = Animal.builder()
                .nome(nome)
                .raca(raca)
                .sexo(sexo)
                .especie(especie)
                .cliente(Cliente.builder().cpf(cpfDono).build())
                .build();

        try{
            animalFacade.cadastrarAnimal(animal);
            Alertas.mostrarSucesso("Sucesso ao cadastrar o animal");

            limparCampos();

        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        }
        catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
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


