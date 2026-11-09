package br.unicentro.poo.projetopoo.Service;

public class ServiceException extends Exception{

    public ServiceException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
