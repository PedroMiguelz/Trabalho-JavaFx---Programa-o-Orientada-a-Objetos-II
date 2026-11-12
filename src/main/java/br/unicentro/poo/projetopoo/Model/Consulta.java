package br.unicentro.poo.projetopoo.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private int id;
    private LocalDate data;
    private BigDecimal valor;
    private Animal animal;
    private Veterinario veterinario;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public LocalDate getData() { return data; }

    public void setData(LocalDate data) { this.data = data; }

    public BigDecimal getValor() { return valor; }

    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Animal getAnimal() { return animal; }

    public void setAnimal(Animal animal) { this.animal = animal; }

    public Veterinario getVeterinario() { return veterinario; }

    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private int id;
        private LocalDate data;
        private BigDecimal valor;
        private Animal animal;
        private Veterinario veterinario;

        public Builder id(int id) { this.id = id; return this; }

        public Builder data(LocalDate data) { this.data = data; return this; }

        public Builder valor(BigDecimal valor) { this.valor = valor; return this; }

        public Builder animal(Animal animal) { this.animal = animal; return this; }

        public Builder veterinario(Veterinario veterinario) { this.veterinario = veterinario; return this; }

        public Consulta build() {
            Consulta c = new Consulta();
            c.id = id;
            c.data = data;
            c.valor = valor;
            c.animal = animal;
            c.veterinario = veterinario;
            return c;
        }
    }
}
