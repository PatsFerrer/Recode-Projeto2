package br.com.destinocerto.aplicacao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import br.com.destinocerto.dao.ClienteDAO;
import br.com.destinocerto.dao.ContatoDAO;
import br.com.destinocerto.dao.ReservaDAO;
import br.com.destinocerto.model.Cliente;
import br.com.destinocerto.model.Contato;
import br.com.destinocerto.model.Passagem;
import br.com.destinocerto.model.Reserva;
import br.com.destinocerto.utils.TerminalUtils;
import br.com.destinocerto.dao.PassagemDAO;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ClienteDAO clienteDAO = new ClienteDAO();
		ReservaDAO reservaDAO = new ReservaDAO();
		PassagemDAO passagemDAO = new PassagemDAO();
		ContatoDAO contatoDAO = new ContatoDAO(); 
		Cliente cliente = null; 

		
		while (true) {
			System.out.println("==================================");
			System.out.println("  \u001B[36mBem-vindo(a) ao Destino Certo!\u001B[0m");
			System.out.println("==================================");

			System.out.println("\u001B[36mEscolha uma opção:\u001B[0m");
			System.out.println("1 - \u001B[32mCadastrar cliente\u001B[0m");
			System.out.println("2 - \u001B[32mCriar Reserva\u001B[0m");
			System.out.println("3 - \u001B[32mGerar Passagem\u001B[0m ");
			System.out.println("4 - \u001B[32mFazer Login\u001B[0m");
			
			System.out.println("5 - \u001B[33mListar clientes\u001B[0m");
			System.out.println("6 - \u001B[33mListar Reservas\u001B[0m");
			System.out.println("7 - \u001B[33mListar todas as Passagens\u001B[0m");

			System.out.println("8 - \u001B[31mExcluir cliente\u001B[0m");
			System.out.println("9 - \u001B[31mCancelar Reserva\u001B[0m");
			System.out.println("10 - Verificar passagem (Cliente)");
			System.out.println("11 - \u001B[34mEntrar em Contato\u001B[0m");


			System.out.println("12 - \u001B[35mSair\u001B[0m");
			System.out.println("------------------------------");
			
			int opcao = scanner.nextInt();
			
			scanner.nextLine();
			TerminalUtils.clearScreen(); 

			switch (opcao) {
			case 1:
				Cliente.cadastrarCliente(scanner, clienteDAO);
				break;

			case 2:
				if (cliente != null) {
					System.out.println("Digite o ID do cliente para criar a reserva:");
					int id_cliente = scanner.nextInt();
					scanner.nextLine();
					Reserva.criarReserva(scanner, reservaDAO, clienteDAO, id_cliente);
				} else {
					System.out.println("Faça login antes de criar uma reserva.");
				}
				break;
			

			case 3:
			    if (cliente != null) {
			        System.out.println("Digite o ID da reserva para criar uma passagem:");
			        int id_reserva = scanner.nextInt();
			        scanner.nextLine();
			        Passagem.gerarPassagem(scanner, passagemDAO, reservaDAO, id_reserva);
			    } else {
			        System.out.println("Faça login antes de criar uma passagem.");
			    }
			    break;

			case 4:
				cliente = Cliente.fazerLogin(scanner, clienteDAO);
				break;

			case 5:
				Cliente.listarCliente(scanner, clienteDAO);
				break;
				
			case 6:			   
			    List<Reserva> todasReservas = reservaDAO.getReservas();
			    if (todasReservas.isEmpty()) {
			        System.out.println("Não há reservas cadastradas.");
			    } else {
			        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			        System.out.println(" -----------------------------");
			        System.out.println("| \u001B[33mLista de Todas as reservas\u001B[0m |");
			        System.out.println(" -----------------------------");
			        for (Reserva r : todasReservas) {
			            System.out.println("ID da Reserva: " + r.getId_reserva());
			            System.out.println("Origem: " + r.getOrigem());
			            System.out.println("Destino: " + r.getDestino());
			            System.out.println("Data da Partida: " + dateFormat.format(r.getData_partida()));
			            System.out.println("Data da Volta: " + dateFormat.format(r.getData_chegada()));
			            System.out.println("------------------------------");
			        }
			    }
			    break;


			case 7:
			    List<Passagem> todasPassagens = passagemDAO.listarTodasPassagens();
			    
			    if (todasPassagens.isEmpty()) {
			        System.out.println("Não há passagens cadastradas.");
			    } else {
			        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			        System.out.println(" -----------------------------");
			        System.out.println("| \u001B[33mLista de Todas as Passagens\u001B[0m |");
			        System.out.println(" -----------------------------");
			        for (Passagem passagem : todasPassagens) {
			            System.out.println("ID da Passagem: " + passagem.getId_passagem());
			            System.out.println("Preço: " + passagem.getPreco());
			            System.out.println("Assento: " + passagem.getAssento());
			            System.out.println("Data de Emissão: " + dateFormat.format(passagem.getData_emissao()));
			            System.out.println("ID da Reserva: " + passagem.getId_reserva());
			            System.out.println("------------------------------");
			        }
			    }
			    break;

			case 8:
				Cliente.excluirCliente(scanner, clienteDAO);
				break;

			case 9:
				Reserva.cancelarReserva(scanner, reservaDAO, passagemDAO);
				break;
			    
			case 10:   			    											
				if (cliente != null) {
			        Passagem.VerSuaPassagem(scanner, passagemDAO, cliente.getId_cliente());
			    } else {
			        System.out.println("Faça login antes de ver sua passagem.");
			    }
			    break;
			    
			case 11:
			    Contato.entrarEmContato(scanner, contatoDAO);
			    break;

			case 12:
				scanner.close();
				System.out.println("Você saiu!");
				System.exit(0);

			default:
				System.out.println("Opção inválida.");
			}
		}
	}
}
