package br.unicentro.poo.projetopoo.Controller.TabCadastro;

import br.unicentro.poo.projetopoo.Model.Veterinario;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.VeterinarioService;
import br.unicentro.poo.projetopoo.Util.Alertas;
import br.unicentro.poo.projetopoo.Util.ValidadorUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class TabCadastroVeterinarioController {

    private final VeterinarioService veterinarioService = ServiceFactory.criarVeterinarioService();

    @FXML
    private Button btnAceitarVeterinario;

    @FXML
    private Button btnCancelarVeterinario;

    @FXML
    private DatePicker dpDataEntradaVeterinario;

    @FXML
    private TextField txtCrmvVeterinario;

    @FXML
    private TextField txtEspecializacaoVeterinario;

    @FXML
    private TextField txtNomeVeterinario;

    @FXML
    private TextField txtTelefoneVeterinario;

    @FXML
    void btnAceitarVeterinarioOnAction(ActionEvent event) {
        String nome = txtNomeVeterinario.getText().trim();
        String crmv = txtCrmvVeterinario.getText().trim();
        String especializacao = txtEspecializacaoVeterinario.getText().trim();
        String telefone = txtTelefoneVeterinario.getText().trim();
        LocalDate dataEntrada = dpDataEntradaVeterinario.getValue();

        if(nome.isEmpty()){
            Alertas.mostrarAviso("Informe o nome do veterinário!");
            txtNomeVeterinario.requestFocus();
            return;
        }

        if(crmv.isEmpty()){
            Alertas.mostrarAviso("Informe o CRMV do veterinário!");
            txtCrmvVeterinario.requestFocus();
            return;
        }

        if(especializacao.isEmpty()){
            Alertas.mostrarAviso("Informe a especialização do veterinário!");
            txtEspecializacaoVeterinario.requestFocus();
            return;
        }

        if(telefone.isEmpty()){
            Alertas.mostrarAviso("Informe o telefone do veterinário!");
            txtTelefoneVeterinario.requestFocus();
            return;
        }

        if(!ValidadorUtil.isTelefoneValido(telefone)){
            Alertas.mostrarAviso("Digite um telefone válido!");
            txtTelefoneVeterinario.requestFocus();
            return;
        }

        if(dataEntrada == null){
            Alertas.mostrarAviso("Informe a data de entrada do veterinário!");
            dpDataEntradaVeterinario.requestFocus();
            return;
        }

        Veterinario veterinario = Veterinario.builder()
                .nome(nome)
                .crmv(crmv)
                .especializacao(especializacao)
                .telefone(telefone)
                .dataEntrada(dataEntrada)
                .build();

        try{
            veterinarioService.cadastrarVeterinario(veterinario);

            Alertas.mostrarSucesso("Sucesso ao cadastrar veterinário");

            limparCampos();


        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        }
        catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
        }
    }

    @FXML
    void btnCancelarVeterinarioOnAction(ActionEvent event) {
        txtNomeVeterinario.clear();
        txtCrmvVeterinario.clear();
        txtEspecializacaoVeterinario.clear();
        txtTelefoneVeterinario.clear();
        dpDataEntradaVeterinario.setValue(null);

        Alertas.mostrarAviso("Operação cancelada");
    }

    private void limparCampos(){
        txtNomeVeterinario.clear();
        txtCrmvVeterinario.clear();
        txtEspecializacaoVeterinario.clear();
        txtTelefoneVeterinario.clear();
        dpDataEntradaVeterinario.setValue(null);
    }

}
