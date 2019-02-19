package test;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.function.Function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GlobalDefinition {
    String wait;
    /* Definition of new function for wait
    public WebElement flwait(WebElement search){
        WebElement wait = null;

        Wait<FluentWait> flwait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);


        try {
            wait = new FluentWait<WebDriver>(driver).ignoring(NoSuchElementException.class).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(2)).until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return search;
                }
            });
            System.out.println(wait);
        } catch (org.openqa.selenium.NoSuchElementException e){
            System.out.println("Catching is working");
        }

        /*
        foo = flwait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return search;
            }
        });

        //System.out.println(foo);
        System.out.println(foo);
        return wait;
    }

    */ //Definition of new function for wait

    //private static XSSFSheet ExcelWSheet;
    //private static XSSFWorkbook ExcelWBook;
    //private static XSSFCell Cell;
    //private static XSSFRow Row;

    /* Implementation of excel sheets
    public static void main(String [] args){
        System.out.println("Could not read the Excel sheet");
        ExcelTest();
        String FilePath = "/home/testautomation/AddUserTCs.xlsx";
        String SheetName = "Sheet1";

    }

    public static void  ExcelTest()  {

        String FilePath = "/home/testautomation/AddUserTCs.xlsx";
        String SheetName = "Sheet1";
        String[][] tabArray = null;
        try {
            File excelFile = new File(FilePath);
            FileInputStream inputStream = new FileInputStream(excelFile);
            // Access the required test data sheet
            ExcelWBook = new XSSFWorkbook(inputStream);
            ExcelWSheet = ExcelWBook.getSheetAt(0);
            int startRow = 1;
            int startCol = 0;
            int ci, cj;
            int totalRows = ExcelWSheet.getLastRowNum();

            System.out.println(totalRows);

            // you can write a function as well to get Column count
            int totalCols = 8;
            tabArray = new String[totalRows][totalCols];
            ci = 0;
            for (int i = startRow; i <= totalRows; i++, ci++) {
                cj = 0;
                for (int j = startCol; j < totalCols; j++, cj++) {
                    Cell = ExcelWSheet.getRow(i).getCell(j);
                    tabArray[ci][cj] = Cell.getStringCellValue();
                    System.out.println(tabArray[ci][cj]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }

    }
    */ //Implementation of excel sheets

}
