package dominio;

import java.util.ArrayList;

public class Computador {
	
	private String id;
	private String ip;
	private String sistema;
	private ArrayList<Puerto> puertos;
	private int risk;
	
	
	
	public Computador(String id, String ip, String sistema) {
		this.id = id;
		this.ip = ip;
		this.sistema = sistema;
		this.puertos = new ArrayList<>();
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

	public void setRisk(int risk) {
		this.risk = risk;
	}
	
	public String infoPorts()
	{
		return "Puertos asociados = " + puertos + " | Riesgo = "
				+ risk + "|";
	}

	@Override
	public String toString() {
		return id + ": IP = " + ip + " | Sistema = " + sistema;
	}

}
