package module.projects.presentationTier.vaadin.reportType;

import java.util.HashMap;
import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public abstract class MovementsReportType extends ProjectReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public MovementsReportType(Map<String, String> args) {
        super(args);
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        setColumnNames(reportViewer.getTable());
        addComponent(reportViewer);
    }

    @Override
    public CustomTableFormatter getCustomFormatter() {
        return new CustomTableFormatter() {
            @Override
            public void format(Table table) {
                table.addGeneratedColumn(getMessage("financialprojectsreports.movements.column.details"), new ColumnGenerator() {
                    @Override
                    public Object generateCell(Table source, Object itemId, Object columnId) {
                        Property paiIDMOV = source.getItem(itemId).getItemProperty("PAI_IDMOV");
                        Link detailsLink =
                                new Link(getMessage("financialprojectsreports.movements.column.details"), new ExternalResource(
                                        "#projectsService?reportType=" + getChildReportName() + "&unit=" + getProjectID()
                                                + "&PAI_IDMOV=" + paiIDMOV));
                        //line.addItemProperty(columnId, new ObjectProperty<Link>(detailsLink));
                        return detailsLink;
                    }
                });
            }
        };

    }

    abstract protected String getChildReportName();

    @Override
    protected ReportViewerComponent getReportViewer() {
        return reportViewer;
    }

    protected void setTableSummaryReport(TableSummaryComponent table) {
        tableSummary = table;
        addComponent(tableSummary);
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        reportViewer.write(sheet, headersFont);
        tableSummary.write(sheet, headersFont);

        HashMap<String, String> fakeArguments = getArgs();
        fakeArguments.put("reportType", "cabimentosDetailsReport");

        Table t = reportViewer.getTable();

        for (Object itemId : t.getItemIds()) {
            Item item = t.getItem(itemId);
            String parentID = item.getItemProperty("PAI_IDMOV").getValue().toString();

            int rowNum = sheet.getLastRowNum() + 2;
            HSSFRow row = sheet.createRow(rowNum++);
            HSSFCell cell = row.createCell(0);
            HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
            style.setFont(headersFont);
            cell.setCellStyle(style);
            cell.setCellValue(getTypeName() + " Nº" + parentID);

            rowNum = reportViewer.writeHeader(sheet, headersFont);
            row = sheet.createRow(rowNum++);
            int i = 0;
            for (Object propertyId : item.getItemPropertyIds()) {
                Property p = item.getItemProperty(propertyId);
                cell = row.createCell(i++);
                cell.setCellValue(p.getValue().toString());
            }

            rowNum++;
            cell = sheet.createRow(rowNum++).createCell(0);
            cell.setCellValue(getChildTypeName());
            cell.setCellStyle(style);

            fakeArguments.put("PAI_IDMOV", parentID);
            ReportType subReport = ReportType.getReportFromType(getChildReportName(), fakeArguments);
            subReport.write(sheet, headersFont);
        }

        sheet.createRow(sheet.getLastRowNum() + 2).createCell(0)
                .setCellValue(getMessage("financialprojectsreports.expensescalculationwarning"));
    }

    @Override
    public TableSummaryComponent getSummary() {
        return tableSummary;
    }

    public abstract void setColumnNames(Table table);

    public abstract String getTypeName();

    public abstract String getChildTypeName();

}
