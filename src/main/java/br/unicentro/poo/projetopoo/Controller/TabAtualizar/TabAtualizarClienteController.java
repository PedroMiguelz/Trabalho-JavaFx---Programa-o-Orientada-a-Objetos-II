package br.unicentro.poo.projetopoo.Controller.TabAtualizar;

import br.unicentro.poo.projetopoo.Controller.Entidades.ClienteController;
import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

public class TabAtualizarClienteController {

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
    private TextField txtBuscarNomeCliente;

    @FXML
    private void btnBuscarClienteOnAction(ActionEvent event) {
        String buscaCpf = txtBuscarCpfCliente.getText().trim();
        String buscaNome = txtBuscarNomeCliente.getText().trim();

        String termoBusca;

        if (!buscaCpf.isEmpty()) {
            termoBusca = buscaCpf;
        } else {
            termoBusca = buscaNome;
        }

        if (termoBusca.isEmpty()) {
            Alertas.mostrarAviso("Digite um cpf ou nome");
            return;
        }

        try {
            ClienteController controller = new ClienteController();
            List<Cliente> clientes = controller.buscarClientes(termoBusca);

            if (clientes.isEmpty()) {
                Alertas.mostrarAviso("Nenhum cliente encontrado.");
                limparCampos();
                return;
            }


            Cliente c = clientes.get(0);
            txtAtualizarCpfCliente.setText(c.getCpf());
            txtAtualizarNomeCliente.setText(c.getNome());
            txtAtualizarTelefoneCliente.setText(c.getTelefone());

            Alertas.mostrarSucesso("Cliente encontrada!");

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao buscar cliente: " + e.getMessage());
        }

    }

    @FXML
    private void btnSalvarClienteOnAction(ActionEvent event) {
        String cpf = txtAtualizarCpfCliente.getText().trim();
        String nome = txtAtualizarNomeCliente.getText().trim();
        String telefone = txtAtualizarTelefoneCliente.getText().trim();

        if (cpf.isEmpty()) {
            Alertas.mostrarAviso("Nenhum cliente selecionado para atualizar. Faça a busca primeiro.");
            return;
        }

        if (nome.isEmpty()) {
            Alertas.mostrarAviso("O nome não pode estar vazio.");
            txtAtualizarNomeCliente.requestFocus();
            return;
        }

        try {
            ClienteController controller = new ClienteController();
            controller.atualizarCliente(cpf, nome, telefone);

            Alertas.mostrarSucesso("Cliente atualizado com sucesso!");

            limparCampos();

        } catch (Exception e) {
            Alertas.mostrarErro("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    private void limparCampos(){
        txtAtualizarCpfCliente.clear();
        txtAtualizarNomeCliente.clear();
        txtAtualizarTelefoneCliente.clear();
        txtBuscarCpfCliente.clear();
        txtBuscarNomeCliente.clear();
    }
}
