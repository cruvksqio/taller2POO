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
	
	
	static ArrayList<Computador> PCs = new ArrayList<>();
	static ArrayList<Puerto> Puertos = new ArrayList<>();
	
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
			PCs.add(new Computador(IDadd, IPadd, OSadd));
		}
		
		for (Computador pc : PCs)
		{
			System.out.println(pc);
		}
		
		
		/*
		File ports = new File("puertos.txt");
		Scanner scanports = new Scanner(ports);
		
		while (scanports.hasNextLine())
		{
			String[] portParts = scanports.nextLine().split("\\|");
			String PCid = portParts[0];
			int IDport = Integer.parseInt(portParts[1]);
			boolean IDstate;
			if (portParts[2].equals("Abierto")) {
				IDstate = true;
			} else if (portParts[2].equals("Cerrado")) {
				IDstate = false;
			}
			
			
			
		}
		
		*/
		
		
		pcscan.close();		
	}
	

}
