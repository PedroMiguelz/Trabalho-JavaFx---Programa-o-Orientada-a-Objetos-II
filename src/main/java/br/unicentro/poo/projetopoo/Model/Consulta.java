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
    //public LocalTime getHora() { return hora; }
    //public void setHora(LocalTime hora) { this.hora = hora; }
    public BigDecimal getValor() { return valor; }

    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Animal getAnimal() { return animal; }

    public void setAnimal(Animal animal) { this.animal = animal; }

    public Veterinario getVeterinario() { return veterinario; }

    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }
}
