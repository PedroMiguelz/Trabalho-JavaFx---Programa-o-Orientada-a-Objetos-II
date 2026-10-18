package br.unicentro.poo.projetopoo.Controller.Entidades;

import br.unicentro.poo.projetopoo.Dao.AnimalDao;
import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class AnimalController {
    private AnimalDao animalDao;

    public AnimalController() {
        this.animalDao = new AnimalDao();
    }

    public void cadastrarAnimal(String nome, String especie, String raca, String sexo, String cpfCliente) throws SQLException {
        if (nome == null || nome.trim().isEmpty() || cpfCliente == null || cpfCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do animal e o CPF do cliente dono são obrigatórios!");
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(cpfCliente);

        Animal animal = new Animal();
        animal.setNome(nome);
        animal.setEspecie(especie);
        animal.setRaca(raca);
        animal.setSexo(sexo);
        animal.setCliente(cliente);

        animalDao.inserir(animal);
    }

    public List<Animal> buscarAnimais(String termoBusca) throws SQLException {
        if (termoBusca == null) {
            termoBusca = "";
        }
        return animalDao.listar(termoBusca);
    }

    public Animal buscarAnimalId(int id) throws SQLException{
        if(id <= 0){
            throw new IllegalArgumentException("Id inválido");
        }
        return animalDao.buscarPorId(id);
    }

    public void atualizarAnimal(int id, String nome, String especie, String raca, String sexo, String cpfCliente) throws SQLException {


        Cliente cliente = new Cliente();
        cliente.setCpf(cpfCliente);

        Animal animal = new Animal();
        animal.setId(id);
        animal.setNome(nome);
        animal.setEspecie(especie);
        animal.setRaca(raca);
        animal.setSexo(sexo);
        animal.setCliente(cliente);

        animalDao.atualizar(animal);
    }
    public void excluirAnimal(int idAnimal) throws SQLException {
        if (idAnimal <= 0) {
            throw new IllegalArgumentException("ID do animal inválido para exclusão!");
        }

        animalDao.excluir(idAnimal);
    }
}
