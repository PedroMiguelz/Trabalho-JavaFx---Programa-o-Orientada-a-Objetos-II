package br.unicentro.poo.projetopoo.Controller.Entidades;

import br.unicentro.poo.projetopoo.Dao.ClienteDao;
import br.unicentro.poo.projetopoo.Model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    private ClienteDao clienteDao;

    public ClienteController() {
        this.clienteDao = new ClienteDao();
    }

    public void cadastrarCliente(String cpf, String nome, String telefone) throws SQLException {
        if (cpf == null || cpf.trim().isEmpty() || nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF e Nome são obrigatórios!");
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);

        clienteDao.inserir(cliente);
    }

    public List<Cliente> buscarClientes(String termoBusca) throws SQLException {
        if (termoBusca == null) {
            termoBusca = "";
        }
        return clienteDao.listar(termoBusca);
    }
    public void atualizarCliente(String cpf, String nome, String telefone) throws SQLException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do cliente é obrigatório para atualização!");
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);

        clienteDao.atualizar(cliente);
    }

    public void excluirCliente(String cpf) throws SQLException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF inválido para exclusão!");
        }

        clienteDao.excluir(cpf);
    }
}
