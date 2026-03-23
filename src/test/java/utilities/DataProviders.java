package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders 
{
    // DataProvider name must be referenced in the Test Case
	
    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException 
    {
        // Path to the Excel file
        String path = ".\\testData\\OpenCart_Login.xlsx"; 
        
        ExcelUtility xlutil = new ExcelUtility(path);
        
        // Use the exact sheet name from your Excel file
        String sheetName = "Login credentials";
        
        int totalRows = xlutil.getRowCount(sheetName); 
        int totalCols = xlutil.getCellCount(sheetName, 1);
        
        // Create a 2D array to store the data
        String logindata[][] = new String[totalRows][totalCols];
        
        // Read data from Excel and store in 2D array (starting from row 1 to skip header)
        for (int i = 1; i <= totalRows; i++) 
        {
            for (int j = 0; j < totalCols; j++) 
            {
                logindata[i - 1][j] = xlutil.getCellData(sheetName, i, j); 
            }
        }
        
        return logindata; 
    }
}