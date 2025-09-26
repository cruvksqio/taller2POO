package dominio;

import java.util.ArrayList;

public class Computador {
	
	private int id;
	private String ip;
	private String sistema;
	private ArrayList<Puerto> puertos;
	private int risk;
	
	
	public Computador(int id, String ip, String sistema, ArrayList<Puerto> puertos, int risk) {
		this.id = id;
		this.ip = ip;
		this.sistema = sistema;
		this.puertos = puertos;
		this.risk = risk;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getSistema() {
		return sistema;
	}


	public void setSistema(String sistema) {
		this.sistema = sistema;
	}


	public ArrayList<Puerto> getPuertos() {
		return puertos;
	}


	public void setPuertos(ArrayList<Puerto> puertos) {
		this.puertos = puertos;
	}


	public int getRisk() {
		return risk;
	}


	public void setRisk(int risk) {
		this.risk = risk;
	}

	
	
	
	

}
