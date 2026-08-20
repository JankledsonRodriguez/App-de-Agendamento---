package com.example.agendamento.model;

public class Agendamento {
    private int id, clienteId;
    private String data, hora, servico, observacao, status, clienteNome;

    public Agendamento(int id,int clienteId,String data,String hora,String servico,
                       String observacao,String status,String clienteNome){
        this.id=id;this.clienteId=clienteId;this.data=data;this.hora=hora;
        this.servico=servico;this.observacao=observacao;this.status=status;this.clienteNome=clienteNome;
    }
    public int getId(){return id;}
    public int getClienteId(){return clienteId;}
    public String getData(){return data;}
    public String getHora(){return hora;}
    public String getServico(){return servico;}
    public String getObservacao(){return observacao;}
    public String getStatus(){return status;}
    public String getClienteNome(){return clienteNome;}
}
