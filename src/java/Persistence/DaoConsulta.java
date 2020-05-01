/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistence;

import Model.Consulta;
import Model.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gabrielferreira
 */
public class DaoConsulta {
    private Connection con;
    
    
    public DaoConsulta(Conexao con) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        this.con = con.fabricar();
    }
    
    //Método chamado em PacienteBean -> historico(), usado para listar todas as consultas de um determinado paciente
    public ArrayList<Consulta> search(Paciente pac) throws SQLException{
        ArrayList<Consulta> resultado = new ArrayList<Consulta>();
        Consulta consulta = null;
        PreparedStatement pstm = con.prepareStatement("select * from Consulta where cpf = ?");
        pstm.setString(1, pac.getCpf());
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            consulta = new Consulta();
            consulta.setNomemedico(rs.getString("Nome"));
            consulta.setCpfpaciente(rs.getString("Cpf"));
            consulta.setDescricao(rs.getString("Descricao"));
            consulta.setHora(rs.getString("Horario"));
            consulta.setDia(rs.getString("Dia"));
            consulta.setCd_consulta(rs.getString("Cd_consulta"));
            resultado.add(consulta);
        }
        
        return resultado;
    }
    
    
    //Método chamado em MédicoBean -> listar(), para se obter toda a agenda de um determinado médico
    public ArrayList<Consulta> listar(String nome_do_medico) throws SQLException{
        ArrayList<Consulta> horarios = new ArrayList<Consulta>();
        PreparedStatement pstm = con.prepareStatement("select * from Consulta where nome = ?");
        pstm.setString(1, nome_do_medico);
        //pstm.setString(2, data);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            Consulta consulta = new Consulta();
            consulta.setCpfpaciente(rs.getString("Cpf"));
            consulta.setNomemedico(rs.getString("Nome"));
            consulta.setCd_consulta(rs.getString("Cd_consulta"));
            consulta.setDescricao(rs.getString("Descricao"));
            consulta.setDia(rs.getString("Dia"));
            consulta.setHora(rs.getString("Horario"));
            horarios.add(consulta);
        }
        return horarios;
    }
    //Método chamado em MédicoBean -> cadastrar() para, assim que criar um médico, iniciar novas consultas para ele, nulas
    public void cadastrarConsultaMedico(String dia, String hora, String nome_medico) throws SQLException{
        PreparedStatement pstm = con.prepareStatement("insert into consulta(dia, horario, nome) values(?,?,?)");
        pstm.setString(1, dia);//mês
        pstm.setString(2, hora);//dia
        pstm.setString(3, nome_medico);
        pstm.executeUpdate();
    }
    
    //Método chamado no PacienteBean, para cadastrar uma consulta escolhida, para um paciente  
    public void cadastrarConsultaPaciente(Consulta consulta_selecionada, String cpf) throws SQLException{
        PreparedStatement pstm = con.prepareStatement("update consulta set cpf =? where cd_consulta = ?");
        pstm.setString(1, cpf);
        pstm.setString(2, consulta_selecionada.getCd_consulta());
        pstm.executeUpdate();
    }
    
    //Método chamado em PacienteBean -> updateConsulta(), para que se possa editar uma consulta, atribuindo à ela uma descrição.
    public void updateConsulta(Consulta consulta) throws SQLException{
       PreparedStatement pstm = con.prepareStatement("update consulta set descricao = ? where cd_consulta = ?");
       pstm.setString(1, consulta.getDescricao());
       pstm.setString(2, consulta.getCd_consulta());
       pstm.executeUpdate();
        
    }
    
    //Método chamado em LoginBean -> agendaMedico(), para que se obtenha, a partir de um login, a agenda completa de um determinado médico
    public ArrayList<Consulta> listarAgendaMedico(String loginsessao) throws SQLException{
        ArrayList<Consulta> agenda = new ArrayList<Consulta>();
        PreparedStatement pstm = con.prepareStatement("select * from Consulta as c inner join Medico as m on c.nome = m.nome inner join Loginsenha as s on m.login = s.login where c.nome = ?");
        pstm.setString(1, loginsessao);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            Consulta consulta = new Consulta();
            consulta.setCpfpaciente(rs.getString("Cpf"));
            consulta.setNomemedico(rs.getString("Nome"));
            consulta.setCd_consulta(rs.getString("Cd_consulta"));
            consulta.setDescricao(rs.getString("Descricao"));
            consulta.setDia(rs.getString("Dia"));
            consulta.setHora(rs.getString("Horario"));
            agenda.add(consulta);
        }
        return agenda;
    }     
     
     //Método chamado em LoginBean -> listarMedico(), para que, através de uma data e de um login, se obtenha a agenda daquele dia para um médico.
     public ArrayList<Consulta> listarPorDataENome(String data, String nome) throws SQLException{
        ArrayList<Consulta> horarios = new ArrayList<Consulta>();
        PreparedStatement pstm = con.prepareStatement("select * from Consulta where dia = ? and nome = ?");
        pstm.setString(1, data);
        pstm.setString(2, nome);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            Consulta consulta = new Consulta();
            consulta.setCpfpaciente(rs.getString("Cpf"));
            consulta.setNomemedico(rs.getString("Nome"));
            consulta.setCd_consulta(rs.getString("Cd_consulta"));
            consulta.setDescricao(rs.getString("Descricao"));
            consulta.setDia(rs.getString("Dia"));
            consulta.setHora(rs.getString("Horario"));
            horarios.add(consulta);
        }
        return horarios;
    }
     
     //Método chamado em CalendarioBean -> listar(), para que se obtenha todas as consultas, para uma determinada data especificada
     public ArrayList<Consulta> listarPorData(String data) throws SQLException{
        ArrayList<Consulta> horarios = new ArrayList<Consulta>();
        PreparedStatement pstm = con.prepareStatement("select * from Consulta where dia = ?");
        pstm.setString(1, data);
        //pstm.setString(2, data);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            Consulta consulta = new Consulta();
            consulta.setCpfpaciente(rs.getString("Cpf"));
            consulta.setNomemedico(rs.getString("Nome"));
            consulta.setCd_consulta(rs.getString("Cd_consulta"));
            consulta.setDescricao(rs.getString("Descricao"));
            consulta.setDia(rs.getString("Dia"));
            consulta.setHora(rs.getString("Horario"));
            horarios.add(consulta);
        }
        return horarios;
    }
}
