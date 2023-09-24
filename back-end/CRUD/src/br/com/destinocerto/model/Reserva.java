package br.com.destinocerto.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import br.com.destinocerto.dao.ClienteDAO;
import br.com.destinocerto.dao.PassagemDAO;
import br.com.destinocerto.dao.ReservaDAO;

public class Reserva {

	private int id_reserva;
	private String origem;
	private String destino;
	private Date data_partida;
	private Date data_chegada;
	private int id_cliente;
	
	public Reserva() {

	}

	public Reserva(String origem, String destino, Date data_partida, Date data_chegada, int id_cliente) {
		this.origem = origem;
		this.destino = destino;
		this.data_partida = data_partida;
		this.data_chegada = data_chegada;
		this.id_cliente = id_cliente;
	}

	public int getId_reserva() {
		return id_reserva;
	}

	public void setId_reserva(int id_reserva) {
		this.id_reserva = id_reserva;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getData_partida() {
		return data_partida;
	}

	public void setData_partida(Date data_partida) {
		this.data_partida = data_partida;
	}

	public Date getData_chegada() {
		return data_chegada;
	}

	public void setData_chegada(Date data_chegada) {
		this.data_chegada = data_chegada;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	
	public static void criarReserva(Scanner scanner, ReservaDAO reservaDAO, ClienteDAO clienteDAO, int id_cliente) {
		
		if (!clienteDAO.clienteExists(id_cliente)) {
	        System.out.println("ID do cliente não existe. Por favor, cadastre o cliente antes de criar a reserva.");
	        return;
	    }
		
		System.out.println("=============================");
    	System.out.println("========\u001B[32mCRIAR RESERVA\u001B[0m========");
    	System.out.println("=============================");

	    System.out.println("Digite a origem da reserva:");
	    String origem = scanner.nextLine().trim();

	    System.out.println("Digite o destino da reserva:");
	    String destino = scanner.nextLine();

	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	    try {
	        System.out.println("Digite a data de partida (dd/MM/yyyy):");
	        String dataPartidaStr = scanner.nextLine();
	        Date dataPartida = dateFormat.parse(dataPartidaStr);

	        System.out.println("Digite a data de chegada (dd/MM/yyyy):");
	        String dataChegadaStr = scanner.nextLine();
	        Date dataChegada = dateFormat.parse(dataChegadaStr);

	        Reserva reserva = new Reserva(origem, destino, dataPartida, dataChegada, id_cliente);
	        
	        reservaDAO.save(reserva);

	        System.out.println("Reserva criada com sucesso!");
	    } catch (ParseException e) {
	        System.out.println("Erro ao analisar a data. Certifique-se de usar o formato dd/MM/yyyy.");
	    }
	}

		
	public static void cancelarReserva(Scanner scanner, ReservaDAO reservaDAO, PassagemDAO passagemDAO) {
	    System.out.println("Digite o ID da reserva que deseja cancelar:");
	    int idParaExcluir = scanner.nextInt();
	    scanner.nextLine();

	    if (reservaDAO.reservaExists(idParaExcluir)) {

	        passagemDAO.deletePassagensByReserva(idParaExcluir);
	        
	        reservaDAO.deleteById(idParaExcluir);
	        
	        System.out.println("------------------------------");
	        System.out.println("Reserva cancelada com sucesso!");
	        System.out.println("------------------------------");
	    } else {
	        System.out.println("----------------------------------");
	        System.out.println("ID da reserva não existe no banco.");
	        System.out.println("----------------------------------");
	    }
	}


	public static void listarReserva(Scanner scanner, ReservaDAO reservaDAO) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    System.out.println("--- LISTA DE RESERVAS ---");

	    for (Reserva r : reservaDAO.getReservas()) {
	        System.out.println("ID Reserva: " + r.getId_reserva());
	        System.out.println("Origem: " + r.getOrigem());
	        System.out.println("Destino: " + r.getDestino());
	        System.out.println("Data de Partida: " + dateFormat.format(r.getData_partida()));
	        System.out.println("Data de Chegada: " + dateFormat.format(r.getData_chegada()));
	        System.out.println("ID da Cliente: " + r.getId_cliente());
	        System.out.println("----------------------------------");
	    }
	    System.out.println("-------------------------");
	}

}