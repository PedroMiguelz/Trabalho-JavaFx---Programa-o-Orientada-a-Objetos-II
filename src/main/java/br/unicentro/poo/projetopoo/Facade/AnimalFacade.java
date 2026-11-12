package br.unicentro.poo.projetopoo.Facade;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Service.AnimalService;
import br.unicentro.poo.projetopoo.Service.ClienteService;
import br.unicentro.poo.projetopoo.Service.ConsultaService;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;

import java.util.List;

public class AnimalFacade {

    private final AnimalService animalService = ServiceFactory.criarAnimalService();
    private final ClienteService clienteService = ServiceFactory.criarClienteService();
    private final ConsultaService consultaService = ServiceFactory.criarConsultaService();

    // Confere o dono antes de cadastrar.
    public void cadastrarAnimal(Animal animal) throws ServiceException {
        Animal completo = montarAnimalCompleto(animal);
        animalService.cadastrarAnimal(completo);
    }

    // Confere se o animal existe e se o dono existe antes de atualizar.
    public void atualizarAnimal(Animal animal) throws ServiceException {
        Animal existente = buscarAnimalExistente(animal.getId());
        Animal completo = montarAnimalCompleto(animal);
        completo.setId(existente.getId());
        animalService.atualizarAnimal(completo);
    }

    // Bloqueia a exclusão se o animal tiver consultas, para não apagá-las em cascata.
    public void excluirAnimal(Animal animal) throws ServiceException {
        Animal existente = buscarAnimalExistente(animal.getId());
        int consultas = consultaService.contarPorAnimal(existente.getId());
        if (consultas > 0) {
            throw new ServiceException("Animal possui " + consultas + " consulta(s). Exclua as consultas antes.", null);
        }
        animalService.excluirAnimal(existente);
    }

    private Animal buscarAnimalExistente(int id) throws ServiceException {
        Animal animal = animalService.listarAnimalId(id);
        if (animal == null) {
            throw new ServiceException("Animal não encontrado (id " + id + ")", null);
        }
        return animal;
    }

    private Animal montarAnimalCompleto(Animal animal) throws ServiceException {
        Cliente dono = buscarCliente(animal.getCliente().getCpf());
        return Animal.builder()
                .nome(animal.getNome())
                .especie(animal.getEspecie())
                .raca(animal.getRaca())
                .sexo(animal.getSexo())
                .cliente(dono)
                .build();
    }

    private Cliente buscarCliente(String cpf) throws ServiceException {
        List<Cliente> encontrados = clienteService.listarCliente(cpf);
        for (Cliente c : encontrados) {
            if (c.getCpf().trim().equals(cpf.trim())) {
                return c;
            }
        }
        throw new ServiceException("Cliente não encontrado (CPF " + cpf + ")", null);
    }
}