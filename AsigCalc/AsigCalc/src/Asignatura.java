

public class Asignatura {
    String nombre;
    Curso curso;
    Estado estado;
    int ECTS;

    public Asignatura(String nombre, Curso curso, Estado estado, int ECTS) {
        this.nombre = nombre;
        this.curso = curso;
        this.estado = estado;
        this.ECTS = ECTS;
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
