/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistence;

import Model.LoginSenha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Arleide
 */
public class DaoLogin {
 private Connection con;
    
    public DaoLogin(Conexao con) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        this.con = con.fabricar();
    }
    
    public void save(LoginSenha ls) throws SQLException{
        PreparedStatement pstm = con.prepareStatement("insert into LoginSenha(Login,Senha,ID_Funcionario) values(?,?,?)");
        pstm.setString(1, ls.getLogin());
        pstm.setString(2, ls.getSenha());
        pstm.setBoolean(3, ls.isEhAdmin());
        pstm.executeUpdate();
        pstm.close();
    }
    
    //Método chamado em LoginBean -> logar, para que se obtenha as características do usuário deste login
    public LoginSenha search(String login, String senha) throws SQLException{
        LoginSenha ls = null;
        PreparedStatement pstm = con.prepareStatement("select * from LoginSenha where Login = ? and Senha = ?");
        pstm.setString(1, login);
        pstm.setString(2, senha);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()){
            ls = new LoginSenha();
            ls.setLogin(rs.getString("Login"));
            ls.setSenha(rs.getString("Senha"));
            ls.setEhAdmin(rs.getBoolean("ehAdmin"));
        }
        return ls;
    }
}
  
