package com.example.agendamento.model;

public class Especialidade {
    private int id;
    private String nome;
    private String descricao;
    private int iconeResId;

    public Especialidade(int id, String nome, String descricao, int iconeResId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.iconeResId = iconeResId;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getIconeResId() { return iconeResId; }
}
