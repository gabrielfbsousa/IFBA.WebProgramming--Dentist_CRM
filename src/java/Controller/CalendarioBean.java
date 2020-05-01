/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Consulta;
import Persistence.Conexao;
import Persistence.DaoConsulta;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
 

@ManagedBean
@SessionScoped
public class CalendarioBean implements Serializable{
         
    private Date date1;
    private List<Consulta> consultas;
    private DaoConsulta dao;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private String dataformatada = "";
     
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
     
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
    
     public Date getDate1() {
        return date1;
    }
 
    public void setDate1(Date date1) {
        this.date1 = date1;
    }


    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    //------------------------------------ Métodos de ação --------------------------------------------
    
    //Método do administrador para que se obtenha todas as consultas em uma determinada data escolhida
    public String listar() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.dao = new DaoConsulta(con); 
        df.applyPattern("yyyy-MM-dd");
        dataformatada = df.format(date1);
        consultas = this.dao.listarPorData(dataformatada);
        return "HorariosGerais";
    }
    
    //Método do médico, para que se obtenha todas as consultas para uma determinada data
    public String listarMedico() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.dao = new DaoConsulta(con);  
        df.applyPattern("yyyy-MM-dd");
        dataformatada = df.format(date1);
        consultas = this.dao.listarPorData(dataformatada);
        return "ResultadoData";
    }
    
   
    
    
}