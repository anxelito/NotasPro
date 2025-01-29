package GUI;

import Main.Asignatura;
import java.io.*;
import java.util.List;

public class CargarDatos {

    public static List<Asignatura> cargarAsignaturas() {
        List<Asignatura> asignaturasCargadas = null;
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("asignaturas.dat"))) {
            asignaturasCargadas = (List<Asignatura>) in.readObject();
            System.out.println("Asignaturas cargadas correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error al cargar las asignaturas.");
        }
        
        return asignaturasCargadas;
    }
}
