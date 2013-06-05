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
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class UnitOverheadHeaderComponent extends CustomComponent implements Reportable {
    Unit unit;

    public UnitOverheadHeaderComponent(Unit unit, String reportType) {
        this.unit = unit;
        VerticalLayout outerLayout = new VerticalLayout();
        outerLayout.addComponent(new Label("<b><h3>" + reportType + "</h3></b>", Label.CONTENT_XHTML));
        GridLayout layout = new GridLayout(2, 3);

        layout.setSpacing(true);
        Label unitLabel, costCenterLabel, dateLabel;
        unitLabel =
                new Label(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                        "financialprojectsreports.unitOverheadHeader.label.unit"));
        costCenterLabel =
                new Label(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                        "financialprojectsreports.unitOverheadHeader.label.costCenter"));
        dateLabel =
                new Label(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                        "financialprojectsreports.unitOverheadHeader.label.date"));

        unitLabel.setStyleName("bold-label");
        costCenterLabel.setStyleName("bold-label");
        dateLabel.setStyleName("bold-label");

        layout.addComponent(unitLabel);
        layout.addComponent(new Label(unit.getName()));
        layout.addComponent(costCenterLabel);
        layout.addComponent(new Label(unit.getCostCenterUnit().getCostCenter()));
        layout.addComponent(dateLabel);
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
                "financialprojectsreports.unitOverheadHeader.label.unit"));
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(unit.getName());

        row = sheet.createRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                "financialprojectsreports.unitOverheadHeader.label.costCenter"));
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(unit.getCostCenterUnit().getCostCenter());

        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                "financialprojectsreports.unitOverheadHeader.label.date"));
        cell.setCellStyle(style);
        cell = row.createCell(1);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
        cell.setCellValue(fmt.print(new DateTime()));

    }
}
