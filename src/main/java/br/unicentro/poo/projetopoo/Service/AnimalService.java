package br.unicentro.poo.projetopoo.Service;

import br.unicentro.poo.projetopoo.Dao.AnimalDao;
import br.unicentro.poo.projetopoo.Model.Animal;


import java.sql.SQLException;
import java.util.List;

public class AnimalService {
    private final AnimalDao animalDao;

    public AnimalService(){
        this.animalDao = new AnimalDao();
    }

    public void cadastrarAnimal(Animal animal) throws ServiceException{
        validar(animal);

        try {
            animalDao.inserir(animal);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao cadastrar animal!", e);
        }
    }

    public void atualizarAnimal(Animal animal) throws ServiceException {
        validar(animal);

        if(animal.getId() <= 0){
            throw new IllegalArgumentException("Id do animal deve ser um número válido");
        }

        try {
            animalDao.atualizar(animal);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao atualizar animal!", e);
        }
    }

    public List<Animal> listarAnimal(String termoBusca) throws ServiceException{
        if (termoBusca == null) {
            termoBusca = "";
        }

        try {
            return animalDao.listar(termoBusca);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao buscar animal!",e);
        }
    }

    public Animal listarAnimalId(int id) throws ServiceException {
        if(id <= 0){
            throw new IllegalArgumentException("Id do animal deve ser um número válido!");
        }

        try {
            return animalDao.buscarPorId(id);
        } catch (SQLException e) {
            throw  new ServiceException("Erro ao buscar animal!", e);
        }
    }

    public void excluirAnimal(Animal animal) throws ServiceException {
        if(animal == null || animal.getId() <= 0){
            throw new IllegalArgumentException("Id do animal deve ser um número válido");
        }

        try {
            animalDao.excluir(animal.getId());
        } catch (SQLException e) {
            throw new ServiceException("Erro ao excluir animal!", e);
        }
    }

    private void validar(Animal animal){

        if(animal == null){
            throw new IllegalArgumentException("Animal invalida!");
        }

        if(animal.getCliente().getCpf() == null){
            throw new IllegalArgumentException("Informe o cpf do cliente dono do animal!");
        }
    }

    /*eyshlaaa*/
    public int contarPorCliente(String cpf) throws ServiceException {
        try {
            return animalDao.contarPorCliente(cpf);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao verificar animais do cliente!", e);
        }
    }
}
