package br.unicentro.poo.projetopoo.Facade;

import br.unicentro.poo.projetopoo.Model.Cliente;
import br.unicentro.poo.projetopoo.Service.AnimalService;
import br.unicentro.poo.projetopoo.Service.ClienteService;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;

import java.util.List;

public class ClienteFacade {

    private final ClienteService clienteService = ServiceFactory.criarClienteService();
    private final AnimalService animalService = ServiceFactory.criarAnimalService();

    public void excluirCliente(Cliente cliente) throws ServiceException {
        Cliente existente = buscarClienteExistente(cliente.getCpf());
        int animais = animalService.contarPorCliente(existente.getCpf());
        if (animais > 0) {
            throw new ServiceException("Cliente possui " + animais + " animal(is). Exclua os animais antes.", null);
        }
        clienteService.excluirCliente(existente);
    }

    private Cliente buscarClienteExistente(String cpf) throws ServiceException {
        List<Cliente> encontrados = clienteService.listarCliente(cpf);
        for (Cliente c : encontrados) {
            if (c.getCpf().trim().equals(cpf.trim())) {
                return c;
            }
        }
        throw new ServiceException("Cliente não encontrado (CPF " + cpf + ")", null);
    }
}