package br.unicentro.poo.projetopoo.Controller.TabAtualizar;

import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Service.ClienteService;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import br.unicentro.poo.projetopoo.Util.ValidadorUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

public class TabAtualizarClienteController {

    private final ClienteService clienteService = ServiceFactory.criarClienteService();

    private Cliente clienteAtual;
    @FXML
    private Button btnBuscarCliente;

    @FXML
    private Button btnSalvarCliente;

    @FXML
    private TextField txtAtualizarCpfCliente;

    @FXML
    private TextField txtAtualizarNomeCliente;

    @FXML
    private TextField txtAtualizarTelefoneCliente;

    @FXML
    private TextField txtBuscarCpfCliente;

    @FXML
    private void btnBuscarClienteOnAction(ActionEvent event) {
        String termoBusca = txtBuscarCpfCliente.getText().trim();

        if (termoBusca.isEmpty()) {
            Alertas.mostrarAviso("Digite um CPF!");
            txtBuscarCpfCliente.requestFocus();
            return;
        }

        if (!ValidadorUtil.isCpfValido(termoBusca)) {
            Alertas.mostrarAviso("Digite um CPF válido!");
            txtBuscarCpfCliente.requestFocus();
            return;
        }

        try {
            List<Cliente> clientes = clienteService.listarCliente(termoBusca);

            if(clientes.isEmpty()){
                Alertas.mostrarAviso("Nenhum cliente encontrado!");
                return;
            }

            Cliente c = clientes.get(0);
            clienteAtual = c;

            txtAtualizarCpfCliente.setText(c.getCpf());
            txtAtualizarNomeCliente.setText(c.getNome());
            txtAtualizarTelefoneCliente.setText(c.getTelefone());

            Alertas.mostrarSucesso("Cliente encontrado!");

        } catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void btnSalvarClienteOnAction(ActionEvent event) {
        String cpf = txtAtualizarCpfCliente.getText().trim();
        String nome = txtAtualizarNomeCliente.getText().trim();
        String telefone = txtAtualizarTelefoneCliente.getText().trim();

        if(clienteAtual == null){
            Alertas.mostrarAviso("Nenhum cliente selecionado para atualizar. Faça a busca primeiro!");
            return;
        }

        if (nome.isEmpty()) {
            Alertas.mostrarAviso("O nome não pode estar vazio!");
            txtAtualizarNomeCliente.requestFocus();
            return;
        }

        if (telefone.isEmpty()) {
            Alertas.mostrarAviso("O telefone não pode estar vazio!");
            txtAtualizarTelefoneCliente.requestFocus();
            return;
        }

        if (!ValidadorUtil.isTelefoneValido(telefone)) {
            Alertas.mostrarAviso("Digite um telefone válido!");
            txtAtualizarTelefoneCliente.requestFocus();
            return;
        }

        try {
            clienteAtual.setNome(nome);
            clienteAtual.setTelefone(telefone);

            clienteService.atualizarCliente(clienteAtual);


            Alertas.mostrarSucesso("Cliente atualizado com sucesso!");

            limparCampos();

        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        }
        catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
        }
    }

    private void limparCampos(){
        txtAtualizarCpfCliente.clear();
        txtAtualizarNomeCliente.clear();
        txtAtualizarTelefoneCliente.clear();
        txtBuscarCpfCliente.clear();
    }
}
