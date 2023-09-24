package br.com.destinocerto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.destinocerto.factory.ConnectionFactory;
import br.com.destinocerto.model.Passagem;
import br.com.destinocerto.model.Reserva;

public class PassagemDAO {

	public void save(Passagem passagem) {
	    String sql = "INSERT INTO passagem(preco, id_reserva, data_emissao, assento) VALUES (?, ?, ?, ?)";

	    Connection conn = null;
	    PreparedStatement pstm = null;

	    try {
	        conn = ConnectionFactory.createConnectionToMySQL();
	        pstm = conn.prepareStatement(sql);

	        pstm.setDouble(1, passagem.getPreco());
	        pstm.setInt(2, passagem.getId_reserva());
	        pstm.setDate(3, new java.sql.Date(passagem.getData_emissao().getTime()));
	        pstm.setInt(4, passagem.getAssento());


	        pstm.execute();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstm != null) {
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

	
	public void update(Passagem passagem) {
		String sql = "UPDATE reserva SET preco = ?, id_reserva = ?, data_emissao = ?, assento"
				+ " WHERE id_passagem = ?";

		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = ConnectionFactory.createConnectionToMySQL();

			pstm = conn.prepareStatement(sql);

			pstm.setDouble(1, passagem.getPreco());
			pstm.setInt(2, passagem.getId_reserva());
			pstm.setDate(3, (java.sql.Date) new Date(passagem.getData_emissao().getTime()));
			pstm.setInt(4, passagem.getAssento());

			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) {
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
		String sql = "DELETE FROM passagem WHERE id = ?";

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
				if (pstm != null) {
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


	public List<Passagem> getPassagensByCliente(int id_cliente) {
		String sql = "SELECT * FROM passagem WHERE id_reserva IN (SELECT id_reserva FROM reserva WHERE id_cliente = ?)";
		List<Passagem> passagens = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id_cliente);
			rset = pstm.executeQuery();

			while (rset.next()) {
				Passagem passagem = new Passagem();
				passagem.setId_passagem(rset.getInt("id_passagem"));
				passagem.setPreco(rset.getDouble("preco"));
				passagem.setId_reserva(rset.getInt("id_reserva"));
				passagem.setData_emissao(rset.getDate("data_emissao"));
				passagem.setAssento(rset.getInt("assento"));
				passagens.add(passagem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return passagens;
	}


	public List<Passagem> listarTodasPassagens() {
	    String sql = "SELECT p.*, r.data_partida, r.data_chegada, c.nome AS nome_cliente " +
	                 "FROM passagem p " +
	                 "INNER JOIN reserva r ON p.id_reserva = r.id_reserva " +
	                 "INNER JOIN cliente c ON r.id_cliente = c.id_cliente";
	    
	    List<Passagem> passagens = new ArrayList<>();
	    
	    Connection conn = null;
	    PreparedStatement pstm = null;
	    ResultSet rset = null;
	    
	    try {
	        conn = ConnectionFactory.createConnectionToMySQL();
	        pstm = conn.prepareStatement(sql);
	        rset = pstm.executeQuery();
	    
	        while (rset.next()) {
	            Passagem passagem = new Passagem();
	            passagem.setId_passagem(rset.getInt("id_passagem"));
	            passagem.setPreco(rset.getDouble("preco"));
	            passagem.setId_reserva(rset.getInt("id_reserva"));
	            passagem.setData_emissao(rset.getDate("data_emissao"));
	            passagem.setAssento(rset.getInt("assento"));

	            passagens.add(passagem);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rset != null) {
	                rset.close();
	            }
	            if (pstm != null) {
	                pstm.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return passagens;
	}

		
	public void deletePassagensByReserva(int idParaExcluir) {
		String sql = "DELETE FROM passagem WHERE id_reserva = ?";

		try (Connection conn = ConnectionFactory.createConnectionToMySQL();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setInt(1, idParaExcluir);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public Reserva getReservaByPassagem(int id_reserva) {
	    String sql = "SELECT * FROM reserva WHERE id_reserva = ?";
	    Reserva reserva = null;

	    Connection conn = null;
	    PreparedStatement pstm = null;
	    ResultSet rset = null;

	    try {
	        conn = ConnectionFactory.createConnectionToMySQL();
	        pstm = conn.prepareStatement(sql);
	        pstm.setInt(1, id_reserva);
	        rset = pstm.executeQuery();

	        if (rset.next()) {
	            reserva = new Reserva();
	            reserva.setId_reserva(rset.getInt("id_reserva"));
	            reserva.setOrigem(rset.getString("origem"));
	            reserva.setDestino(rset.getString("destino"));
	            reserva.setData_partida(rset.getDate("data_partida"));
	            reserva.setData_chegada(rset.getDate("data_chegada"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rset != null) {
	                rset.close();
	            }
	            if (pstm != null) {
	                pstm.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return reserva;
	}

		
}