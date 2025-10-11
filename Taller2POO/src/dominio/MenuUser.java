package dominio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import logica.Main;
import java.util.ArrayList;
import java.util.Date;


public class MenuUser {

	public static void abrirMenuUser() throws FileNotFoundException{
		
		Scanner userS = new Scanner(System.in);
		int userOpt = - 1;

		do
		{
			
			System.out.println("============= Menú Usuario =============");
			System.out.println("1.- Ver lista de PCs");
			System.out.println("2.- Escanear un PC");
			System.out.println("3.- Ver total de puertos abiertos en todos los PCs de la red");
			System.out.println("4.-  Ordenar PCs según IP");
			System.out.println("0.- Salir");
			
			// Leer la opción del menú con nextInt()
	        if (userS.hasNextInt()) {
	            userOpt = userS.nextInt();  
	        } else {
	            System.out.println("Por favor ingrese un número válido.");
	            userS.next();  
	            continue;  // Volver a mostrar el menú
	        }
	        
	        
	        userS.nextLine();  
			
			switch (userOpt) {
			
			case 1 -> 
			{
				userPClist();
			}
			case 2 ->
			{
				scanPC();
			}
			case 3 ->
			{
				allOpenPorts();
			}
			case 4->
			{
				ipSort();
			}
			default ->
			{
				System.out.println("Error, ingrese una opcion correcta");
			}
			case 0 ->
			{
				System.out.println("Volviendo al login...");
			}
			
			}
			
		} while (userOpt!=0);
		
		
		userS.close();
	}

		
	

	private static void allOpenPorts() throws FileNotFoundException {
	    // Leer el archivo de puertos
	    File puertosFile = new File("puertos.txt");
	    Scanner puertoScanner = new Scanner(puertosFile);

	    // Leer el archivo de vulnerabilidades
	    File vulnerabilidadesFile = new File("vulnerabilidades.txt");
	    Scanner vulnerabilidadScanner = new Scanner(vulnerabilidadesFile);

	    System.out.println("======= Puertos abiertos en la red =======");

	    // Leer puertos
	    while (puertoScanner.hasNextLine()) {
	        String linea = puertoScanner.nextLine();
	        String[] partes = linea.split("\\|");
	        String pcId = partes[0];  
	        int portId = Integer.parseInt(partes[1]);  
	        String estado = partes[2];  

	        // Solo mostrar puertos abiertos
	        if (estado.equalsIgnoreCase("Abierto")) {
	            // Buscar el pc con el id
	            Computador pcSeleccionado = null;
	            for (Computador pc : Main.PCs) {
	                if (pc.getId().equals(pcId)) {
	                    pcSeleccionado = pc;
	                    break;
	                }
	            }

	            if (pcSeleccionado != null) {
	                // Leer vulnerabilidad del archivo vulnerabilidades
	                String vulnerabilidad = "Desconocida"; // Placeholder

	                // Volver al inicio de vulnerabilidades.txt para buscar la vulnerabilidad
	                vulnerabilidadScanner = new Scanner(new File("vulnerabilidades.txt")); // Reiniciamos el scanner

	                // Buscar la vulnerabilidad para el puerto
	                while (vulnerabilidadScanner.hasNextLine()) {
	                    String[] vulnerabilidadPartes = vulnerabilidadScanner.nextLine().split("\\|");
	                    int puertoId = Integer.parseInt(vulnerabilidadPartes[0]);  // ID del puerto en vulnerabilidades.txt
	                    if (puertoId == portId) {
	                        vulnerabilidad = vulnerabilidadPartes[1];  // Asignamos la vulnerabilidad
	                        break;
	                    }
	                }

	                // Printy
	                System.out.println("PC ID: " + pcId + " | Puerto: " + portId + " | Estado: " + estado + " | Vulnerabilidad: " + vulnerabilidad);
	            }
	        }
	    }

	    // Cerrar los scanners
	    puertoScanner.close();
	    vulnerabilidadScanner.close();

	    System.out.println("===========================================");
	    System.out.println(" ");
	}




	private static void scanPC() {
		Scanner pcsc = new Scanner(System.in);
	    int idToScan = -67;
	    
	    do {
	        System.out.println("======= Escanear un PC =======");
	        System.out.println("");
	        System.out.println("> Indique ID del equipo (por ejemplo, 1 para PC001):");

	        // Intentar leer el número de ID ingresado
	        if (pcsc.hasNextInt()) {
	            idToScan = pcsc.nextInt();
	            
	            // Buscar el PC con el ID
	            Computador pcSeleccionado = null;
	            String idToSearch = "PC" + String.format("%03d", idToScan); // Formatear a "PC001", "PC002", etc.
	            
	            for (Computador pc : Main.PCs) {
	                if (pc.getId().equals(idToSearch)) {
	                    pcSeleccionado = pc;
	                    break;
	                }
	            }
	            
	            if (pcSeleccionado != null) {
	                // Mostrar los puertos asociados
	                System.out.println("PC seleccionado: " + pcSeleccionado.getId() + " | IP: " + pcSeleccionado.getIp());
	                ArrayList<Puerto> puertos = pcSeleccionado.getPuertos();
	                System.out.println("Puertos asociados:");
	                for (Puerto puerto : puertos) {
	                    System.out.println("Puerto: " + puerto.getNumero());
	                }

	                // Funcion para guardar el reporte
	                guardarReporte(pcSeleccionado, puertos);
	                System.out.println("Escaneo enviado a log de reportes !");
	                System.out.println("");
	                break;  // Salir del ciclo después de escanear
	            } else {
	                System.out.println("¡ID de PC no encontrado! Intente de nuevo.");
	            }
	        } else {
	            System.out.println("Por favor ingrese un número válido.");
	            pcsc.next();  // Limpiar el buffer para que no entre una entrada no válida
	        }
	    } while (idToScan <= 0 || idToScan > Main.PCs.size());

	    //pcsc.close();   Dejo esto acá como castigo xq cerré System.in sin darme cuenta y estuve 1 hora entera como loco soy gil
	}
		
		
	private static void guardarReporte(Computador pc, ArrayList<Puerto> puertos) {
	    
	    int nivelRiesgo = pc.getRisk();

	    // Obtener el nombre del usuario que realizó el escaneo
	    String usuario = Main.username;  // Acceder al username desde el main

	    // Obtener la fecha de escaneo
	    String fechaEscaneo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	    // \n
	    try (FileWriter writer = new FileWriter("reportes.txt", true)) {
	        writer.write("======= Escaneo de " + pc.getId() + " =======\n");
	        writer.write("PC: " + pc.getId() + " | IP: " + pc.getIp() + " | SO: " + pc.getSistema() + "\n");
	        writer.write("Puertos asociados:\n");
	        for (Puerto puerto : puertos) {
	            writer.write("  Puerto: " + puerto.getNumero() + "\n");
	        }
	        writer.write("Nivel de riesgo: " + nivelRiesgo + "\n");
	        writer.write("Usuario que realizó el escaneo: " + usuario + "\n");
	        writer.write("Fecha de escaneo: " + fechaEscaneo + "\n");
	        writer.write("\n---------------------\n\n");
	    } catch (IOException e) {
	        System.out.println("Error al guardar el reporte: " + e.getMessage());
	    }
	}
	
	// Le list print
	private static void userPClist() {
        try {
            File archivo = new File("pcs.txt");
            Scanner sc = new Scanner(archivo);

            System.out.println("======= Lista de PCs =======");

            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] datos = linea.split("\\|");
                String id = datos[0];
                String ip = datos[1];
                String os = datos[2];

                System.out.println("ID: " + id + " | IP: " + ip + " | SO: " + os);
            }

            sc.close();
            System.out.println("============================");

        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
	
	private static void ipSort() {
	    // Boble sortss
	    boolean ordenado;
	    do {
	        ordenado = true;
	        for (int i = 0; i < Main.PCs.size() - 1; i++) {
	            // Obtener la clase de IP de los PCs i y i+1
	            String claseIP1 = Computador.getGroupIP(Main.PCs.get(i).getIp());
	            String claseIP2 = Computador.getGroupIP(Main.PCs.get(i + 1).getIp());

	            // Comparar las clases de IP
	            if (comparaClases(claseIP1, claseIP2) > 0) {
	                // Intercambiar los pcs si no están en orden numeriko
	                Computador temp = Main.PCs.get(i);
	                Main.PCs.set(i, Main.PCs.get(i + 1));
	                Main.PCs.set(i + 1, temp);
	                ordenado = false;
	            }
	        }
	    } while (!ordenado);

	    // Imprimir los pcs ordenaditos
	    System.out.println("======= PCs ordenados según la clase de su IP =======");
	    for (Computador pc : Main.PCs) {
	        String claseIP = Computador.getGroupIP(pc.getIp());
	        System.out.println("PC ID: " + pc.getId() + " | IP: " + pc.getIp() + " | Clase: " + claseIP);
	    }
	}

	// IP comparation in the nation
	private static int comparaClases(String claseIP1, String claseIP2) {
	    if (claseIP1.equals("A") && !claseIP2.equals("A")) {
	        return -1;  
	    } else if (claseIP1.equals("B") && claseIP2.equals("C")) {
	        return -1;  
	    } else if (claseIP1.equals("C") && !claseIP2.equals("C")) {
	        return 1;  
	    } else {
	        return 0;  // Las clases son iguales
	    }
	}

}
