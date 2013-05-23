package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.ui.Table;

public class RevenueReportType extends ProjectReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public RevenueReportType(Map<String, String> args) {
        super(args);
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);
        tableSummary = new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "Valor");
        addComponent(tableSummary);
        setColumnNames(reportViewer.getTable());
    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        return reportViewer;
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        reportViewer.write(sheet, headersFont);
        tableSummary.write(sheet, headersFont);

        sheet.createRow(sheet.getLastRowNum() + 2).createCell(0)
                .setCellValue(getMessage("financialprojectsreports.expensescalculationwarning"));
    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.revenue");
    }

    @Override
    public String getQuery() {
        return "select distinct \"idMov\", \"Ent. Financ.\", \"Rubrica\", \"Data\", \"Descrição\", \"Valor\" from V_MOVRECEUR where PROJECTCODE='"
                + getProjectCode() + "' order by \"Data\", \"idMov\"";
    }

    public void setColumnNames(Table table) {
        table.setColumnHeader("idMov", getMessage("financialprojectsreports.revenue.column.id"));
        table.setColumnHeader("Ent. Financ.", getMessage("financialprojectsreports.revenue.column.financialEntity"));
        table.setColumnHeader("Rubrica", getMessage("financialprojectsreports.revenue.column.rubric"));
        table.setColumnHeader("Data", getMessage("financialprojectsreports.revenue.column.date"));
        table.setColumnHeader("Descrição", getMessage("financialprojectsreports.revenue.column.description"));
        table.setColumnHeader("Valor", getMessage("financialprojectsreports.revenue.column.value"));
    }
}
