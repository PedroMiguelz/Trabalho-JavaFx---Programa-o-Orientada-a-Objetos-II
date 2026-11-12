package br.unicentro.poo.projetopoo.Service;

public class ServiceFactory {

    private ServiceFactory() {}

    public static ClienteService criarClienteService() { return new ClienteService(); }

    public static AnimalService criarAnimalService() { return new AnimalService(); }

    public static VeterinarioService criarVeterinarioService() { return new VeterinarioService(); }

    public static ConsultaService criarConsultaService() { return new ConsultaService(); }
}
