package com.example.agendamento.model;

public class Profissional {
    private int id;
    private String nome;
    private String especialidade;
    private String telefone;

    public Profissional(int id, String nome, String especialidade, String telefone) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.telefone = telefone;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
    public String getTelefone() { return telefone; }
}
