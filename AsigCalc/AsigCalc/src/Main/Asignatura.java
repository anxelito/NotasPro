package Main;

import java.util.ArrayList;
import java.util.List;



public class Asignatura {
    private String nombre;
    private Curso curso;
    private Estado estado;
    private int ECTS;
    public static List<Asignatura> asignaturas = new ArrayList<>();


    public Asignatura(String nombre, Curso curso, int ECTS) {
        this.nombre = nombre;
        this.curso = curso;
        this.estado = null;
        this.ECTS = ECTS;
        asignaturas.add(this);
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
    
}
