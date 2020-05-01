/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Consulta;
import Model.Paciente;
import Persistence.Conexao;
import Persistence.DaoConsulta;
import Persistence.DaoPaciente;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Aluno
 */
@ManagedBean(name = "pb")
@SessionScoped
public class PacienteBean implements Serializable{

   private Paciente pac = new Paciente();
   private DaoPaciente dao;
   private ArrayList<Consulta> consultas = new ArrayList<Consulta>();
   private Consulta consulta;
   private DaoConsulta daoc;

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }  
   
    public Paciente getPac() {
        return pac;
    }

    public void setPac(Paciente pac) {
        this.pac = pac;
    }
    
    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
    }
   
   //--------------------------------------- Métodos de ação ---------------------------
    
    
   //Método para cadastrar um paciente 
   public String cadastrar() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
       Conexao con = new Conexao();
       this.dao = new DaoPaciente(con);
       this.dao.save(this.pac);
       return "InicialAdministrador";
   }
   
   //Método para buscar um paciente
   public String buscar() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException{
       Conexao con = new Conexao();
       this.dao = new DaoPaciente(con);      
       pac = this.dao.search(this.pac.getCpf());
       return "AcoesPaciente";
   }
   
   //Método para dar update num paciente
   public String update() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
       Conexao con = new Conexao();
       this.dao = new DaoPaciente(con);
       this.dao.update(this.pac);
       return "InicialAdministrador";
   }
   
   //Método para listar o histórico de consultas de um paciente, ao utilizar um DaoConsulta
   public String historico() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        DaoConsulta dao = new DaoConsulta(con);
        this.setConsultas(dao.search(pac));
        return "ListarHistorico";
   }
   
   //Método para cadastrar uma consulta para um paciente, utilizando um DaoConsulta
   public String cadastrarConsulta() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.daoc = new DaoConsulta(con);       
        daoc.cadastrarConsultaPaciente(consulta, this.pac.getCpf());
        return "InicialAdministrador";
       
    }   
   
   
   //Método para buscar a lista de consultas de um determinado paciente, para que posteriormente um médico possa adicionar uma descrição
   public String buscarConsultaMedico() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        Conexao con = new Conexao();
        this.dao = new DaoPaciente(con);
        pac = this.dao.search(this.pac.getCpf());
        this.daoc = new DaoConsulta(con);
        this.setConsultas(this.daoc.search(pac));
        return "ResultadoCPF";
    }
   
   /*Método para que o médico possa atribuir uma descrição à consulta de um determinado paciente, que foi selecionada dentre as listadas no método
    acima */
   public String updateConsulta() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Conexao con = new Conexao();
        DaoConsulta dao = new DaoConsulta(con);
        dao.updateConsulta(consulta);
        return "ConsultaData";
    }

    
}





