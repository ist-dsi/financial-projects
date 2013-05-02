package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public class AdiantamentosReportType extends MovementsReportType {

    public AdiantamentosReportType(Map<String, String> args, Project project) {
        super(args, project);
        setTableSummaryReport(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "PAI_VALOR_TOTAL"));

    }

    @Override
    public String getQuery() {
        return "select distinct \"PAI_IDMOV\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_TIPO\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\" from \"V_MOV_ADIANTAMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode() + "' order by \"PAI_IDMOV\"";
    }

    @Override
    public String getLabel() {
        return ADIANTAMENTOS_LABEL;
    }
}
