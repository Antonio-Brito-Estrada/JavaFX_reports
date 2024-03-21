package mx.com.lobos.reporteaccesos.sialpoi.excel.reportgenericsum;

import mx.com.lobos.reporteaccesos.sialpoi.excel.reportgeneric.SialReportGenericExcel;
import java.util.HashMap;
import mx.com.lobos.reporteaccesos.sialpoi.excel.pojos.DataHeaderEnterprise;
import mx.com.lobos.reporteaccesos.sialpoi.excel.reportstyles.SialReportExcelStyles;
import mx.com.lobos.reporteaccesos.sialpoi.util.Utils;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class SialReportGenericSumExcel extends SialReportGenericExcel implements ISialReportGenericSumExcel {

    private int indexRowBody;

    private HashMap<String, String> parametrosHsm;

    private int getIndexRowBody() {
        return indexRowBody;
    }

    private void setIndexRowBody(int indexRowBody) {
        this.indexRowBody = indexRowBody;
    }

    public HashMap<String, String> getParametrosHsm() {
        return parametrosHsm;
    }

    public void setParametrosHsm(HashMap<String, String> parametrosHsm) {
        this.parametrosHsm = parametrosHsm;
    }

    public SialReportGenericSumExcel(
            SialReportExcelStyles sialReportExcelStyles,
            DataHeaderEnterprise dataHeaderEnterprise,
            Utils utils
    ) {
        super(sialReportExcelStyles, dataHeaderEnterprise, utils);
    }

    @Override
    public void buildReport(int totalRenglones) {
        createHeaderSIAL();
        createBodyHeaders();
        setIndexRowBody(getRowsTotal());
        createBody(0);
        createSumColumn(getParametrosHsm());
        createTotalRecords();
    }

    @Override
    public void createSumColumn(HashMap<String, String> parametrosHsm) {
        if (getParametrosHsm().containsKey("sumatoria")) {
            XSSFRow rowSum = this.createRow(getSheet(), getRowsTotal());
            for (int j = 0; j < getHeaders().length; j++) {
                if (getParametrosHsm().get("sumatoria").equals(getHeaders()[j])) {
                    String column_letra = CellReference.convertNumToColString(j),
                            since = column_letra + (getIndexRowBody() + 1),
                            to = column_letra + (getRowsTotal() - 1);
                    createCellSum(rowSum, j, since, to);
                    getWorkbook().getSheetAt(0).autoSizeColumn(0);
                }
            }
        }
    }

    @Override
    public XSSFCell createCellSum(
            XSSFRow row,
            int indexCell,
            String since,
            String to
    ) {
        XSSFCell celda;
        String formula = "SUM(".concat(since).concat(":").concat(to).concat(")");
        celda = createCell(row, indexCell);
        celda.setCellFormula(formula);
        return celda;
    }

}
