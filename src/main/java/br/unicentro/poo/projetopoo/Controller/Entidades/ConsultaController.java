package br.unicentro.poo.projetopoo.Controller.Entidades;

import br.unicentro.poo.projetopoo.Dao.ConsultaDao;
import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Consulta;
import br.unicentro.poo.projetopoo.Model.Veterinario;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConsultaController {
    private ConsultaDao consultaDao;

    public ConsultaController(){
        this.consultaDao = new ConsultaDao();
    }

    public void cadastrarConsulta(LocalDate data, BigDecimal valor, int idAnimal, String crmvVeterinario) throws SQLException {
        crmvVeterinario = crmvVeterinario == null ? "" : crmvVeterinario.trim();

        if (data == null) {
            throw new IllegalArgumentException("A data da consulta é obrigatória!");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da consulta deve ser maior ou igual a zero!");
        }
        if (idAnimal <= 0) {
            throw new IllegalArgumentException("Selecione um animal!");
        }
        if (crmvVeterinario.isEmpty()) {
            throw new IllegalArgumentException("O CRMV do veterinário é obrigatório!");
        }

        Animal animal = new Animal();
        animal.setId(idAnimal);

        Veterinario veterinario = new Veterinario();
        veterinario.setCrmv(crmvVeterinario);

        Consulta consulta = new Consulta();
        consulta.setData(data);
        consulta.setValor(valor);
        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);

        consultaDao.inserir(consulta);
    }

    public List<Consulta> buscarConsultas(String termoBusca) throws SQLException {
        if (termoBusca == null) {
            termoBusca = "";
        }
        return consultaDao.listar(termoBusca);
    }

    public void atualizarConsulta(int idConsulta, LocalDate data, BigDecimal valor, int idAnimal, String crmvVeterinario) throws SQLException {
        if (idConsulta <= 0) {
            throw new IllegalArgumentException("ID da consulta inválido para atualização!");
        }

        crmvVeterinario = crmvVeterinario == null ? "" : crmvVeterinario.trim();

        if (data == null) {
            throw new IllegalArgumentException("A data da consulta é obrigatória!");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da consulta deve ser maior ou igual a zero!");
        }
        if (idAnimal <= 0) {
            throw new IllegalArgumentException("Selecione um animal!");
        }
        if (crmvVeterinario.isEmpty()) {
            throw new IllegalArgumentException("O CRMV do veterinário é obrigatório!");
        }

        Animal animal = new Animal();
        animal.setId(idAnimal);

        Veterinario veterinario = new Veterinario();
        veterinario.setCrmv(crmvVeterinario);

        Consulta consulta = new Consulta();
        consulta.setId(idConsulta);
        consulta.setData(data);
        consulta.setValor(valor);
        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);

        consultaDao.atualizar(consulta);
    }

    public void excluirConsulta(int idConsulta) throws SQLException {
        if (idConsulta <= 0) {
            throw new IllegalArgumentException("ID da consulta inválido para exclusão!");
        }

        consultaDao.excluir(idConsulta);
    }

    public BigDecimal calcularTotalFaturado(LocalDate inicio, LocalDate fim) throws SQLException {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Informe a data inicial e a data final!");
        }
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data inicial não pode ser depois da data final!");
        }
        return consultaDao.totalFaturado(inicio, fim);
    }
}
