package mx.com.lobos.reporteaccesos.tasks;

import javafx.concurrent.Task;
import mx.com.lobos.reporteaccesos.accesos.service.accesosservice.accesosService;
import mx.com.lobos.reporteaccesos.singletonUtils.SingletonParams;

public class ProcesTask extends Task<String>{

    @Override
    protected String call() throws Exception {
        accesosService service = new accesosService();
        SingletonParams params = SingletonParams.getInstance();
        java.lang.String mensaje = "";

        Thread.sleep(1505);
        updateProgress(0, 500);

        try {

            service.generaExcelPrueba(params.getParametrosHsm());
            updateProgress(400, 500);
            
            service.generaExcelPruebaParametro(params.getParametrosHsm());
            updateProgress(400, 500);

//            service.procesoAccesos(params.getParametrosHsm());
//            updateProgress(100, 500);
//
//            service.generaExcelSumarizado(params.getParametrosHsm());
//            updateProgress(200, 500);
//
//            service.generaExcelDetallado(params.getParametrosHsm());
//            updateProgress(300, 500);
//
//            service.generaExcelOrigen(params.getParametrosHsm());
//            updateProgress(400, 500);
//
//            service.generaExcelResumen(params.getParametrosHsm());
//            updateProgress(500, 500);

            mensaje = "Proceso realizado correctamente";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            mensaje = "Error al generar el excel";
        }
        return mensaje;
    }
    
}
