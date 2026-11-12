package br.unicentro.poo.projetopoo.Model;

public class Animal {
    private int id;
    private String nome, especie, raca, sexo;
    private Cliente cliente;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }

    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaca() { return raca; }

    public void setRaca(String raca) { this.raca = raca; }

    public String getSexo() { return sexo; }

    public void setSexo(String sexo) { this.sexo = sexo; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        return nome + (cliente != null ? " - " + cliente.getNome() : "");
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private int id;
        private String nome, especie, raca, sexo;
        private Cliente cliente;

        public Builder id(int id) { this.id = id; return this; }

        public Builder nome(String nome) { this.nome = nome; return this; }

        public Builder especie(String especie) { this.especie = especie; return this; }

        public Builder raca(String raca) { this.raca = raca; return this; }

        public Builder sexo(String sexo) { this.sexo = sexo; return this; }

        public Builder cliente(Cliente cliente) { this.cliente = cliente; return this; }

        public Animal build() {
            Animal a = new Animal();
            a.id = id;
            a.nome = nome;
            a.especie = especie;
            a.raca = raca;
            a.sexo = sexo;
            a.cliente = cliente;
            return a;
        }
    }
}
