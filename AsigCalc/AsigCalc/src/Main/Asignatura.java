package Main;

import GUI.PanelAsig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Asignatura implements Serializable {
    private String Usuario;
    private String nombre;
    private Curso curso;
    private Estado estado;
    private double NotaFinal; // Nota final de la asgnatura
    private double NotaMedia; // Nota media de todas las asignaturas
    private int ECTS;
    private int ECTS_Total;
    public List<Prueba> pruebas;
    public static List<Asignatura> asignaturas = new ArrayList<>();
    
    public Asignatura(String nombre, Curso curso, int ECTS, String usuario) {
        this.Usuario = usuario;
        this.nombre = nombre;
        this.curso = curso;
        this.estado = null;
        this.ECTS = ECTS;
        this.pruebas = new ArrayList<>();
        asignaturas.add(this);
    }
    
    public void calcularEstado() {
        double sumaNotas = 0;
        int cantidadAprobadas = 0;

        for (Prueba prueba : pruebas) {
            if (prueba.getEstado() == Estado.APROBADA) {
                sumaNotas += prueba.getNotaPonderada();
                cantidadAprobadas++;
            }
        }

        if (cantidadAprobadas == pruebas.size() && getNotaFinal() >= 5) {
            this.estado = Estado.APROBADA;
        } else {
            this.estado = Estado.SUSPENSA;
        }
    }

    public void addExamen(Prueba examen){
        pruebas.add(examen);
    }
    
    public void notaFinal () {
        NotaFinal = 0.00;
        for (Prueba prueba : pruebas) {
            NotaFinal+= prueba.getNotaPonderada();
        }
    }
    
    public void notaMedia() {
        double sumaNotas = 0.0;
        int numAprobadas = 0;

        for (Asignatura asig : asignaturas) {
            if (asig.getEstado().equals(Estado.APROBADA)) {
                sumaNotas += asig.NotaFinal;
                numAprobadas++;
            }
        }
        if (numAprobadas > 0) {
            NotaMedia = sumaNotas / numAprobadas;
        } else {
            NotaMedia = 0.00; 
        }
    }
    
    public void creditos() {
        ECTS_Total = 0;
        for (Asignatura asig : asignaturas) {
            if (asig.getEstado().equals(Estado.APROBADA)) {
                ECTS_Total += asig.ECTS;
            }
        }
    }

    public int getCreditos (){
        return ECTS_Total;
    }
    
    public double getNotaMedia (){
        return NotaMedia;
    }
    
    public double getNotaFinal (){
        return NotaFinal;
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

    public static List<Asignatura> getAsignaturas() {
        return asignaturas;
    }
    
    public String getUsuario () {
        return Usuario;
    }
}
