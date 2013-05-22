package module.projects.presentationTier.vaadin.reportType.movementReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.MovementsReportType;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.Table;

public class AdiantamentosReportType extends MovementsReportType {

    public AdiantamentosReportType(Map<String, String> args, Project project) {
        super(args, project);
        setTableSummaryReport(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "PAI_VALOR_TOTAL", "EXECUTED",
                "MISSING"));

    }

    @Override
    public String getQuery() {
        return "select distinct \"PAI_IDMOV\", \"PAI_IDRUB\", \"PAI_TIPO\", TO_CHAR(\"PAI_DATA\",'YYYY-MM-DD') as DATA, \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\", SUM(\"FILHO_VALOR\") + SUM(\"FILHO_IVA\") as EXECUTED, (\"PAI_VALOR_TOTAL\" - SUM(\"FILHO_VALOR\") - SUM(\"FILHO_IVA\")) as MISSING from \"V_MOV_ADIANTAMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode()
                + "' group by \"PAI_IDMOV\", \"PAI_IDRUB\", \"PAI_TIPO\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\"";

    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.adiantamentosListing");
    }

    @Override
    public void setColumnNames(Table table) {
        table.setColumnHeader("PAI_IDMOV", getMessage("financialprojectsreports.column.id"));
        table.setColumnHeader("PAI_IDRUB", getMessage("financialprojectsreports.column.rubric"));
        table.setColumnHeader("PAI_TIPO", getMessage("financialprojectsreports.column.type"));
        table.setColumnHeader("DATA", getMessage("financialprojectsreports.column.date"));
        table.setColumnHeader("PAI_DESCRICAO", getMessage("financialprojectsreports.column.description"));
        table.setColumnHeader("PAI_VALOR_TOTAL", getMessage("financialprojectsreports.column.value"));
        table.setColumnHeader("MISSING", getMessage("financialprojectsreports.column.missing"));
        table.setColumnHeader("EXECUTED", getMessage("financialprojectsreports.column.justified"));
    }

    @Override
    protected String getChildReportName() {
        return "adiantamentosDetailsReport";
    }

    @Override
    public String getTypeName() {
        return "Adiantamento";
    }

    @Override
    public String getChildTypeName() {
        return "Justificações";
    }
}
