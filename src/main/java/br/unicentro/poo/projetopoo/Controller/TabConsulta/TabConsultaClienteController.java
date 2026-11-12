package br.unicentro.poo.projetopoo.Controller.TabConsulta;

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

public class TabConsultaClienteController {

    private final ClienteService clienteService = ServiceFactory.criarClienteService();

    @FXML
    private Button btnConsultarCliente;

    @FXML
    private Button btnLimparCliente;

    @FXML
    private TextField txtConsultarCpfCliente;

    @FXML
    private TextField txtConsultarNomeCliente;

    @FXML
    private TextField txtResultadoCpfCliente;

    @FXML
    private TextField txtResultadoNomeCliente;

    @FXML
    private TextField txtResultadoTelefoneCliente;

    @FXML
    void btnConsultarClienteOnAction(ActionEvent event) {
        String buscaCpf = txtConsultarCpfCliente.getText().trim();
        String buscaNome = txtConsultarNomeCliente.getText().trim();

        String termoBusca;

        if (buscaCpf.isEmpty() && buscaNome.isEmpty()) {
            Alertas.mostrarAviso("Digite um CPF ou Nome para consultar!");
            return;
        }

        if (!buscaCpf.isEmpty() && !ValidadorUtil.isCpfValido(buscaCpf)) {
            Alertas.mostrarAviso("Digite um CPF válido!");
            txtConsultarCpfCliente.requestFocus();
            return;
        }

        if (!buscaCpf.isEmpty()) {
            termoBusca = buscaCpf;
        } else {
            termoBusca = buscaNome;
        }

        try {
            List<Cliente> clientes = clienteService.listarCliente(termoBusca);

            if (clientes.isEmpty()) {
                Alertas.mostrarAviso("Nenhum cliente encontrado.");
                limparCampos();
                return;
            }

            Cliente c = clientes.get(0);
            txtResultadoCpfCliente.setText(c.getCpf());
            txtResultadoNomeCliente.setText(c.getNome());
            txtResultadoTelefoneCliente.setText(c.getTelefone());

            Alertas.mostrarSucesso("Cliente encontrado!");

        } catch (IllegalArgumentException e){
            Alertas.mostrarAviso(e.getMessage());
        }
        catch (ServiceException e) {
            Alertas.mostrarErro(e.getMessage());
        }
    }

    @FXML
    void btnLimparClienteOnAction(ActionEvent event) {
        limparCampos();
    }

    private void limparCampos() {
        txtConsultarCpfCliente.clear();
        txtConsultarNomeCliente.clear();
        txtResultadoCpfCliente.clear();
        txtResultadoNomeCliente.clear();
        txtResultadoTelefoneCliente.clear();
    }
}


