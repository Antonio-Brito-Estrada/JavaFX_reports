package mx.com.lobos.reporteaccesos.hibernatesial;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import mx.com.lobos.reporteaccesos.entities.Accesos;
import mx.com.lobos.reporteaccesos.entities.Empleados;
import mx.com.lobos.reporteaccesos.entities.Resultados;

/**
 *
 * @author salvadora
 */
public class HibernateUtility {

    private static SessionFactory factory;

    private HibernateUtility() {
    }

    public static synchronized SessionFactory getSessionFactory() {

        if (factory == null) {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            entityAnnotator(configuration);
            factory = configuration.buildSessionFactory();
        }
        return factory;
    }

    private static void entityAnnotator(Configuration configuration) {
        configuration.addAnnotatedClass(Accesos.class);
        configuration.addAnnotatedClass(Empleados.class);
        configuration.addAnnotatedClass(Resultados.class);
    }
}
