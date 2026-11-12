package br.unicentro.poo.projetopoo.Facade;

import br.unicentro.poo.projetopoo.Model.Animal;
import br.unicentro.poo.projetopoo.Model.Consulta;
import br.unicentro.poo.projetopoo.Model.Veterinario;
import br.unicentro.poo.projetopoo.Service.AnimalService;
import br.unicentro.poo.projetopoo.Service.ConsultaService;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.VeterinarioService;

import java.util.List;

public class ConsultaFacade {

    private final ConsultaService consultaService = ServiceFactory.criarConsultaService();
    private final AnimalService animalService = ServiceFactory.criarAnimalService();
    private final VeterinarioService veterinarioService = ServiceFactory.criarVeterinarioService();

    public void cadastrarConsulta(Consulta consulta) throws ServiceException {
        Consulta completa = montarConsultaCompleta(consulta);
        consultaService.cadastrarConsulta(completa);
    }

    public void atualizarConsulta(Consulta consulta) throws ServiceException {
        if (consultaService.listarConsulta(consulta.getId()).isEmpty()) {
            throw new ServiceException("Consulta não encontrada (id " + consulta.getId() + ")", null);
        }
        Consulta completa = montarConsultaCompleta(consulta);
        completa = Consulta.builder()
                .id(consulta.getId())
                .data(completa.getData())
                .valor(completa.getValor())
                .animal(completa.getAnimal())
                .veterinario(completa.getVeterinario())
                .build();
        consultaService.atualizarConsulta(completa);
    }

    private Consulta montarConsultaCompleta(Consulta consulta) throws ServiceException {
        Animal animal = buscarAnimal(consulta.getAnimal().getId());
        Veterinario veterinario = buscarVeterinario(consulta.getVeterinario().getCrmv());

        return Consulta.builder()
                .data(consulta.getData())
                .valor(consulta.getValor())
                .animal(animal)
                .veterinario(veterinario)
                .build();
    }

    private Animal buscarAnimal(int id) throws ServiceException {
        Animal animal = animalService.listarAnimalId(id);
        if (animal == null) {
            throw new ServiceException("Animal não encontrado (id " + id + ")", null);
        }
        return animal;
    }

    private Veterinario buscarVeterinario(String crmv) throws ServiceException {
        List<Veterinario> encontrados = veterinarioService.listarVeterinario(crmv);
        for (Veterinario v : encontrados) {
            if (v.getCrmv().trim().equalsIgnoreCase(crmv.trim())) {
                return v;
            }
        }
        throw new ServiceException("Veterinário não encontrado (CRMV " + crmv + ")", null);
    }
}