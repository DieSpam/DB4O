import java.util.LinkedList;

public class Alumno {
	private String DNI;
	private String nombre;
	private String apellidos;
	private LinkedList<Asignatura>  asignaturas;
	
	
	public Alumno(String dNI, String nombre, String apellidos) {
		super();
		DNI = dNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		asignaturas=new LinkedList<Asignatura>();
	}
	
	
	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LinkedList<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(LinkedList<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public void addAsignatura(Asignatura asignatura) {
		this.asignaturas.add(asignatura);
	}

	@Override
	public String toString() {
		String cadena= "Alumno [DNI=" + DNI + ", nombre=" + nombre + ", apellidos=" + apellidos ;
		
		
		
		return cadena;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DNI == null) ? 0 : DNI.hashCode());
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
		Alumno other = (Alumno) obj;
		if (DNI == null) {
			if (other.DNI != null)
				return false;
		} else if (!DNI.equals(other.DNI))
			return false;
		return true;
	}
	
}
