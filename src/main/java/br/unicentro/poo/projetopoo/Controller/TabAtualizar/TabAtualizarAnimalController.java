package br.unicentro.poo.projetopoo.Controller.TabAtualizar;

import br.unicentro.poo.projetopoo.Controller.Entidades.AnimalController;
import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class TabAtualizarAnimalController {

    private Animal animal;

    @FXML
    private Button btnBuscarAnimal;

    @FXML
    private Button btnSalvarAnimal;

    @FXML
    private ComboBox<String> cbAtualizarSexoAnimal;

    @FXML
    private TextField txtAtualizarClienteAnimal;

    @FXML
    private TextField txtAtualizarEspecieAnimal;

    @FXML
    private TextField txtAtualizarIdAnimal;

    @FXML
    private TextField txtAtualizarNomeAnimal;

    @FXML
    private TextField txtAtualizarRacaAnimal;

    @FXML
    private TextField txtBuscarIdAnimal;

    @FXML
    private TextField txtBuscarNomeAnimal;

    @FXML
    void btnBuscarAnimalOnAction(ActionEvent event) {
        String buscaIdAnimal = txtBuscarIdAnimal.getText().trim();
        String buscaNome = txtBuscarNomeAnimal.getText().trim();

        try{
            AnimalController animalController = new AnimalController();

            if(!buscaIdAnimal.isEmpty()){
                int id;
                try {
                    id = Integer.parseInt(buscaIdAnimal);
                } catch (NumberFormatException e){
                    Alertas.mostrarErro("Informe um id válido");
                    return;
                }
                Animal a = animalController.buscarAnimalId(id);

                if(a == null){
                    Alertas.mostrarAviso("Nenhum animal encontrado");
                    limparCampos();
                    return;
                }

                Alertas.mostrarSucesso("Animal encontrada!");
                preencherCampos(a);

            }
            else if(!buscaNome.isEmpty()){
                List<Animal> animais = animalController.buscarAnimais(buscaNome);

                if(animais.isEmpty()){
                    Alertas.mostrarAviso("Nenhum animal encontrado");
                    limparCampos();
                    return;
                }

                Alertas.mostrarSucesso("Animal encontrada!");
                Animal a = animais.get(0);
                preencherCampos(a);


            }
            else {
                Alertas.mostrarAviso("Insira o id ou nome do animal");
            }

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao buscar animal: " + e.getMessage());
        }
    }

    @FXML
    void btnSalvarAnimalOnAction(ActionEvent event) {
        String nome = txtAtualizarNomeAnimal.getText().trim();
        String especie = txtAtualizarEspecieAnimal.getText().trim();
        String raca = txtAtualizarRacaAnimal.getText().trim();
        String sexo = cbAtualizarSexoAnimal.getValue();
        String cliente = txtAtualizarClienteAnimal.getText().trim();

        if(animal == null){
            Alertas.mostrarAviso( "Nenhum animal selecionado para atualizar. Faça a busca primeiro.");
            return;
        }

        try{
            AnimalController animalController = new AnimalController();
            animalController.atualizarAnimal(animal.getId(),nome,especie,raca,sexo,cliente);

            Alertas.mostrarSucesso("Animal atualizado com sucesso!");
            limparCampos();


        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao atualizar animal: " + e.getMessage());
        }
    }

    private void limparCampos(){
        animal = null;
        txtAtualizarNomeAnimal.clear();
        txtAtualizarEspecieAnimal.clear();
        txtAtualizarRacaAnimal.clear();
        cbAtualizarSexoAnimal.setValue(null);
        txtAtualizarClienteAnimal.clear();
    }

    private void preencherCampos(Animal a){
        animal = a;
        txtAtualizarIdAnimal.setText(String.valueOf(a.getId()));
        txtAtualizarNomeAnimal.setText(a.getNome());
        txtAtualizarEspecieAnimal.setText(a.getEspecie());
        txtAtualizarRacaAnimal.setText(a.getRaca());
        cbAtualizarSexoAnimal.setValue(a.getSexo());
        txtAtualizarClienteAnimal.setText(a.getCliente().getCpf());
    }

}
