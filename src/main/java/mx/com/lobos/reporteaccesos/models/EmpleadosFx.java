package mx.com.lobos.reporteaccesos.models;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.com.lobos.reporteaccesos.empleados.empleadosservice.empleadosService;
import mx.com.lobos.reporteaccesos.entities.Empleados;
import mx.com.lobos.reporteaccesos.hibernatesial.HibernateUtility;

public class EmpleadosFx {
    
    private final StringProperty cveEmpleado = new SimpleStringProperty(this, "cveEmpleado", "");

    private final StringProperty nombre = new SimpleStringProperty(this, "nombre", "");

    private final StringProperty apellidos = new SimpleStringProperty(this, "apellidos", "");

    public String getCveEmpleado() {
        return cveEmpleado.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getApellidos() {
        return apellidos.get();
    }

    public String toString() {
        return cveEmpleado.get() + " - " + nombre.get() + " " + apellidos.get();
    }

    public static ObservableList<EmpleadosFx> getList(){
        ObservableList<EmpleadosFx> res = new SimpleListProperty<>(FXCollections.observableArrayList());
        try {
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            Session session = sessionFactory.openSession();

            empleadosService emp =  new empleadosService();
            emp.actualizaEmpleados();

            Query query = session.createQuery("SELECT e from Empleados e order by cast(e.cveEmpleado as integer)");

            List<Empleados> list = query.getResultList();

            for (Empleados item : list) {
                EmpleadosFx v = new EmpleadosFx();
                v.cveEmpleado.set(item.getCveEmpleado());
                v.nombre.set(item.getNombre());
                v.apellidos.set(item.getApellidos());
                res.add(v);
            }

        } catch (Exception ex) {
            Logger.getLogger(EmpleadosFx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
