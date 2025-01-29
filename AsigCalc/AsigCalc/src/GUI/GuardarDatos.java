package GUI;

import Main.Asignatura;
import java.io.*;
import java.util.List;

public class GuardarDatos {

    public static void guardarAsignaturas(List<Asignatura> asignaturas) {
        if (asignaturas == null || asignaturas.isEmpty()) {
            System.out.println("La lista de asignaturas está vacía o es null.");
            return;
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("asignaturas.dat"))) {
            System.out.println("Guardando asignaturas...");
            for (Asignatura asig : asignaturas) {
                System.out.println("Guardando: " + asig.getNombre());
            }
            out.writeObject(asignaturas);
            System.out.println("Asignaturas guardadas correctamente.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar las asignaturas.");
        }
    }
}
