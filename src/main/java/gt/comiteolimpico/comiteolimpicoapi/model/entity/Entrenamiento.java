package gt.comiteolimpico.comiteolimpicoapi.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "entrenamientos")
public class Entrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atleta_id", nullable = false)
    private Atleta atleta;

    private LocalDate fecha = LocalDate.now();
    private String tipo;
    private double valor;
    private boolean internacional;
    private String pais;

    public Entrenamiento() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Atleta getAtleta() { return atleta; }
    public void setAtleta(Atleta atleta) { this.atleta = atleta; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public boolean isInternacional() { return internacional; }
    public void setInternacional(boolean internacional) { this.internacional = internacional; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
}