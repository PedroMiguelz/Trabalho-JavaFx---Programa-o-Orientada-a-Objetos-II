package br.unicentro.poo.projetopoo.Controller.TabCadastro;

import br.unicentro.poo.projetopoo.Controller.Entidades.ClienteController;
import br.unicentro.poo.projetopoo.Util.Alertas;
import br.unicentro.poo.projetopoo.Util.ValidadorUtil; // <--- Import do ValidadorUtil
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TabCadastroClienteController {

    @FXML
    private Button btnAceitarCliente;

    @FXML
    private Button btnCancelarCliente;

    @FXML
    private TextField txtCpfCliente;

    @FXML
    private TextField txtNomeCliente;

    @FXML
    private TextField txtTelefoneCliente;

    @FXML
    private void btnAceitarClienteOnAction(ActionEvent event) {
        String nomeCliente = txtNomeCliente.getText().trim();
        String cpfCliente = txtCpfCliente.getText().trim();
        String telefoneCliente = txtTelefoneCliente.getText().trim();

        if(nomeCliente.isEmpty()){
            Alertas.mostrarAviso("Informe o nome do cliente");
            txtNomeCliente.requestFocus();
            return;
        }

        if(cpfCliente.isEmpty()){
            Alertas.mostrarAviso("Informe o cpf do cliente");
            txtCpfCliente.requestFocus();
            return;
        }

        if(!ValidadorUtil.isCpfValido(cpfCliente)){
            Alertas.mostrarAviso("O formato do cpf é inválido");
            txtCpfCliente.requestFocus();
            return;
        }

        if(telefoneCliente.isEmpty()){
            Alertas.mostrarAviso("Digite o telefone do cliente");
            txtTelefoneCliente.requestFocus();
            return;
        }

        if(!ValidadorUtil.isTelefoneValido(telefoneCliente)){
            Alertas.mostrarAviso("Digite um formato de telefone válido");
            txtTelefoneCliente.requestFocus();
            return;
        }

        try{
            ClienteController clienteController = new ClienteController();
            clienteController.cadastrarCliente(cpfCliente, nomeCliente, telefoneCliente);

            Alertas.mostrarSucesso("Sucesso ao cadastrar cliente");

            limparCampos();

        } catch(Exception e){
            e.printStackTrace();
            Alertas.mostrarErro("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @FXML
    private void btnCancelarClienteOnAction(ActionEvent event) {
        limparCampos();
        Alertas.mostrarAviso("Operação cancelada");
    }

    private void limparCampos(){
        txtNomeCliente.clear();
        txtCpfCliente.clear();
        txtTelefoneCliente.clear();
    }
}