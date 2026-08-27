package com.example.agendamento.repository;

import android.app.AlertDialog;
import android.util.Log;

import com.example.agendamento.LoginActivity;
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
            try (ResultSet r = p.executeQuery()) { return r.next();
                }


        }
    }

    public List<Paciente> listarPacientes() throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, data_nascimento, telefone, email, endereco FROM pacientes ORDER BY nome";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(new Paciente(r.getInt("id"), r.getString("nome"),
                    r.getString("cpf"), r.getString("data_nascimento"),
                    r.getString("telefone"), r.getString("email"), r.getString("endereco")));
        }
        return lista;
    }

    public boolean inserirPaciente(String nome, String cpf, String dataNasc, String telefone, String email, String endereco) throws SQLException {
        String sql = "INSERT INTO pacientes(nome, cpf, data_nascimento, telefone, email, endereco) VALUES(?,?,?,?,?,?)";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, nome); 
            p.setString(2, cpf); 
            p.setString(3, dataNasc); 
            p.setString(4, telefone); 
            p.setString(5, email); 
            p.setString(6, endereco); 
            return p.executeUpdate() == 1;
        }
    }

    public Paciente buscarPacientePorId(int id) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return new Paciente(r.getInt("id"), r.getString("nome"),
                        r.getString("cpf"), r.getString("data_nascimento"),
                        r.getString("telefone"), r.getString("email"), r.getString("endereco"));
            }
        }
        return null;
    }

    public List<Consulta> listarConsultasPorPaciente(int pacienteId) throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT c.*, p.nome paciente_nome, m.nome medico_nome " +
                     "FROM consultas c " +
                     "JOIN pacientes p ON p.id=c.paciente_id " +
                     "LEFT JOIN medicos m ON m.id=c.medico_id " +
                     "WHERE c.paciente_id = ? ORDER BY c.data DESC, c.hora DESC";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, pacienteId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    lista.add(new Consulta(r.getInt("id"), r.getInt("paciente_id"),
                        r.getString("data"), r.getString("hora"), r.getString("especialidade"),
                        r.getString("observacao"), r.getString("status"), 
                        r.getString("paciente_nome"), r.getString("medico_nome")));
                }
            }
        }
        return lista;
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

    public boolean atualizarStatusConsulta(int id, String novoStatus) throws SQLException {
        String sql = "UPDATE consultas SET status = ? WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, novoStatus.toUpperCase());
            p.setInt(2, id);
            return p.executeUpdate() == 1;
        }
    }

    public boolean reagendarConsulta(int id, String novaData, String novaHora) throws SQLException {
        String sql = "UPDATE consultas SET data = ?, hora = ?, status = 'AGENDADO' WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, novaData);
            p.setString(2, novaHora);
            p.setInt(3, id);
            return p.executeUpdate() == 1;
        }
    }

    public List<Consulta> listarAgendaFiltrada(String dataInicio, String dataFim, String especialidade, String status) throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT c.id, c.paciente_id, c.data, c.hora, c.especialidade, c.observacao, c.status, p.nome paciente_nome " +
                "FROM consultas c JOIN pacientes p ON p.id=c.paciente_id WHERE 1=1");
        
        if (dataInicio != null && dataFim != null) {
            if (dataInicio.equals(dataFim)) sql.append(" AND c.data = ?");
            else sql.append(" AND c.data BETWEEN ? AND ?");
        } else if (dataInicio != null) {
            sql.append(" AND c.data >= ?");
        }
        
        if (especialidade != null && !especialidade.isEmpty()) sql.append(" AND c.especialidade = ?");
        if (status != null && !status.isEmpty()) sql.append(" AND c.status = ?");
        
        sql.append(" ORDER BY c.data, c.hora");

        try (Connection c = DatabaseManager.getConnection(); PreparedStatement p = c.prepareStatement(sql.toString())) {
            int i = 1;
            if (dataInicio != null && dataFim != null) {
                p.setString(i++, dataInicio);
                if (!dataInicio.equals(dataFim)) p.setString(i++, dataFim);
            } else if (dataInicio != null) {
                p.setString(i++, dataInicio);
            }
            
            if (especialidade != null && !especialidade.isEmpty()) {
                p.setString(i++, especialidade);
            }
            if (status != null && !status.isEmpty()) {
                p.setString(i++, status.toUpperCase());
            }

            try (ResultSet r = p.executeQuery()) {
                while (r.next()) lista.add(new Consulta(r.getInt("id"), r.getInt("paciente_id"),
                        r.getString("data"), r.getString("hora"), r.getString("especialidade"),
                        r.getString("observacao"), r.getString("status"), r.getString("paciente_nome")));
            }
        }
        return lista;
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

    public List<Medico> listarMedicosPorEspecialidade(String especialidade) throws SQLException {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT id, nome, especialidade, crm FROM medicos WHERE especialidade = ? ORDER BY nome";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, especialidade);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) lista.add(new Medico(r.getInt("id"), r.getString("nome"),
                        r.getString("especialidade"), r.getString("crm")));
            }
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

    public List<Especialidade> listarEspecialidades() throws SQLException {
        List<Especialidade> lista = new ArrayList<>();
        // Busca especialidades únicas diretamente da tabela de médicos para garantir que sempre apareçam
        String sql = "SELECT DISTINCT especialidade FROM medicos ORDER BY especialidade";
        
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            
            int id = 1;
            while (r.next()) {
                String nome = r.getString("especialidade");
                String desc = "Atendimento especializado em " + nome;
                
                // Atribuição de ícones baseada no nome
                int icone = getIconeParaEspecialidade(nome);
                
                lista.add(new Especialidade(id++, nome, desc, icone));
            }
        }
        return lista;
    }

    private int getIconeParaEspecialidade(String nome) {
        String n = nome.toLowerCase();
        if (n.contains("cardio")) return android.R.drawable.ic_menu_myplaces;
        if (n.contains("derma")) return android.R.drawable.ic_menu_camera;
        if (n.contains("pediatra")) return android.R.drawable.ic_menu_edit;
        if (n.contains("orto")) return android.R.drawable.ic_menu_manage;
        if (n.contains("gineco")) return android.R.drawable.ic_menu_agenda;
        if (n.contains("oftalmo")) return android.R.drawable.ic_menu_view;
        return android.R.drawable.ic_menu_info_details; // Ícone genérico
    }

    public java.util.Map<String, Integer> getEstatisticasDashboard() throws SQLException {
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        String hoje = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(new java.util.Date());

        try (Connection c = DatabaseManager.getConnection()) {
            // Consultas de hoje
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM consultas WHERE data = ?")) {
                p.setString(1, hoje);
                try (ResultSet r = p.executeQuery()) { if (r.next()) stats.put("hoje", r.getInt(1)); }
            }
            // Próximas consultas (futuras)
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM consultas WHERE data > ?")) {
                p.setString(1, hoje);
                try (ResultSet r = p.executeQuery()) { if (r.next()) stats.put("proximas", r.getInt(1)); }
            }
            // Confirmadas
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM consultas WHERE status = 'CONFIRMADO'")) {
                try (ResultSet r = p.executeQuery()) { if (r.next()) stats.put("confirmadas", r.getInt(1)); }
            }
            // Pendentes (AGENDADO)
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM consultas WHERE status = 'AGENDADO'")) {
                try (ResultSet r = p.executeQuery()) { if (r.next()) stats.put("pendentes", r.getInt(1)); }
            }
            // Canceladas
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM consultas WHERE status = 'CANCELADO'")) {
                try (ResultSet r = p.executeQuery()) { if (r.next()) stats.put("canceladas", r.getInt(1)); }
            }
            // Total Pacientes
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM pacientes")) {
                try (ResultSet r = p.executeQuery()) { if (r.next()) stats.put("pacientes", r.getInt(1)); }
            }
            // Total Médicos
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM medicos")) {
                try (ResultSet r = p.executeQuery()) { if (r.next()) stats.put("medicos", r.getInt(1)); }
            }
        }
        return stats;
    }
}
