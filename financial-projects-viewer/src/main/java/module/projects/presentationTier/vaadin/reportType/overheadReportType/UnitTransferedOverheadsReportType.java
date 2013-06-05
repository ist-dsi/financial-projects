package module.projects.presentationTier.vaadin.reportType.overheadReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.UnitOverheadsReportType;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public class UnitTransferedOverheadsReportType extends UnitOverheadsReportType {
    String costCenterCoordinator;
    ReportViewerComponent reportViewer;
    TableSummaryComponent summary;

    public UnitTransferedOverheadsReportType(Map<String, String> args) {
        super(args);
        Panel panel = new Panel();
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        setColumnNames(reportViewer.getTable());
        summary = new TableSummaryComponent(reportViewer.getTable(), getLabel(), "VALOR");
        panel.addComponent(reportViewer);

        panel.getContent().setSizeUndefined();
        addComponent(panel);
        addComponent(summary);
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        getOverheadHeader().write(sheet, headersFont);
        reportViewer.write(sheet, headersFont);
        summary.write(sheet, headersFont);
    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.transferedOverheads");
    }

    @Override
    public String getQuery() {

        return "select \"UE\", \"IDMOV\", \"DATE_AUTOR\", \"TIPO\", \"DESCRICAO\", \"VALOR\" from V_OVH_TRANSFERIDOS where CC_COORD='"
                + getCostCenterCoordinator() + "' order by \"DATE_AUTOR\", \"UE\", \"IDMOV\"";

    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setColumnNames(Table table) {
        table.setColumnHeader("UE", getMessage("financialprojectsreports.transferedOverheads.column.exploringUnit"));
        table.setColumnHeader("IDMOV", getMessage("financialprojectsreports.transferedOverheads.column.id"));
        table.setColumnHeader("DATE_AUTOR", getMessage("financialprojectsreports.transferedOverheads.column.date"));
        table.setColumnHeader("TIPO", getMessage("financialprojectsreports.transferedOverheads.column.type"));
        table.setColumnHeader("DESCRICAO", getMessage("financialprojectsreports.transferedOverheads.column.description"));
        table.setColumnHeader("VALOR", getMessage("financialprojectsreports.transferedOverheads.column.value"));
    }
}
