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
        if (cliente == null || cliente.getCpf().trim().isEmpty() || cliente.getCpf() == null) {
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

        if(cliente.getNome() == null || cliente.getNome().trim().isEmpty()){
            throw new IllegalArgumentException("Informe o nome do cliente!");
        }

        if(cliente.getCpf() == null || cliente.getCpf().isEmpty()){
            throw new IllegalArgumentException("Informe o cpf do cliente!");
        }

        if(cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()){
            throw new IllegalArgumentException("Informe o telefone do cliente!");
        }

        if(!ValidadorUtil.isTelefoneValido(cliente.getTelefone())){
            throw new IllegalArgumentException("Digite um formato de elefone válido!");
        }

        if(!ValidadorUtil.isCpfValido(cliente.getCpf())){
            throw new IllegalArgumentException("Digite um formato de CPF válido!");
        }
    }

}
