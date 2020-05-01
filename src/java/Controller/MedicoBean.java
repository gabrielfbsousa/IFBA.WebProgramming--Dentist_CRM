/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Consulta;
import Model.Medico;
import Persistence.Conexao;
import Persistence.DaoConsulta;
import Persistence.DaoEspecialidades;
import Persistence.DaoMed;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MedicoBean implements Serializable{

    private String[] selecionados;
    private List<String> especialidades;
    private Medico med = new Medico();
    private DaoMed daom;
    private DaoEspecialidades daoespec;
    private List<String> lista_de_medicos;
    private String espec_selecionada;
    private String nome_medico;
    private DaoConsulta daoc;
    private List<Consulta> consultas;
    private Consulta consultaselecionada;


        
    //Método para, assim que este Bean for iniciado, gerar uma lista com toads as especialidades
    @PostConstruct
    public void init() {
        especialidades = new ArrayList<String>();
        especialidades.add("Ortodontia");
        especialidades.add("Odontopediatria");
        especialidades.add("Endodontia");
        especialidades.add("Periodontia");
        especialidades.add("Cirurgia");
        especialidades.add("Traumatologia bucomaxilofacial");
        especialidades.add("Dentística");
        especialidades.add("Saúde coletiva");
        especialidades.add("Odontologia legal");
        especialidades.add("Radiologia Odontológica");
    }
    
    
    public Consulta getConsultaselecionada() {
        return consultaselecionada;
    }

    public void setConsultaselecionada(Consulta consultaselecionada) {
        this.consultaselecionada = consultaselecionada;
    }

    
    public String getNome_medico() {
        return nome_medico;
    }

    public void setNome_medico(String nome_medico) {
        this.nome_medico = nome_medico;
    }
    
    public String[] getSelecionados() {
        return selecionados;
    }

    public void setSelecionados(String[] selecionados) {
        this.selecionados = selecionados;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public Medico getMed() {
        return med;
    }

    public void setMed(Medico med) {
        this.med = med;
    }

    public List<String> getLista_de_medicos() {
        return lista_de_medicos;
    }

    public void setLista_de_medicos(ArrayList<String> lista_de_medicos) {
        this.lista_de_medicos = lista_de_medicos;
    }

    public String getEspec_selecionada() {
        return espec_selecionada;
    }

    public void setEspec_selecionada(String espec_selecionada) {
        this.espec_selecionada = espec_selecionada;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    //-------------------------------------- Métodos de ação --------------------------------------------
    
    //Método para cadastrar um médico novo, dando início à suas características, cadastrando suas especialidades, e criando sua agenda, nula.
    public String cadastrar() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Conexao con = new Conexao();
        this.daom = new DaoMed(con);
        this.daom.save(this.med);
        
        this.daoespec = new DaoEspecialidades(con);
        this.daoespec.save(this.med, selecionados);
        
        this.daoc = new DaoConsulta(con);
        //for(int i =1; i< 12; i++){
            for(int j =1; j<30;j++){
                for(int k = 8; k <18; k++){
                    int cont = 0;
                    String data = new String();
                    //data = "2014-"+i+"-"+j;
                    data = "2014-05-"+j;
                    
                    while(cont <2){
                        
                        if(cont == 0){//seta o horário "00"
                            String hora = new String();
                            hora = k+":00";
                            this.daoc.cadastrarConsultaMedico(data, hora, this.med.getNome());
                            cont++;
                        }
                        else{//seta o horário "30"
                            String hora = new String();
                            hora = k+":30";
                             this.daoc.cadastrarConsultaMedico(data, hora, this.med.getNome());
                            cont++;
                        }
                        
                    }
                }
            }
        //}
            
        med = new Medico();
        return "InicialAdministrador";

    }
    
    
    //Método para buscar um médico, à partir de sua CRM
    public String procurar() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Conexao con = new Conexao();
        this.daom = new DaoMed(con);
        med = this.daom.search(this.med.getCrm());
        return "FichaMedico";
    }
    
    //Método para obter os médicos que correspondem à uma especialidade selecionada
    public String listarMedico() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.daoespec = new DaoEspecialidades(con);
        setLista_de_medicos(this.daoespec.list(espec_selecionada));
        return "ListaDeMedicos";
    }

    //Método para que se faça edições num médico escolhido
    public String update() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Conexao con = new Conexao();
        this.daom = new DaoMed(con);
        this.daom.update(this.med);
        return "InicialAdministrador";
    }

    //Método para que se obtenha toda a agenda de um determinado médico
     public String listar() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Conexao con = new Conexao();
        this.daoc = new DaoConsulta(con);
        consultas = this.daoc.listar(this.getNome_medico());
        return "ListarEscalaMedico";
    }
     
    
}
