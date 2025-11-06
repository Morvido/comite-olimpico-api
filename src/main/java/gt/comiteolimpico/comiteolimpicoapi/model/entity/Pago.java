package gt.comiteolimpico.comiteolimpicoapi.model.entity;

import jakarta.persistence.*;
import java.time.YearMonth;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atleta_id", nullable = false)
    private Atleta atleta;

    @Column(name = "mes", columnDefinition = "YEARMONTH")
    private YearMonth mes;

    private double monto;
    private String descripcion;

    public Pago() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Atleta getAtleta() { return atleta; }
    public void setAtleta(Atleta atleta) { this.atleta = atleta; }
    public YearMonth getMes() { return mes; }
    public void setMes(YearMonth mes) { this.mes = mes; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}