package mx.com.lobos.reporteaccesos.sialpoi.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class SialReportExcelAbstract implements ISialReportExcel {
    
    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String SEPARATOR = System.getProperty("file.separator");


    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private int rowsTotal = 0;

    public SialReportExcelAbstract() {
        createWorkBook();
        createSheet();
    }

    @Override
    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    @Override
    public XSSFRow getRow() {
        return row;
    }

    @Override
    public XSSFSheet getSheet() {
        return sheet;
    }

    @Override
    public int getRowsTotal() {
        return rowsTotal;
    }

    private void createWorkBook() {
        workbook = new XSSFWorkbook();
    }

    private void createSheet() {
        sheet = workbook.createSheet();
    }

    @Override
    public XSSFRow createRow(XSSFSheet sheet, int indexRow) {
        row = sheet.createRow(indexRow);
        rowsTotal++;
        return row;
    }

    @Override
    public XSSFCell createCell(XSSFRow row, int indexRow) {
        XSSFCell cell;
        cell = row.createCell(indexRow);
        return cell;
    }

    @Override
    public XSSFCell createCellData(
            XSSFRow row,
            int indexCell,
            Object value,
            XSSFCellStyle cellStyle
    ) {
        XSSFCell cell = this.createCell(row, indexCell);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
        return cell;
    }

    @Override
        public byte[] getReportStream() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Fallo al importa el archivo excel: " + e.getMessage());
        }
    }

    @Override
    public void createFile(String pathname) {
        FileOutputStream fis = null;
        File file;
        try {
            file = new File(pathname);
            System.out.println("File: " + file.getAbsolutePath());
            fis = new FileOutputStream(file);
            workbook.write(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SialReportExcelAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SialReportExcelAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(SialReportExcelAbstract.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public abstract void buildReport(int totalRenglones);
}
