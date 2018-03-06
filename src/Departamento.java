
public class Departamento {
	private int codigoDepartamento;
	private String nombre;

	public Departamento(int codigo,String nombre) {
		this.codigoDepartamento= codigo;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

	public int getCodigoDepartamento() {
		return codigoDepartamento;
	}



	@Override
	public String toString() {
		return "Departamento [codigoDepartamento=" + codigoDepartamento + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigoDepartamento;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (codigoDepartamento != other.codigoDepartamento)
			return false;
		return true;
	}
	
	
	
}
