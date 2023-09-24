package br.com.destinocerto.model;

import java.util.Scanner;

import br.com.destinocerto.dao.ClienteDAO;

public class Cliente {
	private int id_cliente;
	private String nome; 
	private String email;
	private String senha;
	private String telefone;
	
	public Cliente() {

    }
	
	public Cliente(String nome, String email, String senha, String telefone) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
	}
	
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	
	public static void cadastrarCliente(Scanner scanner, ClienteDAO clienteDAO) {
	    System.out.println(" -----------------------------");
	    System.out.println("|        \u001B[32mCadastre-se\u001B[0m         |");
	    System.out.println(" -----------------------------");

	    System.out.println("Digite o nome do cliente:");
	    String nome = scanner.nextLine().trim();

	    System.out.println("Digite o email do cliente:");
	    String email = scanner.nextLine();

	    System.out.println("Digite a senha do cliente:");
	    String senha = scanner.nextLine();

	    System.out.println("Digite o telefone do cliente:");
	    String telefone = scanner.nextLine();

	    Cliente cliente = new Cliente(nome, email, senha, telefone);

	    if (clienteDAO.save(cliente)) {

	        System.out.println("--- CLIENTE INSERIDO ---");
	        System.out.println("NOME: " + nome);
	        System.out.println("EMAIL: " + email);
	        System.out.println("SENHA: " + senha);
	        System.out.println("TELEFONE: " + telefone);
	        System.out.println("-------------------------");
	    } else {
	        System.out.println("Cadastro de cliente falhou.");
	    }
	}

	
	public static void excluirCliente(Scanner scanner, ClienteDAO clienteDAO) {
        
        System.out.println("Digite o ID do cliente que deseja excluir:");
        int idParaExcluir = scanner.nextInt();
        scanner.nextLine();

       
        if (clienteDAO.clienteExists(idParaExcluir)) {

            clienteDAO.deleteById(idParaExcluir);

        } else {
        	System.out.println("------------------------------");
            System.out.println("ID não existe no banco.");
            System.out.println("------------------------------");       	
        }  
}
		
	
    public static void listarCliente(Scanner scanner, ClienteDAO clienteDAO) {
    	
    	System.out.println(" -----------------------------");
        System.out.println("| \u001B[33mLista de Todas os Clientes\u001B[0m |");
        System.out.println(" -----------------------------");
    	
    	for(Cliente c : clienteDAO.getClientes()) {
			System.out.println("ID: " + c.getId_cliente() + " | NOME: " + c.getNome());
		}
    	System.out.println("-------------------------");
    }
	
    
    public static Cliente fazerLogin(Scanner scanner, ClienteDAO clienteDAO) {    	
    	
    	System.out.println("==========================");
    	System.out.println("========\u001B[32mFAÇA LOGIN\u001B[0m========");
    	System.out.println("==========================");
    	
        System.out.println("Digite o email do cliente:");
        String email = scanner.nextLine().trim();

        System.out.println("Digite a senha do cliente:");
        String senha = scanner.nextLine();
       
        Cliente cliente = clienteDAO.login(email, senha);

        if (cliente != null) {
        	System.out.println("===================================");
            System.out.println("Bem-vindo(a) ao Destino Certo, " + cliente.getNome() + "!");
            System.out.println("===================================");
            return cliente;
        } else {
            System.out.println("--------------------------");
            System.out.println("Email ou senha incorretos.");
            System.out.println("--------------------------");
            return null;
        }              
    }
    

}