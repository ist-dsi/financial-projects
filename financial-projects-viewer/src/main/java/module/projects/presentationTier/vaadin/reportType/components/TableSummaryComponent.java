package module.projects.presentationTier.vaadin.reportType.components;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import module.projects.presentationTier.vaadin.Reportable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.bennu.core.util.BundleUtil;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class TableSummaryComponent extends CustomComponent implements Reportable {
    List<String> columns;
    List<String> results;
    Layout subLayout;
    String reportType;

    public TableSummaryComponent(Table table, String reportType, String... collumns) {
        this.reportType = reportType;
        columns = new ArrayList<String>();
        results = new ArrayList<String>();

        Layout layout = new VerticalLayout();
        subLayout = new VerticalLayout();
        String summaryString =
                BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                        "financialprojectsreports.summaryComponent.label.summary");
        String totalString =
                BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                        "financialprojectsreports.summaryComponent.label.total");
        layout.addComponent(new Label("<b>" + summaryString + " " + reportType + "</b>", Label.CONTENT_XHTML));
        for (String collumn : collumns) {
            BigDecimal result = new BigDecimal(0);
            for (Object itemID : table.getItemIds()) {
                Object value = table.getItem(itemID).getItemProperty(collumn).getValue();
                result = result.add((BigDecimal) value);
            }

            subLayout.addComponent(new Label("<b>" + table.getColumnHeader(collumn).toString() + totalString + " :</b> " + result
                    + "€", Label.CONTENT_XHTML));
            columns.add(table.getColumnHeader(collumn).toString());
            results.add(result.toString());
        }
        layout.addComponent(subLayout);
        setCompositionRoot(layout);
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        int rowNum = sheet.getLastRowNum() + 2;
        HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFont(headersFont);
        HSSFCell cell = sheet.createRow(rowNum++).createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                "financialprojectsreports.summaryComponent.label.summary") + " " + reportType);

        for (int i = 0; i < columns.size(); i++) {
            HSSFRow row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(columns.get(i) + " Total: ");
            cell = row.createCell(1);
            cell.setCellValue(results.get(i) + "€");
        }
    }

    @Override
    public void setStyleName(String styleName) {
        subLayout.setStyleName(styleName);
    }
}
