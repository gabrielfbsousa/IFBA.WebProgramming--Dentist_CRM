/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Model.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Arleide
 */
public class DaoMed {

    private Connection con;

    public DaoMed(Conexao con) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.con = con.fabricar();
    }

    //Método chamado no MedicoBean -> cadastrar(), para gerar um médico, bem como seu login e senha
    public void save(Medico med) throws SQLException {
        PreparedStatement pstm = con.prepareStatement("insert into LoginSenha(ehAdmin,Login,Senha) values(?,?,?)");
        pstm.setString(1, "false");
        pstm.setString(2, med.getLogin());
        pstm.setString(3, med.getSenha());
        PreparedStatement pstmm = con.prepareStatement("insert into Medico(Nome,Crm,Email,Telefone,Login) values(?,?,?,?,?)");
        pstmm.setString(1, med.getNome());
        pstmm.setString(2, med.getCrm());
        pstmm.setString(3, med.getEmail());
        pstmm.setString(4, med.getTelefone());
        pstmm.setString(5, med.getLogin());
        pstm.executeUpdate();
        pstmm.executeUpdate();
        pstm.close();
        pstmm.close();

    }

    //Método chamado via MedicoBean -> procurar(), para buscar um médico, à partir de sua CRM
    public Medico search(String Crm) throws SQLException {
        Medico med = null;
        PreparedStatement pstm = con.prepareStatement("select * from Medico where Crm =?");
        pstm.setString(1, Crm);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            med = new Medico();
            med.setNome(rs.getString("Nome"));
            med.setCrm(rs.getString("Crm"));
            med.setTelefone(rs.getString("Telefone"));
            med.setEmail(rs.getString("Email"));
            med.setLogin(rs.getString("Login"));
        }
        
        
        PreparedStatement stmt = con.prepareStatement("select * from LoginSenha where Login =?");
        stmt.setString(1, med.getLogin());
        ResultSet rset = stmt.executeQuery();
        while(rset.next()){
            med.setSenha(rset.getString("Senha"));
        }
        
        return med;
    }

    //Método chamado no MédicoBean -> update(), para que se faça alterações num determinado médico desejado
    public void update(Medico med) throws SQLException {
        PreparedStatement pstm = con.prepareStatement("update Medico set Nome = ?, Email = ?, telefone = ? where Login =?");
        pstm.setString(1, med.getNome());
        pstm.setString(2, med.getEmail());
        pstm.setString(3, med.getTelefone());
        pstm.setString(4, med.getLogin());
        
        PreparedStatement stmt = con.prepareStatement("update LoginSenha set Senha =? where Login =?");
        stmt.setString(1, med.getSenha());
        stmt.setString(2, med.getLogin())
                ;
        pstm.executeUpdate();
        stmt.executeUpdate();
        pstm.close();
        stmt.close();
    }    
    
    public void delete (Medico med) throws SQLException{
        PreparedStatement pstm = con.prepareStatement("delete from Medico where crm = ?");
        pstm.setString(1, med.getCrm());
        pstm.executeUpdate();
        pstm.close();
    }
}
