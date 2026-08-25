package com.example.agendamento.model;

public class Medico {
    private int id;
    private String nome;
    private String especialidade;
    private String registroCRM;

    public Medico(int id, String nome, String especialidade, String registroCRM) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.registroCRM = registroCRM;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
    public String getRegistroCRM() { return registroCRM; }
}
