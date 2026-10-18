package br.unicentro.poo.projetopoo.Controller.Entidades;

import br.unicentro.poo.projetopoo.Dao.VeterinarioDao;
import br.unicentro.poo.projetopoo.Model.Veterinario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VeterinarioController {
    private VeterinarioDao veterinarioDao;

    public VeterinarioController() {
        this.veterinarioDao = new VeterinarioDao();
    }

    public void cadastrarVeterinario(String crmv, String nome, LocalDate dataEntrada, String especializacao, String telefone) throws SQLException {
        if (crmv == null || crmv.trim().isEmpty() || nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("CRMV e Nome são obrigatórios para o veterinário!");
        }

        Veterinario veterinario = new Veterinario();
        veterinario.setCrmv(crmv);
        veterinario.setNome(nome);
        veterinario.setDataEntrada(dataEntrada);
        veterinario.setEspecializacao(especializacao);
        veterinario.setTelefone(telefone);

        veterinarioDao.inserir(veterinario);
    }

    public List<Veterinario> buscarVeterinarios(String termoBusca) throws SQLException {
        if (termoBusca == null) {
            termoBusca = "";
        }
        return veterinarioDao.listar(termoBusca);
    }

    public void atualizarVeterinario(String crmv, String nome, LocalDate dataEntrada, String especializacao, String telefone) throws SQLException {
        if (crmv == null || crmv.trim().isEmpty()) {
            throw new IllegalArgumentException("CRMV é obrigatório para atualizar o veterinário!");
        }

        Veterinario veterinario = new Veterinario();
        veterinario.setCrmv(crmv);
        veterinario.setNome(nome);
        veterinario.setDataEntrada(dataEntrada);
        veterinario.setEspecializacao(especializacao);
        veterinario.setTelefone(telefone);

        veterinarioDao.atualizar(veterinario);
    }

    public void excluirVeterinario(String crmv) throws SQLException {
        if (crmv == null || crmv.trim().isEmpty()) {
            throw new IllegalArgumentException("CRMV inválido para exclusão!");
        }

        veterinarioDao.excluir(crmv);
    }
}
