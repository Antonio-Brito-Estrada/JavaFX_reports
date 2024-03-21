package mx.com.lobos.reporteaccesos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
//@Table(name = "empleados") FUNCIONADO
//public class Empleados {
//    @Id
//    @Column(name = "ID_EMPLEADO")
//    private String idEmpleado;
//
//    @Column(name = "CVE_EMPLEADO")
//    private String cveEmpleado;
//
//    @Column(name = "NOMBRE")
//    private String nombre;
//
//    @Column(name = "APELLIDOS")
//    private String apellidos;
//}
@Table(name = "Empleado")
public class Empleados {
    
    @Id
    @Column(name = "id")
    private String idEmpleado;

    @Column(name = "codigo")
    private String cveEmpleado;

    @Column(name = "nombres")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

}
