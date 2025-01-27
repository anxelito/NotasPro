package Main;


import java.util.ArrayList;
import java.util.List;



public class Examen {
    private String nombre;
    private int ponderacion;
    private int notaMin;
    private double nota;
    private Estado estado;
    public static List<Examen> examenes = new ArrayList<>();

    public Examen(String nombre, int ponderacion, int notaMin, double nota, Estado estado) {
        this.nombre = nombre;
        this.ponderacion = ponderacion;
        this.notaMin = notaMin;
        this.nota = nota;
        this.estado = estado;
        examenes.add(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(int ponderacion) {
        this.ponderacion = ponderacion;
    }

    public int getNotaMin() {
        return notaMin;
    }

    public void setNotaMin(int notaMin) {
        this.notaMin = notaMin;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public static List<Examen> getExamenes() {
        return examenes;
    }

    public static void setExamenes(List<Examen> examenes) {
        Examen.examenes = examenes;
    }

}
