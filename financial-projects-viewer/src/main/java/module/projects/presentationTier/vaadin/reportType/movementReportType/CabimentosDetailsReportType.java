package module.projects.presentationTier.vaadin.reportType.movementReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.MovementsDetailsReportType;
import module.projects.presentationTier.vaadin.reportType.ReportType;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.Table;

public class CabimentosDetailsReportType extends MovementsDetailsReportType {

    public CabimentosDetailsReportType(Map<String, String> args, Project project) {
        super(args, project);
        setTableSummaryReport(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "FILHO_VALOR"));
    }

    @Override
    public String getQuery() {
        // TODO Auto-generated method stub
        return "select distinct \"FILHO_IDMOV\", \"FILHO_IDRUB\", \"FILHO_TIPO\", TO_CHAR(\"FILHO_DATA\",'YYYY-MM-DD') as DATA, \"FILHO_DESCRICAO\", \"FILHO_VALOR\", \"FILHO_IVA\" from \"V_MOV_CABIMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode() + "' AND \"PAI_IDMOV\"='" + getParentId() + "'";

    }

    @Override
    public String getLabel() {
        return ReportType.CABIMENTOS_DETAILS_LABEL;
    }

    @Override
    public void setColumnNames(Table table) {
        table.setColumnHeader("FILHO_IDMOV", getMessage("financialprojectsreports.column.id"));
        table.setColumnHeader("FILHO_IDRUB", getMessage("financialprojectsreports.column.rubric"));
        table.setColumnHeader("FILHO_TIPO", getMessage("financialprojectsreports.column.type"));
        table.setColumnHeader("DATA", getMessage("financialprojectsreports.column.date"));
        table.setColumnHeader("FILHO_DESCRICAO", getMessage("financialprojectsreports.column.description"));
        table.setColumnHeader("FILHO_VALOR", getMessage("financialprojectsreports.column.value"));
        table.setColumnHeader("FILHO_IVA", getMessage("financialprojectsreports.column.iva"));

    }
}
