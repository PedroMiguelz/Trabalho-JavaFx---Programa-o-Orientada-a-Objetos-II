package br.unicentro.poo.projetopoo.Facade;

import br.unicentro.poo.projetopoo.Model.Veterinario;
import br.unicentro.poo.projetopoo.Service.ConsultaService;
import br.unicentro.poo.projetopoo.Service.ServiceException;
import br.unicentro.poo.projetopoo.Service.ServiceFactory;
import br.unicentro.poo.projetopoo.Service.VeterinarioService;

import java.util.List;

public class VeterinarioFacade {

    private final VeterinarioService veterinarioService = ServiceFactory.criarVeterinarioService();
    private final ConsultaService consultaService = ServiceFactory.criarConsultaService();

    // Bloqueia a exclusão se o veterinário tiver consultas.
    public void excluirVeterinario(Veterinario veterinario) throws ServiceException {
        Veterinario existente = buscarVeterinarioExistente(veterinario.getCrmv());
        int consultas = consultaService.contarPorVeterinario(existente.getCrmv());
        if (consultas > 0) {
            throw new ServiceException("Veterinário possui " + consultas + " consulta(s). Exclua as consultas antes.", null);
        }
        veterinarioService.excluirVeterinario(existente);
    }

    private Veterinario buscarVeterinarioExistente(String crmv) throws ServiceException {
        List<Veterinario> encontrados = veterinarioService.listarVeterinario(crmv);
        for (Veterinario v : encontrados) {
            if (v.getCrmv().trim().equalsIgnoreCase(crmv.trim())) {
                return v;
            }
        }
        throw new ServiceException("Veterinário não encontrado (CRMV " + crmv + ")", null);
    }
}