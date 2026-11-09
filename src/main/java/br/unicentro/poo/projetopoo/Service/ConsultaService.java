package br.unicentro.poo.projetopoo.Service;


import br.unicentro.poo.projetopoo.Dao.ConsultaDao;
import br.unicentro.poo.projetopoo.Model.Consulta;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ConsultaService {
    private final ConsultaDao consultaDao;

    public ConsultaService(){
        this.consultaDao = new ConsultaDao();
    }

    public void cadastrarConsulta(Consulta consulta) throws ServiceException{
        validar(consulta);

        try {
            consultaDao.inserir(consulta);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao cadastrar consulta!", e);
        }
    }

    public void atualizarConsulta(Consulta consulta) throws  ServiceException{
        if(consulta.getId() <= 0) {
            throw new IllegalArgumentException("Id da consulta inválido para atualização!");
        }

       validar(consulta);

        try {
            consultaDao.atualizar(consulta);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao atualizar consulta!", e);
        }
    }


    public Optional<Consulta> listarConsulta(int idConsulta) throws ServiceException {
        if (idConsulta <= 0) {
            throw new IllegalArgumentException("Id da consulta inválido!");
        }

        try {
            return consultaDao.listar(String.valueOf(idConsulta)).stream().filter(c -> c.getId() == idConsulta).findFirst();
        } catch (SQLException e){
            throw new ServiceException("Erro ao buscar consulta!", e);
        }
    }

    public void excluirConsulta(Consulta consulta) throws ServiceException {
        if(consulta.getId() <= 0){
            throw new IllegalArgumentException("Id da consulta inválido para exclusão!");
        }

        try {
            consultaDao.excluir(consulta.getId());
        } catch (SQLException e) {
            throw new ServiceException("Erro ao excluir consulta!", e);
        }
    }

    public BigDecimal calcularTotalFaturado(LocalDate inicio, LocalDate fim) throws ServiceException {
        if(inicio == null || fim == null){
            throw new IllegalArgumentException("Informe a data inicial e a data final!");
        }

        if(inicio.isAfter(fim)){
            throw new IllegalArgumentException("A data inicial não pode ser depois da data final!");
        }

        try {
            return consultaDao.totalFaturado(inicio,fim);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao calcular faturamento!",e);
        }
    }

    /*
    VERIFICAÇÃO DE FORMATO -> CONTROLLER
    VERIFICAÇÂO DE VALOR    -> SERVICE
    */

    private void validar(Consulta consulta){

        if(consulta == null){
            throw new IllegalArgumentException("Consulta invalida!");
        }

        if(consulta.getData() == null){
            throw new IllegalArgumentException("A data da consulta é obrigatória!");
        }

        if(consulta.getValor() == null || consulta.getValor().compareTo(BigDecimal.ZERO) < 0){
            throw  new IllegalArgumentException("O valor da consulta deve ser maior ou igual a zero");
        }

        if(consulta.getAnimal() == null || consulta.getAnimal().getId() <= 0){
            throw new IllegalArgumentException("Selecione um id do animal válido!");
        }

        if(consulta.getVeterinario() == null || consulta.getVeterinario().getCrmv() == null || consulta.getVeterinario().getCrmv().isBlank()){
            throw new IllegalArgumentException("O CRMV do veterinário é obrigatório!");
        }
    }

}
