package logica;
/*
Constantino Bekios 21761616-6
Luis Molina 21564225-9
*/
import java.io.*;
import java.util.*;
import dominio.*;

public class Main {
	
	static ArrayList<Computador> PCs = new ArrayList<>();
	static ArrayList<Puerto> Puertos = new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println("Hello chicken");
		
		cargarDatos();
		
		MenuAdmin.abrirMenuAdmin();
		
		

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
