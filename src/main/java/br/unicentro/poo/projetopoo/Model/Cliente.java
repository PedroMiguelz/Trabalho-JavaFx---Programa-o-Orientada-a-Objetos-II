package br.unicentro.poo.projetopoo.Model;

public class Cliente {
    private String cpf, nome, telefone;

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() { return nome; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String cpf, nome, telefone;

        public Builder cpf(String cpf) { this.cpf = cpf; return this; }

        public Builder nome(String nome) { this.nome = nome; return this; }

        public Builder telefone(String telefone) { this.telefone = telefone; return this; }

        public Cliente build() {
            Cliente c = new Cliente();
            c.cpf = cpf;
            c.nome = nome;
            c.telefone = telefone;
            return c;
        }
    }
}
