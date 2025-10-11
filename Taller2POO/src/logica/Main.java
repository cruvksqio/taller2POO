package logica;
/*
Constantino Bekios 21761616-6
Luis Molina 21564225-9
*/

import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import dominio.*;

public class Main {
	
	
	public static ArrayList<Computador> PCs = new ArrayList<>();
	public static ArrayList<Puerto> Puertos = new ArrayList<>();
	public static User UsuarioLogged;
	public static String username;
	
	public static void main(String[] args) throws FileNotFoundException {
	
		cargarDatos();
		
		Scanner loginscan = new Scanner(System.in);
		
		 String username;
		 String passcode;
		 
		 ArrayList<User> usuarios = cargarUsuarios("usuarios.txt");
		 
		System.out.println("--- Bienvenido ---");
		System.out.println("");
		System.out.println("Ingrese nombre de usuario :");
		
		username = loginscan.nextLine();
		
		System.out.println("Ingrese contraseña :");
		
		passcode = loginscan.nextLine();
		
		String role = validarLogin(usuarios, username, passcode);
		
		if (role.equals("ADMIN")) {
				System.out.println("Login exitoso. Rol: " + role);
				MenuAdmin.abrirMenuAdmin();
		}
		if (role.equals("USER")) {
				System.out.println("Login exitoso. Rol: " + role);
				MenuUser.abrirMenuUser();
		}
		
		if (role.equals(null)) {
			System.out.println("Usuario o contraseña incorrectos");
		}
		loginscan.close();
		

	}
		
	public static ArrayList<User> cargarUsuarios(String archivo) throws FileNotFoundException {
		ArrayList<User> listaUsuarios = new ArrayList<>();
		File file = new File(archivo);
		Scanner readit = new Scanner(file);
		
		while(readit.hasNextLine()) {
			String [] p = readit.nextLine().split(";");
			String user = p[0];
			String hashedpword = p[1];
			String role = p[2];
			
			
			User u = new User(user, hashedpword, role);
			listaUsuarios.add(u);
			
		}
		
		for (User i : listaUsuarios) {
			System.out.println(i);
	}
		readit.close();
		return listaUsuarios;
		
		
	}
	
	public static String validarLogin(ArrayList<User> usuarios, String username, String passcode) {
		for (User u : usuarios) {
			if (u.getUsername().equals(username)) {
				String hashedInput = sha256Base64(passcode);
				if (hashedInput != null && hashedInput.equals(u.getPassword())) {
					Main.username = u.getUsername();
					return u.getRole();
				}
				
			}
		}
		return null; // el usuario no es valido
	}
	
	private static String sha256Base64(String passcode) {
		// TODO Auto-generated method stub
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(passcode.getBytes());
			
			return Base64.getEncoder().encodeToString(digest);
		} catch (Exception e) {
			
			return null;
		}
		
	}
	
	public static void cargarDatos() throws FileNotFoundException
	{
		File compus = new File("pcs.txt");
		
		if (!compus.exists()) {				//Control de error si archivo no existe
			throw new FileNotFoundException("ERROR: Archivo no fue encontrado");
			}
		
		Scanner pcscan = new Scanner(compus);
		
		while (pcscan.hasNextLine())		//Creacion de PCs y agregar a lista
		{
			String[] pcParts = pcscan.nextLine().split("\\|");
			String IDadd = pcParts[0];
			String IPadd = pcParts[1];
			String OSadd = pcParts[2];
			
			// EXTRA: PONER GRUPO DE IP :33333333 ()
						
			PCs.add(new Computador(IDadd, IPadd, OSadd, Computador.getGroupIP(IPadd)));
		}
		
		//Cargar puertos con info
		
		File vulne = new File("vulnerabilidades.txt");
		Scanner scVulne = new Scanner(vulne);
		
		while (scVulne.hasNextLine())
		{
			String[] vulneParts = scVulne.nextLine().split("\\|");
			int IDport = Integer.parseInt(vulneParts[0]);
			String vuln = vulneParts[1];
			String desc = vulneParts[2];
			Puertos.add(new Puerto(IDport,vuln,desc));
		}
		
		//ASOCIAR PUERTOS A PC ojala no falle  O.o
			
		File ports = new File("puertos.txt");
		Scanner scanports = new Scanner(ports);
		
		
		while (scanports.hasNextLine())
		{
			String[] portParts = scanports.nextLine().split("\\|");
			String currentPC = portParts[0];
			int portID = Integer.parseInt(portParts[1]);
			boolean IDstate = portParts[2].equals("Abierto");
			
			for (Computador pc : PCs)
			{
				if (pc.getId().equals(currentPC))
				{
					boolean foundPort = false;
					for (Puerto pr : Puertos)
					{
						if (pr.getNumero() == portID)
						{
					        Puerto newPort = new Puerto(pr.getNumero(), pr.getVulnerabilidad(), pr.getDescripcionVul());
					        newPort.setState(IDstate);
					        pc.getPuertos().add(newPort);
					        foundPort = true;
							
							if (IDstate)
							{
								pc.riskUpgrade();
							}
							break;
						}
					}
					
					if (!foundPort)
					{
						Puerto portInfoless = new Puerto(portID, IDstate);
						pc.getPuertos().add(portInfoless);
						
						if (IDstate)
						{
							pc.riskUpgrade();
						}
					}
					break;
				}
			}
		}
		
		
		//estoy goated viejo
		
		pcscan.close();	
		scVulne.close();
		scanports.close();
	}
	

}
