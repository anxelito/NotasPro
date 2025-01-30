package Main;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Prueba implements Serializable{
    private String nombre;
    private int ponderacion;
    private double notaMin;
    private double nota;
    private Estado estado;
    public static List<Prueba> examenes = new ArrayList<>();

    public Prueba(String nombre, int ponderacion, double notaMin, double nota) {
        this.nombre = nombre;
        this.ponderacion = ponderacion;
        this.notaMin = notaMin;
        this.nota = nota;
        if (nota>=notaMin) this.estado = Estado.APROBADA;
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

    public double getNotaMin() {
        return notaMin;
    }

    public void setNotaMin(double notaMin) {
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

    public void setEstado() {
        if (nota>=notaMin) this.estado = Estado.APROBADA;
        else this.estado = Estado.SUSPENSA;
    }

    public static List<Prueba> getExamenes() {
        return examenes;
    }

    public static void setExamenes(List<Prueba> examenes) {
        Prueba.examenes = examenes;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
