package br.unicentro.poo.projetopoo.Service;

import br.unicentro.poo.projetopoo.Dao.ClienteDao;
import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Util.ValidadorUtil;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {
    private final ClienteDao clienteDao;

    public ClienteService(){
        this.clienteDao = new ClienteDao();
    }

    public void cadastrarCliente(Cliente cliente) throws ServiceException{
       validar(cliente);

        try {
            clienteDao.inserir(cliente);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao cadastrar cliente", e);
        }
    }

    public void atualizarCliente(Cliente cliente) throws ServiceException{
       validar(cliente);

        try {
            clienteDao.atualizar(cliente);
        } catch (SQLException e) {
            throw new ServiceException("Errp ao atualizar cliente!", e);
        }
    }

    public List<Cliente> listarCliente(String termoBusca) throws ServiceException{
        if(termoBusca == null){
            termoBusca = "";
        }

        try {
            return clienteDao.listar(termoBusca.trim());
        } catch (SQLException e) {
            throw new ServiceException("Nenhum cliente encontrado!", e);
        }
    }

    public void excluirCliente(Cliente cliente) throws ServiceException {
        if (cliente == null ||  cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF inválido para exclusão!");
        }
        try {
            clienteDao.excluir(cliente.getCpf());
        } catch (SQLException e) {
            throw new ServiceException("Erro ao excluir cliente", e);
        }
    }

    public void validar(Cliente cliente){
        if(cliente == null){
            throw new IllegalArgumentException("Cliente invalido!");
        }
    }

}
