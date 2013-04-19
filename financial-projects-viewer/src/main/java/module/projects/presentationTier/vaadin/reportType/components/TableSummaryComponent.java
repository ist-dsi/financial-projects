package module.projects.presentationTier.vaadin.reportType.components;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import module.projects.presentationTier.vaadin.Reportable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class TableSummaryComponent extends CustomComponent implements Reportable {
    List<String> columns;
    List<String> results;

    public TableSummaryComponent(Table table, String reportType, String... collumns) {
        columns = new ArrayList<String>();
        results = new ArrayList<String>();

        Layout layout = new VerticalLayout();
        layout.addComponent(new Label("Resumo de " + reportType));
        for (String collumn : collumns) {
            BigDecimal result = new BigDecimal(0);
            for (Object itemID : table.getItemIds()) {
                Object value = table.getItem(itemID).getItemProperty(collumn).getValue();
                result = result.add((BigDecimal) value);
            }
            layout.addComponent(new Label(collumn + " Total: " + result + "€"));
            columns.add(collumn);
            results.add(result.toString());
        }
        setCompositionRoot(layout);
    }

    @Override
    public void write(HSSFSheet sheet) {
        int rowNum = sheet.getLastRowNum() + 2;
        for (int i = 0; i < columns.size(); i++) {
            HSSFRow row = sheet.createRow(rowNum++);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(columns.get(i) + " Total: ");
            cell = row.createCell(1);
            cell.setCellValue(results.get(i) + "€");
        }
    }
}
