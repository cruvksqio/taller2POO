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

	public boolean isState() {
		return state;
	}

	public String getVulnerabilidad() {
		return vulnerabilidad;
	}

	public String getDescripcionVul() {
		return descripcionVul;
	}
		
}
