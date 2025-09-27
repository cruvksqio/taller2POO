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

	public static void main(String[] args) {

		System.out.println("Hello chicken");
		
		MenuAdmin.abrirMenuAdmin();
		
		

	}
	
	public static void cargarDatos() throws FileNotFoundException
	{
		File compus = new File("pcs.txt");
		
		if (!compus.exists()) {
			throw new FileNotFoundException("ERROR: Archivo no fue encontrado");
			}
		
		
		Scanner pcscan = new Scanner(compus);
		
		String[] pcParts = pcscan.nextLine().split("|");
		int x=0;
		
		while (pcscan.hasNextLine())
		{
			x++;
			int IDadd = x;
			String IPadd = pcParts[1];
			String OSadd = pcParts[2];
			PCs.add(new Computador(IDadd, IPadd, OSadd, null, 0));
			pcParts = pcscan.nextLine().split("|");
		}
		
		pcscan.close();
	}
	

}
