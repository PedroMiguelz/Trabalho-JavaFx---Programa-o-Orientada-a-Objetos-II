package br.unicentro.poo.projetopoo.Model;

import java.time.LocalDate;

public class Veterinario {
    private String crmv, nome, especializacao, telefone;
    private LocalDate dataEntrada;

    public String getCrmv() { return crmv; }

    public void setCrmv(String crmv) { this.crmv = crmv; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataEntrada() { return dataEntrada; }

    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public String getEspecializacao() { return especializacao; }

    public void setEspecializacao(String especializacao) { this.especializacao = especializacao; }

    public String getTelefone() { return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() { return nome; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String crmv, nome, especializacao, telefone;
        private LocalDate dataEntrada;

        public Builder crmv(String crmv) { this.crmv = crmv; return this; }

        public Builder nome(String nome) { this.nome = nome; return this; }

        public Builder especializacao(String especializacao) { this.especializacao = especializacao; return this; }

        public Builder telefone(String telefone) { this.telefone = telefone; return this; }

        public Builder dataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; return this; }

        public Veterinario build() {
            Veterinario v = new Veterinario();
            v.crmv = crmv;
            v.nome = nome;
            v.especializacao = especializacao;
            v.telefone = telefone;
            v.dataEntrada = dataEntrada;
            return v;
        }
    }
}
