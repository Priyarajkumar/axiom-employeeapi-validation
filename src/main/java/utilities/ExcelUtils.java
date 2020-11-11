
package utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ExcelUtils {

    private static XSSFSheet ExcelWSheet;

    private static XSSFWorkbook ExcelWBook;

    private static XSSFCell Cell;

    private static XSSFRow Row;

    public static HashMap<String, String> getTableArray(String FilePath, String SheetName) throws Exception {

        HashMap<String, String> testDataMap = new HashMap<String, String>();
        String[][] tabArray = null;

        try {

            FileInputStream ExcelFile = new FileInputStream(FilePath);
            ExcelWBook = new XSSFWorkbook(ExcelFile);

            ExcelWSheet = ExcelWBook.getSheet(SheetName);

            int startRow = 1;

            int startCol = 0;

            int ci, cj;

            int totalRows = ExcelWSheet.getLastRowNum();

            ci = 0;

            for (int i = startRow; i <= totalRows; i++, ci++) {

                cj = 0;
                int totalCols = ExcelWSheet.getRow(i).getPhysicalNumberOfCells();
                tabArray = new String[totalRows][totalCols];
                List<String> dataList = new LinkedList<String>();

                for (int j = startCol; j < totalCols; j++, cj++) {

                    String cellData = getCellData(i, j);
                    tabArray[ci][cj] = cellData;
                    dataList.add(cellData);

                }
                testDataMap.put(dataList.get(0), dataList.get(1));

            }

        } catch (FileNotFoundException e) {

            System.out.println("Could not read the Excel sheet");

            e.printStackTrace();

        } catch (IOException e) {

            System.out.println("Could not read the Excel sheet");

            e.printStackTrace();

        }

        return testDataMap;

    }

    public static String getCellData(int RowNum, int ColNum) throws Exception {

        try {
            String cellData = null;

            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            int celltype = Cell.getCellType();

            int dataType = Cell.getCellType();

            if (dataType == 3) {

                return "";

            } else {

                switch (celltype) {
                    case 0:
                        DataFormatter formatter = new DataFormatter();
                        cellData = formatter.formatCellValue(Cell);
                        break;
                    case 1:
                        cellData = Cell.getStringCellValue();
                        break;
                }

                return cellData.toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

            throw (e);

        }

    }

    @DataProvider(name = "EmployeeId")
    public static Object[][] getEmployeeId() throws Exception {
        Object[][] response = new Object[1][1];
        HashMap<String, String> obj = null;
        try {
            obj = getTableArray("src/main/resources/testData/Emp_testData.xlsx", "EmployeeSheet");
            response[0][0] = obj;
        } catch (FileNotFoundException e) {
            System.out.println("File Not found for test data");
        }
        return response;
    }
}

