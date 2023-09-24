package br.com.destinocerto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.destinocerto.factory.ConnectionFactory;
import br.com.destinocerto.model.Contato;

public class ContatoDAO {

    public void save(Contato contato) {
		String sql = "INSERT INTO contato(nome, email, mensagem) VALUES(?,?,?)";

		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = conn.prepareStatement(sql);			
			pstm.setString(1, contato.getNome());			
			pstm.setString(2, contato.getEmail());			
			pstm.setString(3, contato.getMensagem());		
			
			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(pstm != null) {
					pstm.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}


    public List<Contato> getContatos(){
		String sql = "SELECT * FROM contato";
		
		List<Contato> contatos = new ArrayList<Contato>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		ResultSet rset = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = conn.prepareStatement(sql);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				Contato contato = new Contato();
				
				contato.setId_contato(rset.getInt("id_contato"));								
				contato.setNome(rset.getString("nome"));				
				contato.setEmail(rset.getString("email"));				
				contato.setMensagem(rset.getString("mensagem"));
				
				contatos.add(contato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				
				if(pstm != null) {
					pstm.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return contatos;
	}
     
    
}

