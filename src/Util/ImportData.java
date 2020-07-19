package Util;

import Models.Overview;
import Models.Product;
import Models.Stock;
import database.Database;
import java.io.*;
import java.sql.*;
import java.util.*;
import javafx.scene.control.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportData {
    
    String location = "C:\\Users\\ANKUR\\Documents\\NetBeansProjects\\ShoppingApp-master\\src\\Util\\Import.xlsx";
    FileInputStream input;
    Database database = new Database();
    public String userName = "";

    public void Import(String Scene) throws FileNotFoundException, IOException, ClassNotFoundException {
        
        DataFormatter format = new DataFormatter();
        this.input = new FileInputStream(new File(location));
        XSSFWorkbook wb = new XSSFWorkbook(input);
        XSSFSheet sheet = null;
        if(Scene.equals("Product"))
        {
            sheet = wb.getSheetAt(0);
        }
        if(Scene.equals("Overview"))
        {
            sheet = wb.getSheetAt(1);
        }
        if(Scene.equals("Stock"))
        {
            sheet = wb.getSheetAt(2);
        }
        
        Row row;
        
        for(int i = 1; i <= sheet.getLastRowNum(); i++)
        {
            row = sheet.getRow(i);

            if(Scene.equals("Product"))
            {
                Product prod = new Product();
                String productId = format.formatCellValue(row.getCell(0));
                prod.setProductId(productId);
                String productName = format.formatCellValue(row.getCell(1));
                prod.setProductName(productName);
                String overviewId = format.formatCellValue(row.getCell(2));
                prod.setOverviewId(overviewId);
                String productDesc = format.formatCellValue(row.getCell(3));
                prod.setProductDesc(productDesc);
                String status = format.formatCellValue(row.getCell(4));
                prod.setStatus(status);
                String actions = format.formatCellValue(row.getCell(5));
                prod.setActions(actions);
                String productPriority = format.formatCellValue(row.getCell(6));
                prod.setProductPriority(productPriority);

                database.saveNewProduct(prod, userName);
            }
            
            if(Scene.equals("Overview"))
            {
                Overview overview = new Overview();
                String overviewId = format.formatCellValue(row.getCell(0));
                overview.setOverviewId(overviewId);
                String Overview_Desc = format.formatCellValue(row.getCell(1));
                overview.setOverview_Desc(Overview_Desc);
                String StockId = format.formatCellValue(row.getCell(2));
                overview.setStockId(StockId);
                String Action = format.formatCellValue(row.getCell(3));
                overview.setAction(Action);
                String Status = format.formatCellValue(row.getCell(4));
                overview.setStatus(Status);
                String Traceability = format.formatCellValue(row.getCell(5));
                overview.setTraceability(Traceability);
                
                database.saveNewOverview(overview, userName);
            }
            
            if(Scene.equals("Stock"))
            {
                Stock stock = new Stock();
                String StockId = format.formatCellValue(row.getCell(0));
                stock.setStockId(StockId);
                String Stock_Desc = format.formatCellValue(row.getCell(1));
                stock.setStock_Desc(Stock_Desc);
                String productId = format.formatCellValue(row.getCell(2));
                stock.setProduct_Id(productId);
                String Steps = format.formatCellValue(row.getCell(3));
                stock.setSteps(Steps);
                String Actions = format.formatCellValue(row.getCell(4));
                stock.setActions(Actions);
                String Traceability = format.formatCellValue(row.getCell(4));
                stock.setTraceability(Traceability);
                
                database.saveNewStock(stock, userName);
            }
        }
    }
}
