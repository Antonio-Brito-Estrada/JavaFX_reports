package mx.com.lobos.reporteaccesos.sialpoi.excel.reportgenericsum;

import java.util.HashMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public interface ISialReportGenericSumExcel {

    void createSumColumn(HashMap<String, String> parametrosHsm);

    XSSFCell createCellSum(XSSFRow row, int indexCell, String since, String to);
}
