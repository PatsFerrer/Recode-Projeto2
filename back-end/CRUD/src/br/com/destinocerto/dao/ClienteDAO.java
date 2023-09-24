package br.com.destinocerto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.destinocerto.factory.ConnectionFactory;
import br.com.destinocerto.model.Cliente;

public class ClienteDAO {

	public boolean save(Cliente cliente) {
	    String sql = "INSERT INTO cliente(nome, email, senha, telefone) VALUES (?, ?, ?, ?)";

	    Connection conn = null;
	    PreparedStatement pstm = null;

	    try {
	        conn = ConnectionFactory.createConnectionToMySQL();

	        if (emailExists(cliente.getEmail())) {
	            System.out.println("Erro: O email já está em uso por outro cliente.");
	            return false;
	        }

	        pstm = conn.prepareStatement(sql);

	        pstm.setString(1, cliente.getNome());
	        pstm.setString(2, cliente.getEmail());
	        pstm.setString(3, cliente.getSenha());
	        pstm.setString(4, cliente.getTelefone());

	        pstm.execute();

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; 
	    } finally {
	    	try {
				if (pstm != null) {
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

	
	public void update(Cliente cliente) {
		
		String sql = "UPDATE cliente SET nome = ?, email = ?, senha = ?, telefone = ?" + " WHERE id_cliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getEmail());
			pstm.setString(3, cliente.getSenha());
			pstm.setString(4, cliente.getTelefone());						
			pstm.setInt(5, cliente.getId_cliente());
			
			pstm.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) {
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
	
	
	public void deleteById(int id) {

	    if (hasReservasAtivas(id)) {
	        System.out.println("Este cliente possui reservas ativas e não pode ser excluído.");
	    } else {
	        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
	        Connection conn = null;
	        PreparedStatement pstm = null;

	        try {
	            conn = ConnectionFactory.createConnectionToMySQL();
	            pstm = conn.prepareStatement(sql);
	            pstm.setInt(1, id);
	            pstm.execute();
	            System.out.println("-------------------------------");
	            System.out.println(" Cliente excluído com sucesso! ");
	            System.out.println("-------------------------------");
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
	}

	
	public List<Cliente> getClientes() {

		String sql = "SELECT * FROM cliente";

		List<Cliente> clientes = new ArrayList<Cliente>();

		Connection conn = null;
		PreparedStatement pstm = null;

		ResultSet rset = null;

		try {
			conn = ConnectionFactory.createConnectionToMySQL();

			pstm = conn.prepareStatement(sql);

			rset = pstm.executeQuery();

			while (rset.next()) {
				Cliente cliente = new Cliente(sql, sql, sql, sql);


				cliente.setId_cliente(rset.getInt("id_cliente"));
				cliente.setNome(rset.getString("nome"));			
				cliente.setEmail(rset.getString("email"));	
				cliente.setSenha(rset.getString("senha"));
				cliente.setTelefone(rset.getString("telefone"));

				clientes.add(cliente);

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

		return clientes;

	}
		
	
	public boolean isClienteInserido(Cliente cliente) {
	    String sql = "SELECT COUNT(*) FROM cliente WHERE id_cliente = ?";

	    Connection conn = null;
	    PreparedStatement pstm = null;
	    ResultSet resultSet = null;

	    try {
	        conn = ConnectionFactory.createConnectionToMySQL();
	        pstm = conn.prepareStatement(sql);

	        pstm.setInt(1, cliente.getId_cliente());

	        resultSet = pstm.executeQuery();

	        if (resultSet.next() && resultSet.getInt(1) > 0) {
	            return true;
	        } else {
	            return false;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (resultSet != null) {
	                resultSet.close();
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
	}
	
	
	public boolean clienteExists(int id) {
	    String sql = "SELECT COUNT(*) FROM cliente WHERE id_cliente = ?";
	    
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

	
	public Cliente login(String email, String senha) {
	    String sql = "SELECT * FROM cliente WHERE email = ? AND senha = ?";
	    Cliente cliente = null;
	    
	    try (
	        Connection conn = ConnectionFactory.createConnectionToMySQL();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    ) {
	        pstmt.setString(1, email);
	        pstmt.setString(2, senha);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                cliente = new Cliente();
	                cliente.setId_cliente(rs.getInt("id_cliente"));
	                cliente.setNome(rs.getString("nome"));
	                cliente.setEmail(rs.getString("email"));
	                cliente.setSenha(rs.getString("senha"));
	                cliente.setTelefone(rs.getString("telefone"));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return cliente;
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


	public boolean emailExists(String email) {
	    String sql = "SELECT COUNT(*) FROM cliente WHERE email = ?";
	    
	    try (
	        Connection conn = ConnectionFactory.createConnectionToMySQL();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    ) {
	        pstmt.setString(1, email);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return false;
	}

	
	public Cliente getClienteByEmail(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cliente cliente = null;

        try {
            conn = ConnectionFactory.createConnectionToMySQL();
            String sql = "SELECT * FROM cliente WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	
                cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setTelefone(rs.getString("telefone"));                
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

        return cliente;
    }

	
}
