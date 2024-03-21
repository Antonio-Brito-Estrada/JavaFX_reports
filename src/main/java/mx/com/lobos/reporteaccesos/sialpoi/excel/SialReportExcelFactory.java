package mx.com.lobos.reporteaccesos.sialpoi.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.com.lobos.reporteaccesos.sialpoi.excel.pojos.DataHeaderEnterprise;
import mx.com.lobos.reporteaccesos.sialpoi.excel.reportgeneric.SialReportGenericExcel;
import mx.com.lobos.reporteaccesos.sialpoi.excel.reportgenericsum.SialReportGenericSumExcel;
import mx.com.lobos.reporteaccesos.sialpoi.excel.reportstyles.SialReportExcelStyles;
import mx.com.lobos.reporteaccesos.sialpoi.util.Utils;

public class SialReportExcelFactory {

public void createReportGeneric(
            String nameFile,
            DataHeaderEnterprise dataHeaderEnterprise,
            String[] headers,
            List<Object[]> body,
            int totalRenglones,
            Map<String, String> parametrosHsm
    ) {
        SialReportGenericExcel excel = new SialReportGenericExcel(
                new SialReportExcelStyles(),
                dataHeaderEnterprise,
                new Utils()
        );
        excel.setHeaders(headers);
        excel.setBody(body);
        excel.buildReport(totalRenglones);
        excel.createFile(parametrosHsm.get("rutaArchivo").concat(nameFile));
    }

    public byte[] createReportGenericStream(
            DataHeaderEnterprise dataHeaderEnterprise,
            String[] headers,
            List<Object[]> body
    ) {
        SialReportGenericExcel excel = new SialReportGenericExcel(
                new SialReportExcelStyles(),
                dataHeaderEnterprise,
                new Utils()
        );
        excel.setHeaders(headers);
        excel.setBody(body);
        excel.buildReport(0);
        return excel.getReportStream();
    }

    public void createReportGenericSum(
            String nameFile,
            DataHeaderEnterprise dataHeaderEnterprise,
            String[] headers,
            List<Object[]> body,
            Map<String, String> parametrosHsm
    ) {
        SialReportGenericSumExcel excel = new SialReportGenericSumExcel(
                new SialReportExcelStyles(),
                dataHeaderEnterprise,
                new Utils()
        );
        excel.setHeaders(headers);
        excel.setBody(body);
        excel.setParametrosHsm((HashMap<String, String>) parametrosHsm);
        excel.buildReport(0);
        excel.createFile(nameFile);
    }

}
