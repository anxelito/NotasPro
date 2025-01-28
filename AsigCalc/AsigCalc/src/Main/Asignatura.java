package Main;

import java.util.ArrayList;
import java.util.List;



public class Asignatura {
    private String nombre;
    private Curso curso;
    private Estado estado;
    private int ECTS;
    private List<Prueba> pruebas;
    public static List<Asignatura> asignaturas = new ArrayList<>();
    


    public Asignatura(String nombre, Curso curso, int ECTS) {
        this.nombre = nombre;
        this.curso = curso;
        this.estado = null;
        this.ECTS = ECTS;
        this.pruebas = new ArrayList<>();
        asignaturas.add(this);
    }
    
    public void calcularEstado() {
        double sumaNotas = 0;
        boolean todasAprobadas = true;

        for (Prueba prueba : pruebas) {
            if (prueba.getEstado() != Estado.APROVADA) {
                todasAprobadas = false; 
                break;
            }
            sumaNotas += prueba.getNotaPonderada();  
        }

        if (todasAprobadas && sumaNotas / pruebas.size() >= 5) {
            this.estado = Estado.APROVADA;
        } else {
            this.estado = Estado.SUSPENSA;
        }
    }


    
    public void addExamen(Prueba examen){
        pruebas.add(examen);
    }
    
    public List<Prueba> getPruebas() {
        return pruebas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getECTS() {
        return ECTS;
    }

    public void setECTS(int ECTS) {
        this.ECTS = ECTS;
    }
    
    @Override
    public String toString() {
        return nombre;  
    }
    
}
