package dominio;

import java.util.ArrayList;

public class Computador {
	
	private String id;
	private String ip;
	private String sistema;
	private ArrayList<Puerto> puertos;
	private int risk = 0;
	private String IPgrupo;
	
	
	
	public Computador(String id, String ip, String sistema, String IPgrupo) {		//Cargar PCs
		this.id = id;
		this.ip = ip;
		this.sistema = sistema;
		this.puertos = new ArrayList<>();
		this.IPgrupo = IPgrupo;
	}
	
	public Computador(String id, String ip, String sistema, ArrayList<Puerto> puertos, int risk, String iPgrupo) {	//Creador PCs
		super();
		this.id = id;
		this.ip = ip;
		this.sistema = sistema;
		this.puertos = puertos;
		this.risk = risk;
		IPgrupo = iPgrupo;
	}


	public String getIPgrupo() {
		return IPgrupo;
	}

	public String getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getSistema() {
		return sistema;
	}

	public ArrayList<Puerto> getPuertos() {
		return puertos;
	}

	public int getRisk() {
		return risk;
	}

	public void riskUpgrade() {
		this.risk++;
	}
	
	public static String getGroupIP(String ip) {
		
		String[] IPparts = ip.split("\\.");
		int firstIP = Integer.parseInt(IPparts[0]);
		String claseIP = "";
		if (firstIP <= 127)
		{
			claseIP = "A";
		} else if (firstIP >= 128 && firstIP <= 191)
		{
			claseIP = "B";
		} else if (firstIP >= 192 && firstIP <= 223)
		{
			claseIP = "C";
		} else
		{
			claseIP = "Otra";
		}
		
		return claseIP;
	}
	
	public String extraInfo() {
	    String puertosStr = "";
	    for (Puerto p : puertos) {
	        puertosStr += p.toString() + "\n";
	    }
	    
	    return String.format("Puertos asociados:%n%n%sGrupo IP: %s | Riesgo: %d", 
	                        puertosStr, IPgrupo, risk);
	}
	


	@Override
	public String toString() {
		return id + ": IP = " + ip + " ".repeat(5) + "| Sistema = " + sistema;
	}

}
