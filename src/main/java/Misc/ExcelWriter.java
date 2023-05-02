package Misc;

import Config.Config;
import Events.MessageListener;
import Handlers.Drop;
import Config.FileLocations;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcelWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);
    public static void writeExcel(List<Drop> drops) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            LOGGER.info("Starting writeExcel");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Drops");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Chance");
            headerRow.createCell(1).setCellValue("Rarity");
            headerRow.createCell(2).setCellValue("Location");
            headerRow.createCell(3).setCellValue("Type");

            // Create data rows
            int rowNum = 1;
            for (Drop drop : drops) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(drop.getChance());
                row.createCell(1).setCellValue(drop.getRarity());
                row.createCell(2).setCellValue(drop.getLocation());
                row.createCell(3).setCellValue(drop.getType());
            }

            if (drops == null || drops.isEmpty()) {
                LOGGER.error("No drops to write to Excel");
                return;
            }

            // Autosize columns
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream outputStream = new FileOutputStream(FileLocations.DROP_PATH)) {
                workbook.write(outputStream);
                LOGGER.info("Excel file written to: {}", FileLocations.DROP_PATH);
            } catch (IOException e) {
                LOGGER.error("Error writing Excel file", e);
            }
        });

        executor.shutdown();

    }

}
