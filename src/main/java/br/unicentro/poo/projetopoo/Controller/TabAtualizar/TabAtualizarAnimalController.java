package br.unicentro.poo.projetopoo.Controller.TabAtualizar;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Service.AnimalService;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import br.unicentro.poo.projetopoo.Util.ValidadorUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import br.unicentro.poo.projetopoo.Facade.AnimalFacade;

import java.util.List;

public class TabAtualizarAnimalController {

    private Animal animal;

    private final AnimalService animalService = ServiceFactory.criarAnimalService();
    private final AnimalFacade animalFacade = new AnimalFacade();


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

                if(animal == null){
                    Alertas.mostrarAviso( "Nenhum animal selecionado para atualizar. Faça a busca primeiro!");
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
                Alertas.mostrarAviso("Insira o id ou nome do animal!");
            }

        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        }
        catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
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
            Alertas.mostrarAviso( "Nenhum animal selecionado para atualizar. Faça a busca primeiro!");
            return;
        }

        if(cpfCliente.isEmpty()){
            Alertas.mostrarAviso("Insira o CPF do dono do animal!");
            txtAtualizarClienteAnimal.requestFocus();
            return;
        }

        if(!ValidadorUtil.isCpfValido(cpfCliente)){
            Alertas.mostrarAviso("Digite um CPF válido!");
            txtAtualizarClienteAnimal.requestFocus();
            return;
        }

        Animal animalAtualizado = Animal.builder()
                .id(animal.getId())
                .nome(nome)
                .especie(especie)
                .raca(raca)
                .sexo(sexo)
                .cliente(Cliente.builder().cpf(cpfCliente).build())
                .build();

        try{
            animalFacade.atualizarAnimal(animalAtualizado);
            Alertas.mostrarSucesso("Animal atualizado com sucesso!");
            limparCampos();


        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        }
        catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
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
