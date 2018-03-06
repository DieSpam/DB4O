
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;

public class Principal {
	
	//cambio rama diego
	//nuevo comentario
	private static final String BD_INSTITUTO = "instituto.yap";
	private static final int OPCION_SALIR = 4;
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		int opc;


		//comentario master
		cargarDatosIniciales();

		do {
			opc = solicitarOpcion();
			tratarOpcion(opc);
		} while (opc != OPCION_SALIR);

	}

	private static void cargarDatosIniciales() {

		File f = new File(BD_INSTITUTO); // SI NO EXISTE EL FICHERO CON LA BD LO CREA
		if (!f.exists()) {
			ObjectContainer db = abrirBd();
			Departamento matematicas, informatica, lengua;

			db.store(new Alumno("111", "Luis", "Alvarez Fernandez"));
			db.store(new Alumno("222", "Luisa", "Fernandez Campos"));
			db.store(new Alumno("333", "Jose Maria", "Ortiz Fernandez"));
			db.store(new Alumno("444", "Maria Jose", "Alvarez Ortiz"));
			db.store(new Alumno("555", "Marta", "Jimenez Rosas"));
			db.store(new Alumno("666", "Sara", "Suarez Jimenez"));
			db.store(new Alumno("777", "Diego", "Arroyo Garcia"));

			matematicas = new Departamento(1, "Matematicas");
			informatica = new Departamento(2, "Informatica");
			lengua = new Departamento(3, "Lengua");

			db.store(new Asignatura("TIC 1", informatica, 1));
			db.store(new Asignatura("TIC 2", informatica, 2));
			db.store(new Asignatura("TIC 3", informatica, 3));
			db.store(new Asignatura("TIC 4", informatica, 4));
			db.store(new Asignatura("MATEMATICAS 1", matematicas, 1));
			db.store(new Asignatura("MATEMATICAS 2", matematicas, 2));
			db.store(new Asignatura("MATEMATICAS 3", matematicas, 3));
			db.store(new Asignatura("MATEMATICAS 4", matematicas, 4));
			db.store(new Asignatura("LENGUA 1", lengua, 1));
			db.store(new Asignatura("LENGUA 2", lengua, 2));
			db.store(new Asignatura("LITERATURA 3", lengua, 3));
			db.store(new Asignatura("LITERATURA 4", lengua, 4));

			cerrarBd(db);
		}

	}

	private static ObjectContainer abrirBd() {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		ObjectContainer db = Db4oEmbedded.openFile(config, BD_INSTITUTO);
		return db;
	}

	private static void cerrarBd(ObjectContainer db) {
		db.close();
	}

	private static void tratarOpcion(int opc) {

		switch (opc) {
		case 1:
			modificarDepartamentoDeAsignatura();
			break;
		case 2:
			matricularAlumnoEnAsignatura();
			break;

		case 3:
			consultaAlumnosMatriculados();
			break;
			
		

		}

	}


	private static void consultaAlumnosMatriculados() {
		ObjectContainer db = abrirBd();
		ObjectSet<Alumno> listaAlumnos = db.query(new Predicate<Alumno>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean match(Alumno alumno) {
				// TODO Auto-generated method stub
				return alumno.getAsignaturas().size()>0;
			}
		});
		
		for(Alumno alumno : listaAlumnos) {
			System.out.println(alumno);
		}
		
		cerrarBd(db);
	}

	private static void matricularAlumnoEnAsignatura() {
		ObjectContainer db = abrirBd();
		
		String dniAlumno = solicitarCadena("DNI del alumno que desea matricular");
		
		ObjectSet<Alumno> listaAlumnos = db.queryByExample(new Alumno(dniAlumno, null, null));
		
		if (listaAlumnos.size() == 0) {
			System.out.println("ERROR. El alumno que desea matricular no existe.");
		} else {
			Alumno alumno = listaAlumnos.next();
			String nombreAsignatura = solicitarCadena("Nombre de la asignatura en la que matriculará al alumno: ");
			
			ObjectSet<Asignatura> listaAsignaturas = db.queryByExample(new Asignatura(nombreAsignatura, null, 0));
			
			if (listaAsignaturas.size() == 0) {
				System.out.println("ERROR. La asignatura no es correcta.");
			} else {
				Asignatura asignaturaMatricular = listaAsignaturas.next();
				LinkedList<Asignatura> asignaturasAlumno = alumno.getAsignaturas();
				boolean matriculado = false;
				
				//Recorremos la lista de asignaturas en las que esta matriculado el alumno
				//Comprobamos que no esta matriculado en la asignatura indicada
				for(Asignatura asignatura : asignaturasAlumno) {
					if (asignaturaMatricular.equals(asignatura)) {
						matriculado = true;
						System.out.println("ERROR. El alumno ya esta matriculado en la asignatura");
					}
				}
				
				if (!matriculado) {
					alumno.addAsignatura(asignaturaMatricular);
					db.store(alumno);
					System.out.println("Alumno matriculado correctamente.");
				}					
			}
		}
		cerrarBd(db);
	}

	private static void modificarDepartamentoDeAsignatura() {
		ObjectContainer db = abrirBd();
		
		String nombreAsignatura = solicitarCadena("Asignatura que se desea modificar.");
		
		//Obtenemos una lista de las asignaturas que tienen el nombre introducido
		//En este caso solo debe haber una asignatura
		ObjectSet<Asignatura> listaAsignaturas = db.queryByExample(new Asignatura(nombreAsignatura, null, 0));
		
		if (listaAsignaturas.size() == 0) {
			System.out.println("ERROR. No existe la asignatura.");
		} else {
			//Como solo hay una asignatura con ese nombre al almacenarla en memoria 
			//solo tenemos que coger la primera de la lista
			Asignatura asignatura = listaAsignaturas.next();
			int nuevoCodDepartamento = solicitarEntero("Codigo del nuevo departamento: ");
			
			//Obtenemos la lista de departamentos.
			ObjectSet<Departamento> listaDepartamentos = db.queryByExample(new Departamento(nuevoCodDepartamento, null));
			
			if (listaDepartamentos.size() == 0) {
				System.out.println("ERROR. Departamento no valido.");
			} else {
				//Almacenamos el departamento en memoria, igual que la asignatura
				Departamento nuevoDepartamento = listaDepartamentos.next();
				
				if (asignatura.getDepartamento().equals(nuevoDepartamento)) {
					System.out.println("ERROR. El nuevo departamento es el mismo que el antiguo.");
				} else {
					asignatura.setDepartamento(nuevoDepartamento);
					db.store(asignatura);
					System.out.println("EXITO. Codigo de asignatura modificado correctamente.");
				}
			}
		}
		
		cerrarBd(db);
	}

	private static int solicitarOpcion() {
		int opc;
		System.out.println("1.Modificar el departamento de una asignatura");
		System.out.println("2.Matricular a un alumno en una asignatura");
		System.out.println("3.Consulta de alumnos matriculados en alguna asignatura");
		System.out.println("4.Salir");
		do {
			System.out.println("Introduce opcion");
			opc = Integer.parseInt(teclado.nextLine());
		} while (opc < 1 || opc > OPCION_SALIR);
		return opc;
	}

	

	private static int solicitarEntero(String msg) {
		int numero;
		
		System.out.println(msg);
		numero = Integer.parseInt(teclado.nextLine());
		
		return numero;
	}

	private static String solicitarCadena(String msg) {
		String nombre;
		System.out.println(msg);
		nombre = teclado.nextLine();
		return nombre;
	}

}
