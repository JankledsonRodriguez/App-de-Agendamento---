package com.example.agendamento.model;

public class Consulta {
    private int id;
    private int pacienteId;
    private String data;
    private String hora;
    private String especialidade;
    private String observacao;
    private String status;
    private String pacienteNome;

    public Consulta(int id, int pacienteId, String data, String hora, String especialidade,
                    String observacao, String status, String pacienteNome) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.data = data;
        this.hora = hora;
        this.especialidade = especialidade;
        this.observacao = observacao;
        this.status = status;
        this.pacienteNome = pacienteNome;
    }

    public int getId() { return id; }
    public int getPacienteId() { return pacienteId; }
    public String getData() { return data; }
    public String getHora() { return hora; }
    public String getEspecialidade() { return especialidade; }
    public String getObservacao() { return observacao; }
    public String getStatus() { return status; }
    public String getPacienteNome() { return pacienteNome; }
}
