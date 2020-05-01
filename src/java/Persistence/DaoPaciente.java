/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistence;

import Model.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Aluno
 */
public class DaoPaciente {
    private Connection con;
    
    public DaoPaciente(Conexao con) throws ClassNotFoundException, SQLException,  InstantiationException, IllegalAccessException{
        this.con = con.fabricar();
    }
    
    //Método chamado no PacienteBean -> cadastrar(), para gerar um novo paciente
    public void save(Paciente pac) throws SQLException{
        PreparedStatement pstm = con.prepareStatement("insert into paciente(Nome,Cpf,Telefone,Email) values(?,?,?,?)");
        pstm.setString(1, pac.getNome());
        pstm.setString(2, pac.getCpf());
        pstm.setString(3, pac.getTelefone());
        pstm.setString(4, pac.getEmail());
        pstm.executeUpdate();
        pstm.close();
    }
    
    //Método chamado no PacienteBean -> buscar(), para procurar um paciente, via CPF
    public Paciente search(String cpf) throws SQLException{
        Paciente pac = null;
        PreparedStatement pstm = con.prepareStatement("select * from paciente where cpf=?");
        pstm.setString(1, cpf);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
        pac = new Paciente();
        pac.setNome(rs.getString("Nome"));
        pac.setCpf(rs.getString("Cpf"));
        pac.setTelefone(rs.getString("Telefone"));
        pac.setEmail(rs.getString("Email"));
        }
        return pac;
    }
    
    public void update(Paciente pac) throws SQLException{
        PreparedStatement pstm = con.prepareStatement("update Paciente set Nome = ?, Telefone = ?, Email = ?  where Cpf = ?");
        pstm.setString(1, pac.getNome());
        pstm.setString(2, pac.getTelefone());
        pstm.setString(3, pac.getEmail());
        pstm.setString(4, pac.getCpf());
        pstm.executeUpdate();
        pstm.close();
    }
    
}
