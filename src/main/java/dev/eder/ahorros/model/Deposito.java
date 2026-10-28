package dev.eder.ahorros.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "depositos")
@Getter
@Setter 
@NoArgsConstructor
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ahorrista_id", nullable = false)
    private Ahorrista ahorrista;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal capital;

    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;

    @Column(name = "tasa_mensual", precision = 5, scale = 2)
    private BigDecimal tasaMensual;

    @Column(name = "monto_final", precision = 12, scale = 2)
    private BigDecimal montoFinal;

    @Column(name = "interes_ganado", precision = 12, scale = 2)
    private BigDecimal interesGanado;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    void alCrear(){
        this.fechaRegistro=LocalDateTime.now();
    }
}
