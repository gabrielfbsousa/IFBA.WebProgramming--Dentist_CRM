/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Administrador;
import Persistence.Conexao;
import Persistence.DaoAdm;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Aluno
 */
@ManagedBean (name = "cub")
@SessionScoped
public class UsuarioBean implements Serializable{

    private Administrador adm = new Administrador();
    private DaoAdm dao;
    
   
    public Administrador getAdm() {
        return adm;
    }

    public void setAdm(Administrador adm) {
        this.adm = adm;
    }
    
    //Método para cadastrar um novo administrador
    public String cadastrar() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.dao = new DaoAdm(con);
        this.dao.save(this.adm);
        adm = new Administrador();
        return "index";
    }
    
    //Método para buscar um administrador, à partir de seu CPF
    public String procurar() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.dao = new DaoAdm(con);
        adm = this.dao.search(getAdm().getCpf());
        return "EditarUsuario";
    }
    
    //Método para dar update num administrador
    public String update() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.dao = new DaoAdm(con);
        this.dao.update(this.adm);
        return "InicialAdministrador";
    }
    
    
    //Método para deletar um administrador
    public String delete() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.dao = new DaoAdm(con);
        this.dao.delete(this.adm);
        return "InicialAdministrador";
    }
}
