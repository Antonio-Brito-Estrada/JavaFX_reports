package mx.com.lobos.reporteaccesos.sialpoi.excel.reportstyles;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SialReportExcelStyles {
    public XSSFFont getFontStyleHeaderSial(XSSFWorkbook workbook) {
        XSSFFont font;
        font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Arial");
        font.setColor(new XSSFColor(getColorRGB(255, 255, 255), null));
        return font;
    }

    public XSSFCellStyle getCellStyleHeaderSial(XSSFWorkbook workbook, XSSFFont font) {
        XSSFCellStyle cellStyle;
        cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillBackgroundColor(new XSSFColor(getColorRGB(255, 0, 0), null));
        cellStyle.setFillForegroundColor(new XSSFColor(getColorRGB(255, 0, 0), null));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    private byte[] getColorRGB(int r, int g, int b) {
        return new byte[]{(byte) r, (byte) g, (byte) b};
    }
}
