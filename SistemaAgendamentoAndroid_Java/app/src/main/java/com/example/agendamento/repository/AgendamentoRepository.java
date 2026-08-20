package com.example.agendamento.repository;

import com.example.agendamento.database.DatabaseManager;
import com.example.agendamento.model.Agendamento;
import com.example.agendamento.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepository {

    public boolean login(String email, String senha) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE email=? AND senha=? LIMIT 1";
        try (Connection c=DatabaseManager.getConnection();
             PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,email); p.setString(2,senha);
            try(ResultSet r=p.executeQuery()){ return r.next(); }
        }
    }

    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> lista=new ArrayList<>();
        String sql="SELECT id,nome,telefone,email FROM clientes ORDER BY nome";
        try(Connection c=DatabaseManager.getConnection();
            PreparedStatement p=c.prepareStatement(sql);
            ResultSet r=p.executeQuery()){
            while(r.next()) lista.add(new Cliente(r.getInt("id"),r.getString("nome"),
                    r.getString("telefone"),r.getString("email")));
        }
        return lista;
    }

    public boolean inserirCliente(String nome,String telefone,String email)throws SQLException{
        String sql="INSERT INTO clientes(nome,telefone,email) VALUES(?,?,?)";
        try(Connection c=DatabaseManager.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,nome);p.setString(2,telefone);p.setString(3,email);return p.executeUpdate()==1;
        }
    }

    public boolean atualizarCliente(int id,String nome,String telefone,String email)throws SQLException{
        String sql="UPDATE clientes SET nome=?,telefone=?,email=? WHERE id=?";
        try(Connection c=DatabaseManager.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,nome);p.setString(2,telefone);p.setString(3,email);p.setInt(4,id);return p.executeUpdate()==1;
        }
    }

    public boolean excluirCliente(int id)throws SQLException{
        try(Connection c=DatabaseManager.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM clientes WHERE id=?")){
            p.setInt(1,id);return p.executeUpdate()==1;
        }
    }

    public List<Agendamento> listarAgendamentos() throws SQLException{
        List<Agendamento> lista=new ArrayList<>();
        String sql="SELECT a.id,a.cliente_id,a.data,a.hora,a.servico,a.observacao,a.status,c.nome cliente_nome "+
                "FROM agendamentos a JOIN clientes c ON c.id=a.cliente_id ORDER BY a.data,a.hora";
        try(Connection c=DatabaseManager.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){
            while(r.next()) lista.add(new Agendamento(r.getInt("id"),r.getInt("cliente_id"),
                    r.getString("data"),r.getString("hora"),r.getString("servico"),
                    r.getString("observacao"),r.getString("status"),r.getString("cliente_nome")));
        }
        return lista;
    }

    public boolean inserirAgendamento(int clienteId,String data,String hora,String servico,String obs)throws SQLException{
        String sql="INSERT INTO agendamentos(cliente_id,data,hora,servico,observacao,status) VALUES(?,?,?,?,?,'AGENDADO')";
        try(Connection c=DatabaseManager.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,clienteId);p.setString(2,data);p.setString(3,hora);p.setString(4,servico);p.setString(5,obs);
            return p.executeUpdate()==1;
        }
    }

    public boolean atualizarAgendamento(int id,int clienteId,String data,String hora,String servico,String obs,String status)throws SQLException{
        String sql="UPDATE agendamentos SET cliente_id=?,data=?,hora=?,servico=?,observacao=?,status=? WHERE id=?";
        try(Connection c=DatabaseManager.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,clienteId);p.setString(2,data);p.setString(3,hora);p.setString(4,servico);
            p.setString(5,obs);p.setString(6,status);p.setInt(7,id);return p.executeUpdate()==1;
        }
    }

    public boolean excluirAgendamento(int id)throws SQLException{
        try(Connection c=DatabaseManager.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM agendamentos WHERE id=?")){
            p.setInt(1,id);return p.executeUpdate()==1;
        }
    }
}
