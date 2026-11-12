package br.unicentro.poo.projetopoo.Service;

import br.unicentro.poo.projetopoo.Dao.VeterinarioDao;
import br.unicentro.poo.projetopoo.Model.Veterinario;

import java.sql.SQLException;
import java.util.List;

public class VeterinarioService {
    private final VeterinarioDao veterinarioDao;

    public VeterinarioService(){
        this.veterinarioDao = new VeterinarioDao();
    }

    public void cadastrarVeterinario(Veterinario veterinario) throws ServiceException{
        validar(veterinario);

        try {
            veterinarioDao.inserir(veterinario);
        } catch (SQLException e){
            throw new ServiceException("Erro ao cadastrar veterinário", e);
        }
    }

    public void atualizarVeterinario(Veterinario veterinario) throws ServiceException{
        validar(veterinario);

        if(veterinario.getCrmv() == null || veterinario.getCrmv().trim().isEmpty()){
            throw new IllegalArgumentException("Informe um CRMV válido!");
        }

        try {
            veterinarioDao.atualizar(veterinario);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao atualizar veterinário!", e);
        }
    }

    public List<Veterinario> listarVeterinario(String termoBusca) throws ServiceException{
        if (termoBusca == null) {
            termoBusca = "";
        }

        try{
            return veterinarioDao.listar(termoBusca);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao buscar veterinário!", e);
        }
    }

    public void excluirVeterinario(Veterinario veterinario) throws ServiceException{
        if (veterinario == null) {
            throw new IllegalArgumentException("Veterinário inválido!");
        }

        if (veterinario.getCrmv() == null || veterinario.getCrmv().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o CRMV do veterinário!");
        }

        try {
            veterinarioDao.excluir(veterinario.getCrmv());
        } catch (SQLException e) {
            throw new ServiceException("Erro ao excluir veterinário!", e);
        }


    }

    public void validar(Veterinario veterinario){
        if(veterinario == null){
            throw new IllegalArgumentException("Veterinário inválido!");
        }
    }
}
