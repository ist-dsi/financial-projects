package module.projects.presentationTier.vaadin.reportType.movementReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.MovementsDetailsReportType;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public class AdiantamentosDetailsReportType extends MovementsDetailsReportType {

    String paiIDMOV;

    public AdiantamentosDetailsReportType(Map<String, String> args, Project project) {
        // TODO Auto-generated constructor stub
        super(args, project);
        setTableSummaryReport(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "FILHO_VALOR"));
    }

    @Override
    public String getQuery() {
        // TODO Auto-generated method stub
        return "select distinct \"FILHO_IDMOV\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_TIPO\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\" from \"V_MOV_ADIANTAMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode() + "' AND \"PAI_IDMOV\"='" + paiIDMOV + "'";
    }

    @Override
    public String getLabel() {
        return CABIMENTOS_DETAILS_STRING;
    }
}
