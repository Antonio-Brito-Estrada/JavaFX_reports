package mx.com.lobos.reporteaccesos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "resultados")
@XmlRootElement
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Resultados {
  
//    @Id
//    @Column(name = "ID_RESULTADO")
//    private String idResultados;
    @Id
    @Column(name = "CVE_EMPLEADO", nullable = false)
    private String cveEmpleado;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "PUESTO", nullable = false)
    private String puesto;

    @Column(name = "FECHA", nullable = false)
    private String fecha;

    @Column(name = "HORA_ENTRADA")
    private String horaEntrada;

    @Column(name = "HORA_SALIDA")
    private String horaSalida;

    @Column(name = "HORAS_MINUTOS")
    private String horaMinutos;

    @Column(name = "TIEMPO_EFECTIVO")
    private Long tiempoEfectivo;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

}
