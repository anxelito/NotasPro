package Main;


import java.util.ArrayList;
import java.util.List;



public class Prueba {
    private String nombre;
    private int ponderacion;
    private int notaMin;
    private double nota;
    private Estado estado;
    public static List<Prueba> examenes = new ArrayList<>();

    public Prueba(String nombre, int ponderacion, int notaMin, double nota) {
        this.nombre = nombre;
        this.ponderacion = ponderacion;
        this.notaMin = notaMin;
        this.nota = nota;
        if (nota>=notaMin) this.estado = Estado.APROVADA;
        else this.estado = Estado.SUSPENSA;
        examenes.add(this);
    }
    
    public double getNotaPonderada() {
        return (nota * ponderacion) / 100.0;
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

    public static List<Prueba> getExamenes() {
        return examenes;
    }

    public static void setExamenes(List<Prueba> examenes) {
        Prueba.examenes = examenes;
    }

}
