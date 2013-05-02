package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public class CabimentosDetailsReportType extends MovementsDetailsReportType {

    public CabimentosDetailsReportType(Map<String, String> args, Project project) {
        super(args, project);
        setTableSummaryReport(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "FILHO_VALOR"));
    }

    @Override
    public String getQuery() {
        // TODO Auto-generated method stub
        return "select distinct * from \"V_MOV_CABIMENTOS\" where \"PAI_IDPROJ\"='" + getProjectCode() + "' AND \"PAI_IDMOV\"='"
                + getParentId() + "'";
    }

    @Override
    public String getLabel() {
        return ReportType.CABIMENTOS_DETAILS_STRING;
    }

}
