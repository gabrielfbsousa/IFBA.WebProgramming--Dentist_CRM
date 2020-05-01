/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistence;

import Model.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Aluno
 */
public class DaoAdm {
    private Connection con;
    
    public DaoAdm(Conexao con) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        this.con = con.fabricar();
    }
    
    //chamado no UsuárioBean -> cadastrar(), para cadastrar um administrador
    public void save(Administrador adm) throws SQLException{
        PreparedStatement pstm = con.prepareStatement("insert into LoginSenha(Login,Senha,ehAdmin) values(?,?,?)");
        pstm.setString(1, adm.getLogin());
        pstm.setString(2, adm.getSenha());
        pstm.setString(3, "true");
        PreparedStatement admstm = con.prepareStatement("insert into Administrador(Nome,Cpf,Login) values(?,?,?)");
        admstm.setString(1, adm.getNome());
        admstm.setString(2, adm.getCpf());
        admstm.setString(3, adm.getLogin());
        pstm.executeUpdate();
        admstm.executeUpdate();
        pstm.close();
        admstm.close();
       
    }
    
    //chamado no UsuárioBean -> buscar(), para achar um administrador, via CPF
    public Administrador search(String cpf) throws SQLException{
        Administrador adm = null;
        PreparedStatement pstm = con.prepareStatement("select * from Administrador where cpf =? ");
        pstm.setString(1, cpf);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            adm = new Administrador();
            adm.setNome(rs.getString("Nome"));
            adm.setCpf(rs.getString("Cpf"));
            adm.setLogin(rs.getString("Login"));
        }
        pstm.close();
        
        PreparedStatement stmt = con.prepareStatement("select * from LoginSenha where Login =?");
        stmt.setString(1, adm.getLogin());
        ResultSet rset = stmt.executeQuery();
        while(rset.next()){
            adm.setSenha(rset.getString("Senha"));
        }
        stmt.close();
        
        return adm;               
    }
    
    //chamado no UsuárioBean -> update(), para dar update num administrador
    public void update(Administrador adm) throws SQLException{
        PreparedStatement pstm = con.prepareStatement("update Administrador set Nome =? where Cpf=?");
        pstm.setString(1, adm.getNome());
        pstm.setString(3, adm.getCpf());
        pstm.executeUpdate();
        pstm.close();
        
        PreparedStatement stmt = con.prepareStatement("update LoginSenha set Senha =? set where Login =?");
        stmt.setString(1, adm.getSenha());
        stmt.setString(2, adm.getLogin());
        stmt.executeUpdate();
        stmt.close();
    }
    
    //chamado no UsuárioBean -> delete(), para deletar um administrador
    public void delete(Administrador adm) throws SQLException{
     PreparedStatement pstm = con.prepareStatement("delete from Administrador where cpf = ?");
     pstm.setString(1, adm.getCpf());
     pstm.executeUpdate();
     pstm.close();
    }
    
    
    
}
