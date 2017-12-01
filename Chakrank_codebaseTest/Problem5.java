package com.java.Assignment6;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class Problem5 {

        private WritableCellFormat timesBoldUnderline;
        private WritableCellFormat times;
        private String inputFile;

public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
        }

        public void write() throws IOException, WriteException {
                File file = new File(inputFile);
                WorkbookSettings wbSettings = new WorkbookSettings();

                wbSettings.setLocale(new Locale("en", "EN"));

                WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
                workbook.createSheet("Report", 0);
                WritableSheet excelSheet = workbook.getSheet(0);
                createLabel(excelSheet);
                createContent(excelSheet);

                workbook.write();
                workbook.close();
        }

        private void createLabel(WritableSheet sheet)
                        throws WriteException {
                // Lets create a times font
                WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
                // Define the cell format
                times = new WritableCellFormat(times10pt);
                // Lets automatically wrap the cells
                times.setWrap(true);

                // create create a bold font with unterlines
                WritableFont times10ptBoldUnderline = new WritableFont(
                                WritableFont.TIMES, 10, WritableFont.BOLD, false,
                                UnderlineStyle.SINGLE);
                timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
                // Lets automatically wrap the cells
                timesBoldUnderline.setWrap(true);

                CellView cv = new CellView();
                cv.setFormat(times);
                cv.setFormat(timesBoldUnderline);
                cv.setAutosize(true);
        }

        private void createContent(WritableSheet sheet) throws WriteException,
                        RowsExceededException {
                // Write a few number
                for (int i = 1; i < 10; i++) {
                        // First column
                        addNumber(sheet, 0, i, i + 10);
                        // Second column
                        addNumber(sheet, 1, i, i * i);
                }
                // Lets calculate the sum of it
                StringBuffer buf = new StringBuffer();
                buf.append("SUM(A2:A10)");
                Formula f = new Formula(0, 10, buf.toString());
                sheet.addCell(f);
                buf = new StringBuffer();
                buf.append("SUM(B2:B10)");
                f = new Formula(1, 10, buf.toString());
                sheet.addCell(f);
        }

        private void addNumber(WritableSheet sheet, int column, int row,
                        Integer integer) throws WriteException, RowsExceededException {
                Number number;
                number = new Number(column, row, integer, times);
                sheet.addCell(number);
        }

        public static void main(String[] args) throws WriteException, IOException {
                Problem5 test = new Problem5();
                test.setOutputFile("C:\\Users\\SAIKIRAN\\Desktop\\lars.xls");
                test.write();
                System.out.println("File successfully created ");
        }
}