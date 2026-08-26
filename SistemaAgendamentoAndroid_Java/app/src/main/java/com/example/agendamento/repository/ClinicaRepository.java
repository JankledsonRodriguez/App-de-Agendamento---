package com.example.agendamento.repository;

import com.example.agendamento.database.DatabaseManager;
import com.example.agendamento.model.Consulta;
import com.example.agendamento.model.Paciente;
import com.example.agendamento.model.Especialidade;
import com.example.agendamento.model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClinicaRepository {

    public boolean login(String email, String senha) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE email=? AND senha=? LIMIT 1";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, email); p.setString(2, senha);
            try (ResultSet r = p.executeQuery()) { return r.next(); }
        }
    }

    public List<Paciente> listarPacientes() throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, telefone, email FROM pacientes ORDER BY nome";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(new Paciente(r.getInt("id"), r.getString("nome"),
                    r.getString("telefone"), r.getString("email")));
        }
        return lista;
    }

    public boolean inserirPaciente(String nome, String telefone, String email) throws SQLException {
        String sql = "INSERT INTO pacientes(nome, telefone, email) VALUES(?,?,?)";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, nome); p.setString(2, telefone); p.setString(3, email); return p.executeUpdate() == 1;
        }
    }

    public List<Consulta> listarConsultas() throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.paciente_id, c.data, c.hora, c.especialidade, c.observacao, c.status, p.nome paciente_nome " +
                "FROM consultas c JOIN pacientes p ON p.id=c.paciente_id ORDER BY c.data, c.hora";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql); ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(new Consulta(r.getInt("id"), r.getInt("paciente_id"),
                    r.getString("data"), r.getString("hora"), r.getString("especialidade"),
                    r.getString("observacao"), r.getString("status"), r.getString("paciente_nome")));
        }
        return lista;
    }

    public boolean inserirConsulta(int pacienteId, String data, String hora, String especialidade, String obs) throws SQLException {
        String sql = "INSERT INTO consultas(paciente_id, data, hora, especialidade, observacao, status) VALUES(?,?,?,?,?,'AGENDADO')";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, pacienteId); p.setString(2, data); p.setString(3, hora); p.setString(4, especialidade); p.setString(5, obs);
            return p.executeUpdate() == 1;
        }
    }

    public List<Medico> listarCorpoClinico() throws SQLException {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT id, nome, especialidade, crm FROM medicos ORDER BY nome";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(new Medico(r.getInt("id"), r.getString("nome"),
                    r.getString("especialidade"), r.getString("crm")));
        }
        return lista;
    }

    public boolean inserirMedico(String nome, String especialidade, String crm) throws SQLException {
        String sql = "INSERT INTO medicos(nome, especialidade, crm) VALUES(?,?,?)";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, nome); p.setString(2, especialidade); p.setString(3, crm);
            return p.executeUpdate() == 1;
        }
    }

    public boolean autocadastroMedico(String nome, String email, String especialidade, String crm, String senha) throws SQLException {
        Connection c = null;
        try {
            c = DatabaseManager.getConnection();
            c.setAutoCommit(false); // Iniciar transação

            // 1. Inserir em usuarios (para login)
            String sqlUser = "INSERT INTO usuarios(nome, email, senha) VALUES(?,?,?)";
            try (PreparedStatement p1 = c.prepareStatement(sqlUser)) {
                p1.setString(1, nome);
                p1.setString(2, email);
                p1.setString(3, senha);
                p1.executeUpdate();
            }

            // 2. Inserir em medicos (perfil profissional)
            String sqlMedico = "INSERT INTO medicos(nome, especialidade, crm) VALUES(?,?,?)";
            try (PreparedStatement p2 = c.prepareStatement(sqlMedico)) {
                p2.setString(1, nome);
                p2.setString(2, especialidade);
                p2.setString(3, crm);
                p2.executeUpdate();
            }

            c.commit(); // Confirmar tudo
            return true;
        } catch (SQLException e) {
            if (c != null) c.rollback();
            throw e;
        } finally {
            if (c != null) {
                c.setAutoCommit(true);
                c.close();
            }
        }
    }

    public boolean atualizarSenha(String email, String novaSenha) throws SQLException {
        String sql = "UPDATE usuarios SET senha = ? WHERE email = ?";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, novaSenha);
            p.setString(2, email);
            return p.executeUpdate() == 1;
        }
    }

    public List<Especialidade> listarEspecialidades() {
        List<Especialidade> lista = new ArrayList<>();
        lista.add(new Especialidade(1, "Cardiologia", "Saúde do Coração", android.R.drawable.ic_menu_myplaces));
        lista.add(new Especialidade(2, "Dermatologia", "Pele e Estética", android.R.drawable.ic_menu_camera));
        lista.add(new Especialidade(3, "Pediatria", "Saúde Infantil", android.R.drawable.ic_menu_edit));
        lista.add(new Especialidade(4, "Ortopedia", "Ossos e Articulações", android.R.drawable.ic_menu_manage));
        lista.add(new Especialidade(5, "Ginecologia", "Saúde da Mulher", android.R.drawable.ic_menu_agenda));
        lista.add(new Especialidade(6, "Oftalmologia", "Visão e Olhos", android.R.drawable.ic_menu_view));
        return lista;
    }
}
