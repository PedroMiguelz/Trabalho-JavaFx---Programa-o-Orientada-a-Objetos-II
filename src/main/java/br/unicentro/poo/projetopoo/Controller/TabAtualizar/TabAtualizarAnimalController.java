package br.unicentro.poo.projetopoo.Controller.TabAtualizar;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Service.AnimalService;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class TabAtualizarAnimalController {

    private Animal animal;

    private final AnimalService animalService = new AnimalService();

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

            if(!buscaIdAnimal.isEmpty()){
                int id;
                try {
                    id = Integer.parseInt(buscaIdAnimal);
                } catch (NumberFormatException e){
                    Alertas.mostrarErro("Informe um id válido");
                    return;
                }

                Animal a = animalService.listarAnimalId(id);

                if(a == null){
                    Alertas.mostrarAviso("Nenhum animal encontrado");
                    limparCampos();
                    return;
                }

                Alertas.mostrarSucesso("Animal encontrada!");
                preencherCampos(a);

            }
            else if(!buscaNome.isEmpty()){
                List<Animal> animais = animalService.listarAnimal(buscaNome);

                if(animais.isEmpty()) {
                    Alertas.mostrarAviso("Nenhum animal encontrado!");
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

        } catch (ServiceException e) {
            Alertas.mostrarErro("Erro ao buscar animal: " + e.getMessage());
        }
    }

    @FXML
    void btnSalvarAnimalOnAction(ActionEvent event) {
        String nome = txtAtualizarNomeAnimal.getText().trim();
        String especie = txtAtualizarEspecieAnimal.getText().trim();
        String raca = txtAtualizarRacaAnimal.getText().trim();
        String sexo = cbAtualizarSexoAnimal.getValue();
        String cpfCliente = txtAtualizarClienteAnimal.getText().trim();

        if(animal == null){
            Alertas.mostrarAviso( "Nenhum animal selecionado para atualizar. Faça a busca primeiro.");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(cpfCliente);

        Animal animalAtualizado = new Animal();
        animalAtualizado.setNome(nome);
        animalAtualizado.setEspecie(especie);
        animalAtualizado.setRaca(raca);
        animalAtualizado.setSexo(sexo);
        animalAtualizado.setCliente(cliente);

        try{
           animalService.atualizarAnimal(animalAtualizado);

            Alertas.mostrarSucesso("Animal atualizado com sucesso!");
            limparCampos();


        } catch (ServiceException e) {
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
        txtAtualizarIdAnimal.clear();
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
