package mx.com.lobos.reporteaccesos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "accesos")

public class Accesos {
    
    @Id
    @Column(name = "ID_ACCESO")
    private String idAcceso;

    @Column(name = "CVE_EMPLEADO")
    private String cveEmpleado;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PUESTO")
    private String puesto;

    @Column(name = "FECHA_HORA")
    private String fechaHora;

    @Column(name = "TIPO_MARCAJE")
    private String tipoMarcaje;
    
}
