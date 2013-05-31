package module.projects.presentationTier.vaadin.reportType.overheadReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.UnitOverheadsReportType;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public class UnitGeneratedOverheadsReportType extends UnitOverheadsReportType {
    String costCenterCoordinator;
    ReportViewerComponent reportViewer;
    TableSummaryComponent summary;

    public UnitGeneratedOverheadsReportType(Map<String, String> args) {
        super(args);
        Panel panel = new Panel();
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        summary = new TableSummaryComponent(reportViewer.getTable(), getLabel(), "VALOR_RECEITA", "PCT_OVH", "VALOR_OVH");

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
        return getMessage("financialprojectsreports.reportTitle.generatedOverheads");
    }

    @Override
    public String getQuery() {
        return "select \"UNID_EXPL\", \"ID_PROJ\", \"ACRONIMO\", \"ID_COORD\", \"NOME\", \"TIPO_OVH\", \"DATE_AUTOR\", \"DESCRICAO\", \"VALOR_RECEITA\", \"PCT_OVH\", \"VALOR_OVH\" from V_OVH_GERADOS where cc_coord='"
                + getCostCenterCoordinator() + "' order by \"DATE_AUTOR\", \"UNID_EXPL\", \"ID_PROJ\"";

    }

    public void setColumnNames(Table table) {
        table.setColumnHeader("UNID_EXPL", getMessage("financialprojectsreports.generatedOverheads.column.exploringUnit"));
        table.setColumnHeader("ID_PROJ", getMessage("financialprojectsreports.generatedOverheads.column.projectId"));
        table.setColumnHeader("ACRONIMO", getMessage("financialprojectsreports.generatedOverheads.column.acronym"));
        table.setColumnHeader("ID_COORD", getMessage("financialprojectsreports.generatedOverheads.column.coordinatorID"));
        table.setColumnHeader("NOME", getMessage("financialprojectsreports.generatedOverheads.column.name"));
        table.setColumnHeader("TIPO_OVH", getMessage("financialprojectsreports.generatedOverheads.column.overheadType"));
        table.setColumnHeader("DATE_AUTOR", getMessage("financialprojectsreports.generatedOverheads.column.authorDate"));
        table.setColumnHeader("DESCRICAO", getMessage("financialprojectsreports.generatedOverheads.column.description"));
        table.setColumnHeader("VALOR_RECEITA", getMessage("financialprojectsreports.generatedOverheads.column.revenueValue"));
        table.setColumnHeader("PCT_OVH", getMessage("financialprojectsreports.generatedOverheads.column.overHeadPercentage"));
        table.setColumnHeader("VALOR_OVH", getMessage("financialprojectsreports.generatedOverheads.column.overHeadValue"));

    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }

}
