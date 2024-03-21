package mx.com.lobos.reporteaccesos.sialpoi.excel.reportgeneric;

public interface ISialReportGenericExcel {

    void createBodyHeaders();

    void createTotalRecords();

    void createBody(int totalRenglones);

    void createHeaderSIAL();
}
