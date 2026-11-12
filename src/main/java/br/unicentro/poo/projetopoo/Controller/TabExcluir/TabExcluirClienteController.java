package br.unicentro.poo.projetopoo.Controller.TabExcluir;

import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Service.ClienteService;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Util.Alertas;
import br.unicentro.poo.projetopoo.Util.ValidadorUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import br.unicentro.poo.projetopoo.Facade.ClienteFacade;

import java.util.List;
import java.util.Optional;

public class TabExcluirClienteController {

    private final ClienteService clienteService = ServiceFactory.criarClienteService();
    private final ClienteFacade clienteFacade = new ClienteFacade();
    private Cliente clienteAtual;

    @FXML
    private Button btnBuscarCliente;

    @FXML
    private Button btnExcluirCliente;

    @FXML
    private TextField txtBuscarCpfCliente;

    @FXML
    private TextField txtBuscarNomeCliente;

    @FXML
    private TextField txtResultadoCpfCliente;

    @FXML
    private TextField txtResultadoNomeCliente;

    @FXML
    private TextField txtResultadoTelefoneCliente;

    @FXML
    void btnBuscarClienteOnAction(ActionEvent event) {
        String buscaCpf = txtBuscarCpfCliente.getText().trim();
        String buscaNome = txtBuscarNomeCliente.getText().trim();


        if (buscaCpf.isEmpty() && buscaNome.isEmpty()) {
            Alertas.mostrarAviso("Insira o CPF ou o Nome para buscar o cliente!");
            txtBuscarCpfCliente.requestFocus();
            return;
        }

        if (!buscaCpf.isEmpty() && !ValidadorUtil.isCpfValido(buscaCpf)) {
            Alertas.mostrarAviso("Digite um CPF válido!");
            txtBuscarCpfCliente.requestFocus();
            return;
        }

        String termoBusca = !buscaCpf.isEmpty() ? buscaCpf : buscaNome;

        try {
            List<Cliente> clientes = clienteService.listarCliente(termoBusca);

            Cliente c = clientes.stream()
                    .filter(cli -> (!buscaCpf.isEmpty() && cli.getCpf().equalsIgnoreCase(buscaCpf)) ||
                            (!buscaNome.isEmpty() && cli.getNome().toLowerCase().contains(buscaNome.toLowerCase())))
                    .findFirst()
                    .orElse(!clientes.isEmpty() ? clientes.get(0) : null);

            if (c == null) {
                Alertas.mostrarAviso("Nenhum cliente encontrado");
                limparCampos();
                return;
            }

            clienteAtual = c;
            txtResultadoCpfCliente.setText(c.getCpf());
            txtResultadoNomeCliente.setText(c.getNome());
            txtResultadoTelefoneCliente.setText(c.getTelefone());

            Alertas.mostrarSucesso("Cliente encontrado!");

        } catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
        }
    }

    @FXML
    void btnExcluirClienteOnAction(ActionEvent event) {
        if (clienteAtual == null) {
            Alertas.mostrarAviso("Nenhum cliente selecionado para excluir. Faça a busca primeiro.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este cliente?");
        confirmacao.setContentText("Cliente: " + clienteAtual.getNome() + " (CPF: " + clienteAtual.getCpf() + ")");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                clienteFacade.excluirCliente(clienteAtual);
                Alertas.mostrarSucesso("Cliente excluído com sucesso!");
                limparCampos();

            } catch (IllegalArgumentException e) {
                Alertas.mostrarAviso(e.getMessage());
            }
            catch (ServiceException e) {
                Alertas.mostrarErro(e.getMessage());
            }
        }
    }

    private void limparCampos() {
        clienteAtual = null;
        txtBuscarCpfCliente.clear();
        txtBuscarNomeCliente.clear();
        txtResultadoCpfCliente.clear();
        txtResultadoNomeCliente.clear();
        txtResultadoTelefoneCliente.clear();
        txtBuscarCpfCliente.requestFocus();
    }
}