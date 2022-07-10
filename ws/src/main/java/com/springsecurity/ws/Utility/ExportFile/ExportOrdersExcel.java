package com.springsecurity.ws.Utility.ExportFile;

import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.Utility.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.time.temporal.ChronoUnit;

@Slf4j
public class ExportOrdersExcel extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        // define excel file name to be exported
        response.addHeader("Content-Disposition", "attachment;fileName=Orders_Initial.xlsx");

        // Hna Kay9ra Data Mn Controllers
        @SuppressWarnings("unchecked")
        List<OrdersResponse> list = (List<OrdersResponse>) model.get("orders");

        // create one sheet
        Sheet sheet = workbook.createSheet("Orders_Initial");
        // STYLE CELL
        CellStyle cellStyle4 = workbook.createCellStyle();
        cellStyle4.setAlignment(HorizontalAlignment.CENTER);
        // create row0 as a header
        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("Numéro de Commande");
        row0.createCell(1).setCellValue("                   Vehicule                ");
        row0.createCell(2).setCellValue("                Nom De Client                  ");
        row0.createCell(3).setCellValue("   Date De Commande   ");
        row0.createCell(4).setCellValue("   Date From   ");
        row0.createCell(5).setCellValue("   Date To   ");
        row0.createCell(6).setCellValue("   Téléphone   ");
        row0.createCell(7).setCellValue("       Ville         ");
        row0.createCell(8).setCellValue("    Prix Par Nuit    ");
        row0.createCell(9).setCellValue("    Nombre De Nuit    ");
        row0.createCell(10).setCellValue("   Total  ");

        // FOR AUTO SIZE
        for (int j = 0; j < row0.getPhysicalNumberOfCells(); j++) {
            sheet.autoSizeColumn((short) j);
        }

        int rowNum = 1;
        for(OrdersResponse cmdi : list) {
            Row row = sheet.createRow(rowNum++);
            sheet.autoSizeColumn(list.size());
            row.createCell(0).setCellValue(cmdi.getIdbOrder());
            row.createCell(1).setCellValue(cmdi.getVehicule().getNomVehicule());
            row.createCell(2).setCellValue(cmdi.getFn() +" "+cmdi.getLn());
            row.createCell(3).setCellValue(formatter.format(cmdi.getDtOrder()));
            row.createCell(4).setCellValue(formatter.format(cmdi.getDtfrom()));
            row.createCell(5).setCellValue(formatter.format(cmdi.getDtto()));
            row.createCell(6).setCellValue(cmdi.getTel());
            row.createCell(7).setCellValue(cmdi.getCity().getCityName());
            row.createCell(8).setCellValue(cmdi.getVehicule().getPn() +" MAD");
            row.createCell(9).setCellValue(ChronoUnit.DAYS.between(cmdi.getDtfrom().toInstant(),cmdi.getDtto().toInstant()));
            row.createCell(10).setCellValue((ChronoUnit.DAYS.between(cmdi.getDtfrom().toInstant(),cmdi.getDtto().toInstant())*(cmdi.getVehicule().getPn()))+" MAD ");

        };
    }
}
