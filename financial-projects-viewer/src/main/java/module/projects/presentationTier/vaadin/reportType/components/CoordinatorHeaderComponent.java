package module.projects.presentationTier.vaadin.reportType.components;

import module.projects.presentationTier.vaadin.Reportable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pt.ist.bennu.core.util.BundleUtil;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class CoordinatorHeaderComponent extends CustomComponent implements Reportable {
    String coordinatorName;

    public CoordinatorHeaderComponent(String coordinatorName, String reportType) {
        this.coordinatorName = coordinatorName;
        VerticalLayout outerLayout = new VerticalLayout();
        outerLayout.addComponent(new Label("<b><h3>" + reportType + "</h3></b>", Label.CONTENT_XHTML));
        GridLayout layout = new GridLayout(2, 2);
        layout.setSpacing(true);

        Label coord, date;
        coord =
                new Label(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                        "financialprojectsreports.coordinatorHeader.label.coordinator"));
        date =
                new Label(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                        "financialprojectsreports.coordinatorHeader.label.date"));
        coord.setStyleName("bold-label");
        date.setStyleName("bold-label");

        layout.setWidth("100%");

        layout.addComponent(coord);
        layout.addComponent(new Label(coordinatorName));
        layout.addComponent(date);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
        layout.addComponent(new Label(fmt.print(new DateTime())));
        outerLayout.addComponent(layout);
        setCompositionRoot(outerLayout);
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFont(headersFont);
        int rowNum = sheet.getLastRowNum() + 2;
        HSSFRow row = sheet.createRow(rowNum++);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                "financialprojectsreports.coordinatorHeader.label.coordinator"));
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(coordinatorName);
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                "financialprojectsreports.coordinatorHeader.label.date"));
        cell.setCellStyle(style);
        cell = row.createCell(1);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
        cell.setCellValue(fmt.print(new DateTime()));

    }
}
