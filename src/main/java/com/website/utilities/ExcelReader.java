package com.website.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * ExcelReader - Reads test data from .xlsx Excel files.
 *
 * WHY: Keeps test data separate from code. Testers can edit Excel without touching Java.
 * HOW: Use with @DataProvider to feed data into @Test methods.
 *
 * USAGE:
 *   Object[][] data = ExcelReader.getTestData("src/test/resources/testdata.xlsx", "LoginData");
 *   // Returns all rows (excluding header) as a 2D array
 *
 * REUSABLE: Yes - copy to any Selenium project.
 * LOCATION: src/main/java/.../utilities/
 */
public class ExcelReader {

    private static final Logger logger = LogManager.getLogger(ExcelReader.class);

    /**
     * Reads all data from a specific sheet in an Excel file.
     * Skips the first row (header row).
     * Returns data as Object[][] — ready for @DataProvider.
     *
     * @param filePath  - path to .xlsx file (e.g., "src/test/resources/testdata.xlsx")
     * @param sheetName - name of the sheet to read (e.g., "LoginData")
     * @return Object[][] containing all rows and columns (excluding header)
     */
    public static Object[][] getTestData(String filePath, String sheetName) {
        Object[][] data = null;

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in: " + filePath);
            }

            int totalRows = sheet.getPhysicalNumberOfRows() - 1; // Exclude header
            
            // FIX 1: Use getLastCellNum() to get the true column count, ignoring blanks
            //int totalCols = sheet.getRow(0).getLastCellNum(); 
            int totalCols = 11;

            data = new Object[totalRows][totalCols];

            // Start from row 1 (skip header at row 0)
            for (int i = 0; i < totalRows; i++) {
                Row row = sheet.getRow(i + 1);
                
                // Safety check: if an entire row is completely empty, skip it
                if (row == null) continue; 
                
                for (int j = 0; j < totalCols; j++) {
                    // FIX 2: Safely handle intentionally blank cells in your negative test data
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i][j] = getCellValue(cell);
                }
            }

            logger.info("Excel data loaded: " + totalRows + " rows and " + totalCols + " columns from sheet '" + sheetName + "'");

        } catch (IOException e) {
            logger.error("Failed to read Excel file: " + filePath);
            e.printStackTrace();
        }

        return data;
    }
    /**
     * Safely extracts the value from a cell, handling all cell types.
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                // Avoid returning "1.0" for whole numbers like 1
                double numVal = cell.getNumericCellValue();
                if (numVal == Math.floor(numVal)) {
                    return String.valueOf((long) numVal);
                }
                return String.valueOf(numVal);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return cell.toString().trim();
        }
    }
}
