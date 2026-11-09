package br.unicentro.poo.projetopoo.Controller.TabCadastro;

import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Service.ClienteService;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import br.unicentro.poo.projetopoo.Util.ValidadorUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TabCadastroClienteController {

    private final ClienteService clienteService = new ClienteService();

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
            Alertas.mostrarAviso("Digite um formato de CPF válido!");
        }

        if(telefoneCliente.isEmpty()){
            Alertas.mostrarAviso("Digite o telefone do cliente");
            txtTelefoneCliente.requestFocus();
            return;
        }

        if(!ValidadorUtil.isTelefoneValido(telefoneCliente)){
            Alertas.mostrarAviso("Digite o um formato válido de telefone");
        }



        Cliente cliente = new Cliente();
        cliente.setNome(nomeCliente);
        cliente.setCpf(cpfCliente);
        cliente.setTelefone(telefoneCliente);

        try{
            clienteService.cadastrarCliente(cliente);

            Alertas.mostrarSucesso("Sucesso ao cadastrar cliente");

            limparCampos();

        }
        catch(ServiceException e){
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