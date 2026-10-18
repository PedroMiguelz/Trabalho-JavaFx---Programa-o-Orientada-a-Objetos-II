package br.unicentro.poo.projetopoo.Controller.TabCadastro;

import br.unicentro.poo.projetopoo.Controller.Entidades.VeterinarioController;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;

public class TabCadastroVeterinarioController {

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
            Alertas.mostrarAviso("Informe o nome do veterinário");
            txtNomeVeterinario.requestFocus();
            return;
        }

        if(crmv.isEmpty()){
            Alertas.mostrarAviso("Informe o crmv do veterinário");
            txtCrmvVeterinario.requestFocus();
            return;
        }

        if(especializacao.isEmpty()){
            Alertas.mostrarAviso("Informe a especialização do veterinário");
            txtEspecializacaoVeterinario.requestFocus();
            return;
        }

        if(telefone.isEmpty()){
            Alertas.mostrarAviso("Informe o telefone do veterinário");
            txtTelefoneVeterinario.requestFocus();
            return;
        }

        if(dataEntrada == null){
            Alertas.mostrarAviso("Informe a data de entrada do veterinário");
            dpDataEntradaVeterinario.requestFocus();
            return;
        }

        try{
            VeterinarioController vetController = new VeterinarioController();
            vetController.cadastrarVeterinario(crmv, nome, dataEntrada, especializacao, telefone);

            Alertas.mostrarSucesso("Sucesso ao cadastrar veterinário");

        } catch (SQLException e) {
            e.printStackTrace();
           Alertas.mostrarErro("Erro ao cadastrar veterinário: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alertas.mostrarErro("Erro ao processar veterinário: " + e.getMessage());
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

}
