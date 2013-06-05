package module.projects.presentationTier.vaadin.reportType.overheadReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.UnitOverheadsReportType;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public class UnitOverheadsSummaryReportType extends UnitOverheadsReportType {
    String costCenterCoordinator;
    ReportViewerComponent reportViewer;
    TableSummaryComponent summary;

    public UnitOverheadsSummaryReportType(Map<String, String> args) {
        super(args);
        Panel panel = new Panel();
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        setColumnNames(reportViewer.getTable());
        summary =
                new TableSummaryComponent(reportViewer.getTable(), getLabel(), "REC_OG", "OVH_OG", "REC_OA", "OVH_OA", "REC_OO",
                        "OVH_OO", "REC_OE", "OVH_OE", "TOTAL_OVH", "OVH_TRANSF", "SALDO");

        panel.addComponent(reportViewer);
        panel.getContent().setSizeUndefined();
        addComponent(panel);
        addComponent(summary);
        Label warning = new Label(getMessage("financialprojectsreports.overheadsSummary.warning"));
        warning.setStyleName("bold-label");
        addComponent(warning);
        addComponent(new Label(getMessage("financialprojectsreports.overheadsSummary.warning.OG")));
        addComponent(new Label(getMessage("financialprojectsreports.overheadsSummary.warning.OA")));
        addComponent(new Label(getMessage("financialprojectsreports.overheadsSummary.warning.OO")));
        addComponent(new Label(getMessage("financialprojectsreports.overheadsSummary.warning.OE")));
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        getOverheadHeader().write(sheet, headersFont);
        reportViewer.write(sheet, headersFont);
        summary.write(sheet, headersFont);

        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFont(headersFont);
        int rowNum = sheet.getLastRowNum() + 2;
        HSSFCell cell = sheet.createRow(rowNum++).createCell(0);
        cell.setCellStyle(style);

        cell.setCellValue(getMessage("financialprojectsreports.overheadsSummary.warning"));
        cell = sheet.createRow(rowNum++).createCell(0);
        cell.setCellValue(getMessage("financialprojectsreports.overheadsSummary.warning.OG"));
        cell = sheet.createRow(rowNum++).createCell(0);
        cell.setCellValue(getMessage("financialprojectsreports.overheadsSummary.warning.OA"));
        cell = sheet.createRow(rowNum++).createCell(0);
        cell.setCellValue(getMessage("financialprojectsreports.overheadsSummary.warning.OO"));
        cell = sheet.createRow(rowNum++).createCell(0);
        cell.setCellValue(getMessage("financialprojectsreports.overheadsSummary.warning.OE"));
    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.overheadsSummary");
    }

    @Override
    public String getQuery() {
        return "select \"ANO\", \"UE\", \"COST_CENTER\", \"REC_OG\", \"OVH_OG\", \"REC_OA\", "
                + "\"OVH_OA\",\"REC_OO\", \"OVH_OO\", \"REC_OE\", \"OVH_OE\", \"TOTAL_OVH\", \"OVH_TRANSF\", \"SALDO\" from V_OVH_RESUMO where CC_COORD='"
                + getCostCenterCoordinator() + "' order by \"ANO\", \"UE\"";

    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setColumnNames(Table table) {
        table.setColumnHeader("ANO", getMessage("financialprojectsreports.overheadsSummary.column.year"));
        table.setColumnHeader("UE", getMessage("financialprojectsreports.overheadsSummary.column.exploringUnit"));
        table.setColumnHeader("COST_CENTER", getMessage("financialprojectsreports.overheadsSummary.column.costCenter"));
        table.setColumnHeader("REC_OG", getMessage("financialprojectsreports.overheadsSummary.column.ogRevenue"));
        table.setColumnHeader("OVH_OG", getMessage("financialprojectsreports.overheadsSummary.column.ogOverhead"));
        table.setColumnHeader("REC_OA", getMessage("financialprojectsreports.overheadsSummary.column.oaRevenue"));
        table.setColumnHeader("OVH_OA", getMessage("financialprojectsreports.overheadsSummary.column.oaOverhead"));
        table.setColumnHeader("REC_OO", getMessage("financialprojectsreports.overheadsSummary.column.ooRevenue"));
        table.setColumnHeader("OVH_OO", getMessage("financialprojectsreports.overheadsSummary.column.ooOverhead"));
        table.setColumnHeader("REC_OE", getMessage("financialprojectsreports.overheadsSummary.column.oeRevenue"));
        table.setColumnHeader("OVH_OE", getMessage("financialprojectsreports.overheadsSummary.column.oeOverhead"));
        table.setColumnHeader("TOTAL_OVH", getMessage("financialprojectsreports.overheadsSummary.column.totalOverhead"));
        table.setColumnHeader("OVH_TRANSF", getMessage("financialprojectsreports.overheadsSummary.column.transferedOverhead"));
        table.setColumnHeader("SALDO", getMessage("financialprojectsreports.overheadsSummary.column.balance"));
    }

}
