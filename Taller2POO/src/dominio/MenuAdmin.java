package dominio;

import java.util.ArrayList;
import java.util.Scanner;
import logica.Main;

public class MenuAdmin {
	
	public static void abrirMenuAdmin()
	{
		Scanner adminS = new Scanner(System.in);
		int adminOpt = - 1;

		do
		{
			
			System.out.println("============= Menu ADMIN =============");
			System.out.println("1.- Ver lista completa de PCs y su info");
			System.out.println("2.- Administrar PCs");
			System.out.println("3.- Clasificar PCs por riesgo");
			System.out.println("0.- Salir");
			
			adminOpt = adminS.nextInt();
			
			switch (adminOpt) {
			
			case 1 -> 
			{
				verListaPCs();
			}
			case 2 ->
			{
				administrarPCs(adminS);
			}
			case 3 ->
			{
				verRiesgoPCs();
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
			
		} while (adminOpt!=0);
		
		
		adminS.close();
	}
	
	////////////////////////////////////////////
	
	public static void verListaPCs() {
		
		System.out.println("===== LISTA DE COMPUTADORES CARGADOS =====");
		
		for (Computador pc : Main.PCs)
		{
			System.out.printf("%s%n", "=".repeat(80));
			System.out.println(pc);
			System.out.printf("%s%n", "=".repeat(80));
			System.out.println(pc.extraInfo());
		}
		
	}
	
	//////////////////////////////////////////////
	
	public static void verRiesgoPCs() {
		
		System.out.println("~-".repeat(20));
		
		for (Computador pc : Main.PCs)
		{
			String risk;
			if (pc.getRisk()<1) {
				risk = "Bajo Riesgo";
			} else if (pc.getRisk()<3) {
				risk = "Medio Riesgo";
			} else {
				risk = "Alto Riesgo";
			}
			
			System.out.println(pc.getId() + "| Riesgo: " + pc.getRisk() + " " + risk);
			System.out.println("~-".repeat(20));
			
			for (Puerto pr : pc.getPuertos())
			{
				if (pr.isState())
				{
					System.out.println(pr.getVulnerabilidad());
				}
			}
			System.out.println("~-".repeat(20));
			
		}
		
		
	}
	
	////////////////////////////////////////////////
	
	static int PCsizeNew = Main.PCs.size();

	public static void administrarPCs(Scanner adminS)
	{
	    int adminOpt;
	    
	    do {
	        adminOpt = -1;
	        System.out.println("===== PC ADMINISTRATOR MENU DX ======");
	        System.out.println("1.- Crear nuevo PC");
	        System.out.println("2.- Borrar PC existente");
	        System.out.println("0.- Volver al menu principal");
	        
	        adminOpt=adminS.nextInt();
	        adminS.nextLine(); // CONSUMIR SALTO DE LÍNEA
	        
	        switch (adminOpt) {
	        case 1 ->
	        {
	            PCsizeNew++;
	            System.out.println("==== Crear un PC ====");
	            System.out.println("PC Num : " + (PCsizeNew));
	            System.out.printf("=".repeat(15) + "%n");
	            PCsizeNew--;
	            
	            System.out.println("Ingrese la IP correspondiente");
	            String inputIP = adminS.nextLine();
	            
	         try {
	                verificarIP(inputIP);
	                
	            } catch (IllegalArgumentException e) {
	                System.out.println(e.getMessage());
	                System.out.println("Por favor, ingrese una IP válida.");
	                continue;
	            }
	            
	            
	            String IPgrupoAdd = Computador.getGroupIP(inputIP);
	            
	            //ermm yeahh ummrmm
	            
	            System.out.println("Ingrese el sistema operativo:");
	            String inputOS = adminS.nextLine();
	            
	            //Puerto thinggg
	            
	            ArrayList<Puerto> portsToAdd = new ArrayList<>();
	            ArrayList<Puerto> prtDispo = new ArrayList<>(Main.Puertos); 
	            int riskAdd=0;
	            boolean stateAdd = false;
	            
	            String inputPort = "";
	            
	            do {
	                System.out.println("=== Puertos disponibles con info ===");
	                for (Puerto p : prtDispo) {
	                    System.out.print(p.getNumero() + ",");
	                }
	                
	                System.out.println(" Ingresa el puerto a asociar al PC (N para dejar de agregar)");
	                
	                inputPort = adminS.nextLine();
	                
	                // Verificar si input esta vacio
	                if (inputPort.trim().isEmpty()) {
	                    System.out.println("--!-- Entrada vacía, por favor ingresa un número o 'N' --!--");
	                    continue;
	                }
	                
	                // Verificar si el usuario quiere salir
	                if (inputPort.equalsIgnoreCase("N")) {
	                    break;
	                }
	                
	                try {
	                    int intPort = Integer.parseInt(inputPort);
	                    
	                    // Buscar puerto sin modificar lista durante iteración
	                    Puerto puertoEncontrado = null;
	                    for (Puerto pr : prtDispo) {
	                        if (pr.getNumero() == intPort) {
	                            puertoEncontrado = pr;
	                            break;
	                        }
	                    }
	                    
	                    if (puertoEncontrado != null) {
	                        System.out.println("--!-- Puerto valido --!--");
	                        System.out.println("Ingrese el estado del puerto ");
	                        System.out.println("1.- Abierto");
	                        System.out.println("2.- Cerrado");
	                        int statePort = adminS.nextInt();
	                        adminS.nextLine();
	                        
	                        if (statePort == 1) {
	                            stateAdd = true;
	                            riskAdd++;
	                        } else if (statePort == 2) {
	                            stateAdd = false;
	                        }
	                        
	                        puertoEncontrado.setState(stateAdd);
	                        portsToAdd.add(puertoEncontrado);
	                        prtDispo.remove(puertoEncontrado); // Remover después de iterar
	                        
	                    } else {
	                        System.out.println("--!-- Puerto no encontrado --!--");
	                        System.out.println("¿Deseas crear un nuevo puerto?");
	                        System.out.println("1.- Si");
	                        System.out.println("2.- No");
	                        int ugottaPick = adminS.nextInt();
	                        adminS.nextLine();
	                        if (ugottaPick == 1) {
	                            
	                            crearPuerto(intPort, adminS, stateAdd, riskAdd, portsToAdd);

	                        } else if (ugottaPick == 2) {
	                            // No hacer return, solo continuar
	                            continue;
	                        }
	                    }
	                } catch (NumberFormatException e) {
	                    System.out.println("--!-- Entrada inválida. Por favor ingresa un número o 'N' --!--");
	                }
	                
	            } while (!inputPort.equalsIgnoreCase("N"));
	            
	            // Build PC PORFIIIIINNN
	            
	            System.out.println("=".repeat(35));
	            
	            // PC ID builder
	            
	            String addPCID = "";
	            PCsizeNew++; 
	            
	            if (PCsizeNew < 9) {
	                addPCID = "PC00" + PCsizeNew;
	            } else if (PCsizeNew < 99) {
	                addPCID = "PC0" + PCsizeNew;
	            } else if (PCsizeNew < 999) {
	                addPCID = "PC" + PCsizeNew;
	            }
	            
	            System.out.println("== " + addPCID);
	            System.out.println("== IP: " + inputIP);
	            System.out.println("== OS: " + inputOS);
	            System.out.println("== Ports: ");
	            for (Puerto pr : portsToAdd)
	            {
	                System.out.print(pr.getNumero() + " | ");
	            }
	            System.out.println();
	            System.out.println("=".repeat(35));
	            
	            System.out.println("¿Agregar PC?");
	            System.out.println("1.- SI");
	            System.out.println("2.- NO");
	            
	            int finalDestination = adminS.nextInt();
	            adminS.nextLine(); // CONSUMIR SALTO DE LÍNEA
	            if (finalDestination == 1) {
	                Main.PCs.add(new Computador(addPCID, inputIP, inputOS, portsToAdd, riskAdd, IPgrupoAdd));
	                System.out.println("PC agregado exitosamente");
	            } else {
	                System.out.println("Bueno chiken...");
	                PCsizeNew--; // DECREMENTAR SI NO SE AGREGA
	            }
	        }
	        
	        
	        case 2 ->
	        {
	            System.out.println("*".repeat(30));
	            int x=0;
	            for (Computador pc : Main.PCs)
	            {
	                x++;
	                System.out.println(x + ". " + pc);
	            }
	            System.out.println("Elija PC a eliminar");
	            
	            int deletePC = adminS.nextInt()-1;
	            adminS.nextLine(); // CONSUMIR SALTO DE LÍNEA
	            
	            if (deletePC < 0 || deletePC >= Main.PCs.size()) {
	                System.out.println("ERROR: PC no válido");
	                break;
	            }
	            
	            System.out.println("¿Deseas eliminar " + Main.PCs.get(deletePC).getId() + "?");
	            
	            System.out.println("1.- Si");
	            System.out.println("2.- No");
	            int ugottaPick = adminS.nextInt();
	            adminS.nextLine(); 
	            if (ugottaPick==1) {
	                Main.PCs.remove(deletePC);
	                System.out.println("PC removido exitosamente");
	            } else {
	                //No hacer return, solo continuar
	            }    
	            
	        }
	        case 0 ->
	        {
	            System.out.println("Volviendo al menu principal...");
	        }
	        default ->
	        {
	            System.out.println("ERROR: Ingrese una opcion valida");
	        }
	        }
	        
	    } while (adminOpt!=0);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	public static String verificarIP(String inputIP) {
		
		String[] partsIPver = inputIP.split("\\.");
		
		if (partsIPver.length != 4) {
		    throw new IllegalArgumentException("ERROR: IP debe tener 4 partes");
		}
		
	    for (int i=0; i<4; i++) {
	    	
	    	if (partsIPver[i].length() > 3) {
	    		throw new IllegalArgumentException("ERROR: IP no cumple formato (0.0.0.0 | menor a 3)");
	    	}
	    }
	    
	    return inputIP;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
		 
	public static void crearPuerto(int intPort, Scanner adminS, boolean stateAdd, int riskAdd, ArrayList<Puerto> portsToAdd) {
		
		System.out.println("Puerto ID = " + intPort);
		
		System.out.println("Ingrese el estado del puerto ");
		System.out.println("1.- Abierto");
		System.out.println("2.- Cerrado");
		int statePort = adminS.nextInt();
		adminS.nextLine();
		
		if (statePort == 1) {
			stateAdd = true;
			riskAdd++;
		} else if (statePort == 2) {
			stateAdd = false;
		}
		
		System.out.println("Ingresa nombre vulnerabilidad:");
		String vulnAdd = adminS.nextLine();
		
		System.out.println("Ingresa descripcion vulnerabilidad:");
		String descAdd = adminS.nextLine();
		
		
		System.out.println("=".repeat(20));
		System.out.println("Puerto ID = " + intPort);
		String stateRes = "";
		if (stateAdd) {
			stateRes = "Abierto";
		} else 
			stateRes = "Cerrado";
		System.out.println("Estado = " + stateRes);
		System.out.println("Vuln = " + vulnAdd);
		System.out.println("Desc = " + descAdd);
		System.out.println("=".repeat(20));
		System.out.println("¿Desea agregar el puerto?");
		System.out.println("1.- Si");
		System.out.println("2.- No");
		int ugottaPickAgain = adminS.nextInt();
		adminS.nextLine();
		
		if (ugottaPickAgain == 1) {
			portsToAdd.add(new Puerto(intPort, stateAdd, vulnAdd, descAdd));
			
		} else if (ugottaPickAgain == 2) {
			return;
		}
		
	}

}
