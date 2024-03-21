package mx.com.lobos.reporteaccesos.accesos.service.accesosservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import mx.com.lobos.reporteaccesos.hibernatesial.HibernateUtility;
import mx.com.lobos.reporteaccesos.sialpoi.excel.SialReportExcelFactory;
import mx.com.lobos.reporteaccesos.sialpoi.excel.pojos.DataHeaderEnterprise;

// Implementacion del proyecto generico
//import mx.com.lobos.sialpoi.excel.pojos.DataHeaderEnterprise;
//import mx.com.lobos.sialpoi.excel.SialReportExcelFactory;
public class accesosService {

    List<Object[]> nombreArrayList = new ArrayList();
    List<Object[]> nuevaLista = new ArrayList();
    List<Object[]> listaTotalTiempoAcumulado = new ArrayList();
    List<Object[]> list, listConTotales, listaFinal = new ArrayList();
    String claveEmp = "", fecha = "", claveEmpLista = "", fechaLista = "";
    int contador = -2, contadorHoras = 0, tamanioLista = 0, registroConMayorLogitud = 0, posionFinalTotal = 0;
    boolean is = true;
    String[] mapping;

    public void procesoAccesos(HashMap<String, String> parametrosHsm) {
        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        String cveEmpleado = parametrosHsm.get("cveEmpleado"),
               fechaInicio = parametrosHsm.get("fechaInicio") + " " + "00:00:00.000",
               fechaFin = parametrosHsm.get("fechaFin") + " " + "23:59:59.000";

         ProcedureCall call = session.createStoredProcedureCall("SP_TIEMPO_EFICIENTE_FRESAM");
//       ProcedureCall call = session.createStoredProcedureCall("SP_TIEMPO_EFICIENTE_FRESAM_PRUEBA");

        session.beginTransaction();

        call.registerParameter(1, String.class, ParameterMode.IN);
        call.registerParameter(2, String.class, ParameterMode.IN);
        call.registerParameter(3, String.class, ParameterMode.IN);

        call.setParameter(1, cveEmpleado);
        call.setParameter(2, fechaInicio);
        call.setParameter(3, fechaFin);

        call.execute();
        session.getTransaction().commit();
    }

    public void generaExcelResumen(HashMap<String, String> parametrosHsm) {

        SialReportExcelFactory reporte = new SialReportExcelFactory();
        DataHeaderEnterprise dataHeaderEnterprise = new DataHeaderEnterprise();

        // Generar nombre del reporte
        String nombreArchivo = "reporteResumen_".concat(parametrosHsm.get("fechaInicio")).concat("_").concat(parametrosHsm.get("fechaFin")).concat(".xlsx");
        dataHeaderEnterprise.setCveReport("CHREPF01");
        dataHeaderEnterprise.setEnterprise("AGRO FRESAM S.A DE C.V");
        dataHeaderEnterprise.setTitleReport("REPORTE DE ACCESOS");
        dataHeaderEnterprise.setVersion("1.0.0");

        // Consultar la informacion requerida
        consultarRegistros();

        // Ordenar arreglos por clave de empleado y dia
        ordenarArrPorClaveEmpDia();

        // Encabezado dinamico
        generarEncabezadoDinamico();

        //CREAR LISTA FINAL
        llenarListaFinal(posionFinalTotal);

//        /**
//         * Reporte Generico de chavon
//         */
//        reporte.setAutoSizeColumn(Boolean.FALSE);
//        reporte.createReportGeneric(
//                "ReportGeneric.xlsx",
//                dataHeaderEnterprise,
//                mapping,
//                listaFinal
//        );
        /**
         * Reporte Generico de el proyecto
         */
        reporte.createReportGeneric(
                nombreArchivo,
                dataHeaderEnterprise,
                mapping,
                listaFinal,
                posionFinalTotal,
                parametrosHsm
        );

    }

    public void generaExcelDetallado(HashMap<String, String> parametrosHsm) {
        SialReportExcelFactory reporte = new SialReportExcelFactory();
        DataHeaderEnterprise dataHeaderEnterprise = new DataHeaderEnterprise();
        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        String[] mapping;

        // Generar nombre del reporte
        String nombreArchivo = "reporteDetallado_".concat(parametrosHsm.get("fechaInicio")).concat("_").concat(parametrosHsm.get("fechaFin")).concat(".xlsx");
        dataHeaderEnterprise.setCveReport("CHREPF01");
        dataHeaderEnterprise.setEnterprise("AGRO FRESAM S.A DE C.V");
        dataHeaderEnterprise.setTitleReport("REPORTE DE ACCESOS");
        dataHeaderEnterprise.setVersion("1.0.0");

        Query query = session.createNativeQuery("SELECT CVE_EMPLEADO, NOMBRE, PUESTO, FECHA, HORA_ENTRADA, HORA_SALIDA, HORAS_MINUTOS, TIEMPO_EFECTIVO, OBSERVACIONES\n" +
                                                "FROM resultados \n" +
                                                "WHERE fecha is not null and hora_entrada is not null and hora_salida is not null\n" +
                                                "ORDER BY cast(CVE_EMPLEADO as int), id");
        List<Object[]> list = query.getResultList();

        mapping = new String[]{
            "CVE_EMPLEADO",
            "NOMBRE",
            "PUESTO",
            "FECHA",
            "HORA_ENTRADA",
            "HORA_SALIDA",
            "HORAS_MINUTOS",
            "TIEMPO_EFECTIVO",
            "OBSERVACIONES"
        };

        reporte.createReportGeneric(
                nombreArchivo,
                dataHeaderEnterprise,
                mapping,
                list,
                mapping.length,
                parametrosHsm
        );

    }

    public void generaExcelSumarizado(HashMap<String, String> parametrosHsm) {
        SialReportExcelFactory reporte = new SialReportExcelFactory();
        DataHeaderEnterprise dataHeaderEnterprise = new DataHeaderEnterprise();
        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        String[] mapping;

        // Generar nombre del reporte
        String nombreArchivo = "reporteSumarizado_".concat(parametrosHsm.get("fechaInicio")).concat("_").concat(parametrosHsm.get("fechaFin")).concat(".xlsx");
        dataHeaderEnterprise.setCveReport("CHREPF01");
        dataHeaderEnterprise.setEnterprise("AGRO FRESAM S.A DE C.V");
        dataHeaderEnterprise.setTitleReport("REPORTE DE ACCESOS");
        dataHeaderEnterprise.setVersion("1.0.0");

        Query query = session.createNativeQuery("select CVE_EMPLEADO, NOMBRE, PUESTO, FECHA, \n"
                + "	RIGHT('0' + CAST(SUM(TIEMPO_EFECTIVO) / 60 AS VARCHAR),2)  + ':' +\n"
                + "	RIGHT('0' + CAST(SUM(TIEMPO_EFECTIVO) % 60 AS VARCHAR),2)\n"
                + "	 AS Time\n"
                + ",MAX(OBSERVACIONES) OBSERVACIONES\n"
                + "from resultados\n"
                + "WHERE fecha is not null and hora_entrada is not null and hora_salida is not null\n"
                + "GROUP BY FECHA, CVE_EMPLEADO, NOMBRE, PUESTO\n"
                + "order by cast(CVE_EMPLEADO as integer), cast(FECHA as datetime)");
        List<Object[]> list = query.getResultList();

        mapping = new String[]{
            "CVE_EMPLEADO",
            "NOMBRE",
            "PUESTO",
            "FECHA",
            "TIEMPO_POR_DÍA",
            "OBSERVACIONES"
        };

        reporte.createReportGeneric(
                nombreArchivo,
                dataHeaderEnterprise,
                mapping,
                list,
                mapping.length,
                parametrosHsm
        );
    }

    public void generaExcelOrigen(HashMap<String, String> parametrosHsm) {
        SialReportExcelFactory reporte = new SialReportExcelFactory();
        DataHeaderEnterprise dataHeaderEnterprise = new DataHeaderEnterprise();
        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        String[] mapping;

        // Generar nombre del reporte
        String nombreArchivo = "reporteOrigen_".concat(parametrosHsm.get("fechaInicio")).concat("_").concat(parametrosHsm.get("fechaFin")).concat(".xlsx");
        dataHeaderEnterprise.setCveReport("CHREPF01");
        dataHeaderEnterprise.setEnterprise("AGRO FRESAM S.A DE C.V");
        dataHeaderEnterprise.setTitleReport("REPORTE DE ACCESOS");
        dataHeaderEnterprise.setVersion("1.0.0");

        Query query = session.createNativeQuery("select * from entradasSalidas WHERE CVE_EMPLEADO LIKE :clave order by cast(CVE_EMPLEADO as integer),cast(FECHA_HORA as datetime)");
        query.setParameter("clave", parametrosHsm.get("cveEmpleado"));

        List<Object[]> list = query.getResultList();

        mapping = new String[]{
            "CVE_EMPLEADO",
            "NOMBRE",
            "PUESTO",
            "FECHA_HORA",
            "TIPO_MARCAJE"
        };

        reporte.createReportGeneric(
                nombreArchivo,
                dataHeaderEnterprise,
                mapping,
                list,
                mapping.length,
                parametrosHsm
        );

    }

    public void ordenarArrPorClaveEmpDia() {
        try {

            for (int i = 0; i < tamanioLista; i++) {

                if (list.get(i)[0] != null && list.get(i)[3] != null) {

                    claveEmpLista = list.get(i)[0].toString();
                    fechaLista = list.get(i)[3].toString();

                    if (!claveEmp.equals(claveEmpLista) || !fecha.equals(fechaLista)) {
                        claveEmp = claveEmpLista;
                        fecha = fechaLista;
                        is = true;
                        contador++;
                        if (contador == -1) {
                            nombreArrayList.add(list.get(i));
                        } else {
                            insertarRegistrosPorDia();
                            nombreArrayList.add(list.get(i));
                        }
                    } else {
                        nombreArrayList.add(list.get(i));
                        is = false;
                        if (i + 1 == tamanioLista) {
                            is = true;
                            contador++;
                        }
                        insertarRegistrosPorDia();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ordenarArrPorClaveEmpDia: " + e);
        }
    }

    public void insertarRegistrosPorDia() {
        try {
            if (contador >= 0 && is && nombreArrayList.size() > 0) {

                int cont = 0, tamanioTabla = list.get(0).length, agregarHoras = 0, tamanioArr = nombreArrayList.size() * tamanioTabla;
                tamanioArr = tamanioArr == 6 ? tamanioArr + 1 : tamanioArr;
                String cveEmp = "", fecha = "", nombre = "", puesto = "";
                Object[] registroEmpForDia = new Object[tamanioArr];

                for (Object[] item : nombreArrayList) {
                    for (int j = 0; j < item.length; j++) {

                        if (item[j] != null) {
                            if (cont < 4) {
                                registroEmpForDia[cont] = item[j];
                                cveEmp = registroEmpForDia[0].toString();
                                fecha = item[1].toString();
                                nombre = item[2].toString();
                                puesto = item[3].toString();
                                cont++;
                            } else if (!cveEmp.equals(item[j].toString()) && !fecha.equals(item[j].toString())
                                    && !nombre.equals(item[j].toString()) && !puesto.equals(item[j].toString())) {
                                registroEmpForDia[cont] = item[j];
                                cont++;
                            }
                        }

                    }
                }

                agregarHoras = registroEmpForDia.length - 1;
                registroEmpForDia[agregarHoras] = listConTotales.get(contadorHoras)[2];
//                if (registroEmpForDia[0].toString().equals(listConTotales.get(contadorHoras)[0])
//                        && registroEmpForDia[3].toString().equals(listConTotales.get(contadorHoras)[1])) {
//                    registroEmpForDia[agregarHoras] = listConTotales.get(contadorHoras)[2];
//                } else {
//                    registroEmpForDia[agregarHoras] = "INCONSISTENCIA";
//                    contadorHoras--;
//                }

                nuevaLista.add(registroEmpForDia);
                nombreArrayList = new ArrayList();
                contadorHoras++;
            }
        } catch (Exception e) {
            System.out.println("insertarRegistrosPorDia, Exception:" + e);
        }

    }

    public void consultarRegistros() {

        try {

            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            Session session = sessionFactory.openSession();
            Query queryTotalesHoras = session.createNativeQuery(" select CVE_EMPLEADO, FECHA, \n"
                    + "	RIGHT('0' + CAST(SUM(TIEMPO_EFECTIVO) / 60 AS VARCHAR),2)  + ':' +\n"
                    + "	RIGHT('0' + CAST(SUM(TIEMPO_EFECTIVO) % 60 AS VARCHAR),2)\n"
                    + "	 AS Time\n"
                    + ",MAX(OBSERVACIONES) OBSERVACIONES\n"
                    + "from resultados\n"
                    + "WHERE fecha is not null and hora_entrada is not null and hora_salida is not null\n"
                    + "GROUP BY FECHA, CVE_EMPLEADO\n"
                    + "order by cast(CVE_EMPLEADO as integer), cast(FECHA as datetime)");
            listConTotales = queryTotalesHoras.getResultList();

            Query query = session.createNativeQuery("SELECT CVE_EMPLEADO, NOMBRE, PUESTO, FECHA, convert(varchar(5),cast(HORA_entrada as datetime),108) HORA_ENTRADA, convert(varchar(5),cast(HORA_SALIDA as datetime),108) HORA_SALIDA FROM resultados WHERE fecha is not null and hora_entrada is not null and hora_salida is not null order by cast(CVE_EMPLEADO as integer), cast(FECHA as datetime)");
            list = query.getResultList();

            Query queryTiempoAcumulado = session.createNativeQuery("select CVE_EMPLEADO, \n"
                    + "	RIGHT('0' + CAST(SUM(TIEMPO_EFECTIVO) / 60 AS VARCHAR),2)  + ':' +\n"
                    + "	RIGHT('0' + CAST(SUM(TIEMPO_EFECTIVO) % 60 AS VARCHAR),2)\n"
                    + "	 AS TiempoTotal\n"
                    + "from resultados\n"
                    + "WHERE fecha is not null and hora_entrada is not null and hora_salida is not null\n"
                    + "GROUP BY CVE_EMPLEADO\n"
                    + "order by cast(CVE_EMPLEADO as integer)");
            listaTotalTiempoAcumulado = queryTiempoAcumulado.getResultList();

            tamanioLista = list.size();

        } catch (Exception e) {
            System.out.println("consultarRegistros: " + e);
        }
    }

    public void llenarListaFinal(int tamanioArr) {
        try {
            int tamanioBody = nuevaLista.size(), tamaniPosisonItem = 0, contador =0;
            boolean isEqual = true;
            for (int i = 0; i < tamanioBody; i++) {
                Object[] registroEmpleado = new Object[tamanioArr + 2];
                tamaniPosisonItem = nuevaLista.get(i).length;
                for (int j = 0; j < tamaniPosisonItem; j++) {
                    //LLenar el total de tiempo acumulado
                    if(j == 0){
                        if(i != (tamanioBody -1)){
                            isEqual = nuevaLista.get(i)[j].toString().equals(nuevaLista.get(i+1)[j].toString()) ? true : false;
                        }else {
                            isEqual = false;
                        }
                        if(!isEqual){
                            registroEmpleado[tamanioArr+1] = listaTotalTiempoAcumulado.get(contador)[1];
                            contador++;
                        }
                    }

                    if (j != (tamaniPosisonItem - 1)) { // El ultimo
                        if (nuevaLista.get(i)[j] != null) {
                            registroEmpleado[j] = nuevaLista.get(i)[j];
                        }
                    } else {
                        registroEmpleado[tamanioArr] = nuevaLista.get(i)[j];
                    }
                    
                }

                listaFinal.add(registroEmpleado);
            }
        } catch (Exception e) {
            System.out.println("llenarListaFinal, Exception:" + e);
        }
    }

    public void generarEncabezadoDinamico() {
        // Sacar el registro que tenga mayor longitud
        tamanioLista = nuevaLista.size();
        for (int i = 0; i < tamanioLista; i++) {
            if (registroConMayorLogitud < nuevaLista.get(i).length) {
                registroConMayorLogitud = nuevaLista.get(i).length;
            }
        }

        posionFinalTotal = ((registroConMayorLogitud - 6) / 6) * 2;
        posionFinalTotal = posionFinalTotal + 6;
        registroConMayorLogitud = (registroConMayorLogitud) / 6;

        ArrayList<String> nombreArrayList = new ArrayList<String>();
        nombreArrayList.add("CVE_EMPLEADO");
        nombreArrayList.add("NOMBRE");
        nombreArrayList.add("PUESTO");
        nombreArrayList.add("FECHA");
        nombreArrayList.add("ENTRADA");
        nombreArrayList.add("SALIDA");

        if (registroConMayorLogitud > 1) {
            for (int i = 1; i < registroConMayorLogitud; i++) {
                nombreArrayList.add("ENTRADA");
                nombreArrayList.add("SALIDA");
            }
        }

        nombreArrayList.add("TOTAL_HORAS");
        nombreArrayList.add("ACUMULADO");
        mapping = new String[nombreArrayList.size()];
        for (int i = 0; i < nombreArrayList.size(); i++) {
            mapping[i] = nombreArrayList.get(i);
        }

    }

    public void generaExcelPrueba(HashMap<String, String> parametrosHsm) {
        SialReportExcelFactory reporte = new SialReportExcelFactory();
        DataHeaderEnterprise dataHeaderEnterprise = new DataHeaderEnterprise();
        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        String[] mapping;

        // Generar nombre del reporte
        String nombreArchivo = "reporteOrigen_".concat(parametrosHsm.get("fechaInicio")).concat("_").concat(parametrosHsm.get("fechaFin")).concat(".xlsx");
        dataHeaderEnterprise.setCveReport("CHREPF01");
        dataHeaderEnterprise.setEnterprise("EMPRESA X S.A DE C.V");
        dataHeaderEnterprise.setTitleReport("REPORTE EMPLEADOS");
        dataHeaderEnterprise.setVersion("1.0.0");

//        Query query = session.createNativeQuery("select * from empleados");
        Query query = session.createNativeQuery("select codigo, nombres, apellidos, telefono, edad from empleado");


        List<Object[]> list = query.getResultList();

        mapping = new String[]{
            "CVE_EMPLEADO",
            "NOMBRE",
            "APELLIDOS",
            "TELEFONO",
            "EDAD"
        };

        reporte.createReportGeneric(
                nombreArchivo,
                dataHeaderEnterprise,
                mapping,
                list,
                mapping.length,
                parametrosHsm
        );

    }
      
    public void generaExcelPruebaParametro(HashMap<String, String> parametrosHsm) {
        SialReportExcelFactory reporte = new SialReportExcelFactory();
        DataHeaderEnterprise dataHeaderEnterprise = new DataHeaderEnterprise();
        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        Session session = sessionFactory.openSession();
        String[] mapping;

        // Generar nombre del reporte
        String nombreArchivo = "reporteOrigenParametros_".concat(parametrosHsm.get("fechaInicio")).concat("_").concat(parametrosHsm.get("fechaFin")).concat(".xlsx");
        dataHeaderEnterprise.setCveReport("CHREPF01");
        dataHeaderEnterprise.setEnterprise("EMPRESA X S.A DE C.V");
        dataHeaderEnterprise.setTitleReport("REPORTE EMPLEADOS");
        dataHeaderEnterprise.setVersion("1.0.0");

//        Query query = session.createNativeQuery("select * from empleados WHERE CVE_EMPLEADO LIKE :clave");
        Query query = session.createNativeQuery("select codigo, nombres, apellidos, telefono, edad from empleado WHERE CAST(codigo AS VARCHAR) LIKE :clave");
        query.setParameter("clave", (parametrosHsm.get("cveEmpleado").length() > 0 ? parametrosHsm.get("cveEmpleado") : "%" + parametrosHsm.get("cveEmpleado") + "%"));
        
        List<Object[]> list = query.getResultList();

        mapping = new String[]{
            "CVE_EMPLEADO",
            "NOMBRE",
            "APELLIDOS",
            "TELEFONO",
            "EDAD"
        };

        reporte.createReportGeneric(
                nombreArchivo,
                dataHeaderEnterprise,
                mapping,
                list,
                mapping.length,
                parametrosHsm
        );

    }

}
