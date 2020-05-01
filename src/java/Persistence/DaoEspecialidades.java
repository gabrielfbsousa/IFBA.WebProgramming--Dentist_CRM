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
import java.util.ArrayList;

/**
 *
 * @author gabrielferreira
 */
public class DaoEspecialidades {

    private Connection con;

    public DaoEspecialidades(Conexao con) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.con = con.fabricar();
    }

    //Método chamado no MedicoBean -> cadastrar(), para salvar as especialidades de um determinado médico
    public void save(Medico med, String[] selecionadas) throws SQLException {
        PreparedStatement pstm = null;
        for (int i = 0; i < selecionadas.length ; i++) {
            pstm = con.prepareStatement("insert into Especialidade(nome, nome_especialidade) values(?,?) ");
            pstm.setString(1, med.getNome());
            pstm.setString(2, selecionadas[i]);
            pstm.executeUpdate();
            pstm.close();
        }
    }
    
    //Método chamado no MédicoBean -> listarMedico(), para obtermos uma lista de médicos que têm uma determinada especialidade selecionada
    public ArrayList<String> list(String selecionados) throws SQLException{
        ArrayList<String> lista_de_medicos = new ArrayList<String>();
        PreparedStatement pstm = con.prepareStatement("select * from Especialidade where nome_especialidade = ?");
        pstm.setString(1, selecionados);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            lista_de_medicos.add(rs.getString("Nome"));
        }
        pstm.close();
        return lista_de_medicos;
        
    }

}
