package mx.com.lobos.reporteaccesos.sialpoi.excel.reportgeneric;

import java.util.Date;
import java.util.List;
import mx.com.lobos.reporteaccesos.sialpoi.excel.SialReportExcelAbstract;
import mx.com.lobos.reporteaccesos.sialpoi.excel.pojos.DataHeaderEnterprise;
import mx.com.lobos.reporteaccesos.sialpoi.excel.reportlabels.SialLabels;
import mx.com.lobos.reporteaccesos.sialpoi.excel.reportstyles.SialReportExcelStyles;
import mx.com.lobos.reporteaccesos.sialpoi.util.Utils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class SialReportGenericExcel extends SialReportExcelAbstract implements ISialReportGenericExcel {

    private final SialReportExcelStyles sialReportExcelStyles;
    private final DataHeaderEnterprise dataHeaderEnterprise;
    private final Utils utils;
    private String[] headers;
    private List<Object[]> body;

    public String[] getHeaders() {
        return headers;
    }

    public List<Object[]> getBody() {
        return body;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public void setBody(List<Object[]> body) {
        this.body = body;
    }

    public SialReportGenericExcel(
            SialReportExcelStyles sialReportExcelStyles,
            DataHeaderEnterprise dataHeaderEnterprise,
            Utils utils
    ) {
        super();
        this.sialReportExcelStyles = sialReportExcelStyles;
        this.dataHeaderEnterprise = dataHeaderEnterprise;
        this.utils = utils;
    }

    @Override
    public void createBodyHeaders() {
        for (int i = 0; i < 2; i++) {
            createRow(getSheet(), getRowsTotal());
        }

        XSSFFont font = sialReportExcelStyles.getFontStyleHeaderSial(getWorkbook());
        XSSFCellStyle cellStyle = sialReportExcelStyles.getCellStyleHeaderSial(getWorkbook(), font);
        for (int i = 0; i < getHeaders().length; i++) {
            createCellData(getRow(), i, getHeaders()[i], cellStyle);
            getWorkbook().getSheetAt(0).autoSizeColumn(i, true);
        }
    }

    @Override
    public void createTotalRecords() {
        for (int i = 0; i < 2; i++) {
            createRow(getSheet(), getRowsTotal());
        }
        createCellData(getRow(), 0, SialLabels.TOTAL_RECORDS, null);
        createCellData(getRow(), 1, getBody().size(), null);
        getWorkbook().getSheetAt(0).autoSizeColumn(0);
    }

    @Override
    public void createBody(int totalRenglones) {
        int tamanioBody=getBody().size(), tamaniPosisonItem=0; 
        for (int i = 0; i < tamanioBody; i++) {
            tamaniPosisonItem = getBody().get(i).length;
            XSSFRow row = createRow(getSheet(), getRowsTotal());
            for (int j = 0; j < tamaniPosisonItem; j++) {
                    if (getBody().get(i)[j] != null) {
                       createCellData(row, j, getBody().get(i)[j], null);
                    }
            }
        }
    }

    @Override
    public void createHeaderSIAL() {
        XSSFRow row_0,
                row_1,
                row_2;
        XSSFFont font = sialReportExcelStyles.getFontStyleHeaderSial(getWorkbook());
        XSSFCellStyle cellStyle = sialReportExcelStyles.getCellStyleHeaderSial(getWorkbook(), font);

        row_0 = createRow(getSheet(), getRowsTotal());
        createCellData(row_0, 0, dataHeaderEnterprise.getVersion(), cellStyle);
        getWorkbook().getSheetAt(0).autoSizeColumn(0);
        getSheet().addMergedRegion(new CellRangeAddress(0, 0, 1, 9));

        createCellData(row_0, 1, dataHeaderEnterprise.getEnterprise(), cellStyle);
        createCellData(row_0, 10, SialLabels.FECHA, cellStyle);
        createCellData(row_0, 11, utils.formateaFecha(new Date(), "dd MMM yyyy"), cellStyle);
        getWorkbook().getSheetAt(0).autoSizeColumn(11);

        row_1 = createRow(getSheet(), getRowsTotal());
        createCellData(row_1, 0, dataHeaderEnterprise.getCveReport(), cellStyle);
        getSheet().addMergedRegion(new CellRangeAddress(1, 1, 1, 9));
        createCellData(row_1, 10, SialLabels.HORA, cellStyle);
        createCellData(row_1, 1, "", cellStyle);
        createCellData(row_1, 11, utils.formateaFecha(new Date(), "HH:mm:ss"), cellStyle);

        row_2 = createRow(getSheet(), getRowsTotal());
        createCellData(row_2, 0, "", cellStyle);
        getSheet().addMergedRegion(new CellRangeAddress(2, 2, 1, 9));
        createCellData(row_2, 1, dataHeaderEnterprise.getTitleReport(), cellStyle);
        getSheet().addMergedRegion(new CellRangeAddress(2, 2, 10, 11));
        createCellData(row_2, 10, "", cellStyle);
    }

    @Override
    public void buildReport(int totalRenglones) {
        createHeaderSIAL();
        createBodyHeaders();
        createBody(totalRenglones);
        createTotalRecords();
    }
}
