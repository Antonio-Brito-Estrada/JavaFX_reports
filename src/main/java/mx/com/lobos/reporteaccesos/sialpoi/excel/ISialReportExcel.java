package mx.com.lobos.reporteaccesos.sialpoi.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ISialReportExcel {

    XSSFWorkbook getWorkbook();

    XSSFRow getRow();

    XSSFSheet getSheet();

    int getRowsTotal();
    
    XSSFRow createRow(XSSFSheet sheet, int indexRow);

    XSSFCell createCell(XSSFRow row, int indexRow);

    XSSFCell createCellData(XSSFRow row, int indexCell, Object value, XSSFCellStyle cellStyle);

    void createFile(String name);
    
    void buildReport(int totalRenglones);
    
    byte[] getReportStream();
}
