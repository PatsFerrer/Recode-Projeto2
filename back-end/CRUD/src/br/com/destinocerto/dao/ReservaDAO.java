package br.com.destinocerto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.destinocerto.factory.ConnectionFactory;
import br.com.destinocerto.model.Reserva;

public class ReservaDAO {
	
	public void save(Reserva reserva) {
		String sql = "INSERT INTO reserva(origem, destino, data_partida, data_chegada, id_cliente) VALUES (?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, reserva.getOrigem());		
			pstm.setString(2, reserva.getDestino());						
			pstm.setDate(3, new java.sql.Date(reserva.getData_partida().getTime()));			
	        pstm.setDate(4, new java.sql.Date(reserva.getData_chegada().getTime()));	        
	        pstm.setInt(5, reserva.getId_cliente());
			
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
	
		
	public void update(Reserva reserva) {
		String sql = "UPDATE reserva SET origem = ?, destino = ?, data_partida = ?, data_chegada" +" WHERE id_reserva = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, reserva.getOrigem());			
			pstm.setString(2, reserva.getDestino());			
			pstm.setDate(3, (java.sql.Date) new Date(reserva.getData_partida().getTime()));			
			pstm.setDate(4, (java.sql.Date) new Date(reserva.getData_chegada().getTime()));
					
			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstm != null) {
					pstm.close();
				}
				
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
		
	public void removeById(int id) {
		String sql = "DELETE FROM reserva WHERE id = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, id);
			
			pstm.execute();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
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
	}
	
		
	public List<Reserva> getReservas(){
		String sql = "SELECT * FROM reserva";
		
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		ResultSet rset = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = conn.prepareStatement(sql);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				Reserva reserva = new Reserva();
				
				reserva.setId_reserva(rset.getInt("id_reserva"));				
				reserva.setOrigem(rset.getString("origem"));				
				reserva.setDestino(rset.getString("destino"));				
				reserva.setData_partida(rset.getDate("data_partida"));				
				reserva.setData_chegada(rset.getDate("data_chegada"));
				
				
				reservas.add(reserva);
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
		return reservas;
	}
	

    public List<Reserva> getReservasByCliente(int id_cliente) {
        String sql = "SELECT * FROM reserva WHERE id_cliente = ?";
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.createConnectionToMySQL();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id_cliente);
            try (ResultSet rset = pstm.executeQuery()) {
                while (rset.next()) {
                    Reserva reserva = new Reserva();
                    reserva.setId_reserva(rset.getInt("id_reserva"));
                    reserva.setOrigem(rset.getString("origem"));
                    reserva.setDestino(rset.getString("destino"));
                    reserva.setData_partida(rset.getDate("data_partida"));
                    reserva.setData_chegada(rset.getDate("data_chegada"));

                    reservas.add(reserva);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservas;
    }

   
 	public boolean reservaExists(int id) {
 	    String sql = "SELECT COUNT(*) FROM reserva WHERE id_reserva = ?";
 	    
 	    Connection conn = null;
 	    PreparedStatement pstmt = null;
 	    ResultSet rs = null;

 	    try {
 	        conn = ConnectionFactory.createConnectionToMySQL();
 	        pstmt = conn.prepareStatement(sql);
 	        pstmt.setInt(1, id);
 	        rs = pstmt.executeQuery();

 	        if (rs.next()) {
 	            int count = rs.getInt(1);
 	            return count > 0;
 	        }
 	    } catch (Exception e) {
 	        e.printStackTrace();
 	    } finally {
 	        try {
 	            if (rs != null) {
 	                rs.close();
 	            }
 	            if (pstmt != null) {
 	                pstmt.close();
 	            }
 	            if (conn != null) {
 	                conn.close();
 	            }
 	        } catch (Exception e) {
 	            e.printStackTrace();
 	        }
 	    }
 	    
 	    return false;
 	}
 	
 	 	 	
public void deleteById(int id) {
		
		String sql = "DELETE FROM reserva WHERE id_reserva = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, id);
			
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


public boolean hasReservasAtivas(int idCliente) {
    String sql = "SELECT COUNT(*) FROM reserva WHERE id_cliente = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = ConnectionFactory.createConnectionToMySQL();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idCliente);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return false;
}

  
}