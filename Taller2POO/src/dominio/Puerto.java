package dominio;

public class Puerto {
	
	private int numero;
	private boolean state;
	private String vulnerabilidad = "Ninguna";
	private String descripcionVul = "N/A";
	
	
	public Puerto(int numero, String vulnerabilidad, String descripcionVul) {	//Para los de vulne
		this.numero = numero;
		this.vulnerabilidad = vulnerabilidad;
		this.descripcionVul = descripcionVul;
	}
	
	public Puerto(int numero, boolean state)		//Sin vulne (no existen en archivo vulne.txt)
	{
		this.numero=numero;
		this.state=state;
	}

	public Puerto(int numero, boolean state, String vulnerabilidad, String descripcionVul) {	//Constructor de puertos
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
	
	public void setState(boolean state) {
		this.state = state;
	}


	public String getVulnerabilidad() {
		return vulnerabilidad;
	}

	public String getDescripcionVul() {
		return descripcionVul;
	}

	@Override
	public String toString() {
	    String estado;
	    if (state) {
	        estado = "Abierto";
	    } else {
	        estado = "Cerrado";
	    }
	    
	    // Construccion string :3333
	    String resultado = "ID Puerto: " + numero + "\n" +
	                      "Estado: " + estado + "\n" +
	                      "Vulnerabilidad: " + vulnerabilidad + "\n" +
	                      "Descripción: " + descripcionVul + "\n" +
	                      "-".repeat(100);
	    
	    return resultado;
	}
	
	
	
}
