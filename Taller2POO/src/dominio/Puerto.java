package dominio;

public class Puerto {
	
	private int numero;
	private boolean state;
	private String vulnerabilidad;
	private String descripcionVul;
	
	
	public Puerto(int numero, boolean state, String vulnerabilidad, String descripcionVul) {
		this.numero = numero;
		this.state = state;
		this.vulnerabilidad = vulnerabilidad;
		this.descripcionVul = descripcionVul;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}


	public String getVulnerabilidad() {
		return vulnerabilidad;
	}


	public void setVulnerabilidad(String vulnerabilidad) {
		this.vulnerabilidad = vulnerabilidad;
	}


	public String getDescripcionVul() {
		return descripcionVul;
	}


	public void setDescripcionVul(String descripcionVul) {
		this.descripcionVul = descripcionVul;
	}
	
	
	

}
