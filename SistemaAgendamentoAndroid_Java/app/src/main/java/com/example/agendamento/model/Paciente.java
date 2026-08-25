package com.example.agendamento.model;

public class Paciente {
    private int id;
    private String nome;
    private String telefone;
    private String email;

    public Paciente(int id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
}
