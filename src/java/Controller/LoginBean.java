/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Consulta;
import Model.LoginSenha;
import Persistence.Conexao;
import Persistence.DaoConsulta;
import Persistence.DaoLogin;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Arleide
 */
@Named(value = "lb")
@SessionScoped
public class LoginBean implements Serializable {

    private LoginSenha ls = new LoginSenha();
    private DaoLogin daols;
    private String loginsessao;
    private String senhasessao;
    private DaoConsulta daoc;
    private List<Consulta> consultas;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private String dataformatada = "";
    private Date date1;

    public LoginSenha getLs() {
        return ls;
    }

    public void setLs(LoginSenha ls) {
        this.ls = ls;
    }

    public String getSenhasessao() {
        return senhasessao;
    }

    public void setSenhasessao(String senhasessao) {
        this.senhasessao = senhasessao;
    }

    public String getLoginsessao() {
        return loginsessao;
    }

    public void setLoginsessao(String loginsessao) {
        this.loginsessao = loginsessao;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    public Date getDate1() {
        return date1;
    }
 
    public void setDate1(Date date1) {
        this.date1 = date1;
    }
    
    //-------------------------------- Métodos de ação -------------------------------------------
    
    //Método para fazer o login, redirecionadno para a tela inicial específica de cada um
    public String logar() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Conexao con = new Conexao();
        this.daols = new DaoLogin(con);
        ls = this.daols.search(getLoginsessao(), getSenhasessao());
        if (ls.isEhAdmin()) {
            return "InicialAdministrador";
        } else {
            return "InicialMedico";
        }
    }

    //Método para, a partir do Login do médico, se possa listar toda sua agenda
    //PS: foi escolhido o Login para isto, pois este é inalterável (o nome, pode sofrer mudanças em caso de casamento, por exemplo)
    public String agendaMedico() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Conexao con = new Conexao();
        this.daoc = new DaoConsulta(con); 
        consultas = this.daoc.listarAgendaMedico(getLoginsessao());
        return "AgendaMedico";
    }
    
    //Método para, a partir do login de um determinado médico, obter sua agenda para um determinado dia escolhido
    public String listarMedico() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.daoc = new DaoConsulta(con);  
        df.applyPattern("yyyy-MM-dd");
        dataformatada = df.format(date1);
        consultas = this.daoc.listarPorDataENome(dataformatada, getLoginsessao());
        return "ResultadoData";
    }
    
    
}
