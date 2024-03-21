package mx.com.lobos.reporteaccesos.empleados.empleadosservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;

import mx.com.lobos.reporteaccesos.hibernatesial.HibernateUtility;

public class empleadosService {
    public void actualizaEmpleados(){
        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
//PRUEBA CONECTAR A LA SERVIDOR
//        ProcedureCall call = session.createStoredProcedureCall("SP_ACTUALIZA_EMPLEADOS");
//
//        session.beginTransaction();
//        call.execute();
//        session.getTransaction().commit();
    }
}
