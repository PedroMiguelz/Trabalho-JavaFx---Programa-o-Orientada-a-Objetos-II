package br.unicentro.poo.projetopoo.Model;

public class Cliente {
    private String cpf, nome, telefone;
    private int qtdAnimais;

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public int getQtdAnimais() { return qtdAnimais; }
    public void setQtdAnimais(int qtdAnimais) { this.qtdAnimais = qtdAnimais; }

    @Override
    public String toString() { return nome; }
}