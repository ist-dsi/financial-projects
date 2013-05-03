package module.projects.presentationTier.vaadin.reportType.movementReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.MovementsReportType;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public class CabimentosReportType extends MovementsReportType {

    public CabimentosReportType(Map<String, String> args, Project project) {
        super(args, project);
        setTableSummaryReport(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "PAI_VALOR_TOTAL",
                "TOTAL_EXECUCOES", "EXECUCOES_EM_FALTA"));

    }

    @Override
    public String getLabel() {
        return CABIMENTOS_LABEL;
    }

    @Override
    public String getQuery() {
        return "select \"PAI_IDMOV\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\",SUM(FILHO_VALOR) + SUM(FILHO_IVA) as TOTAL_EXECUCOES, \"PAI_VALOR_TOTAL\" - SUM(FILHO_VALOR) - SUM(FILHO_IVA) EXECUCOES_EM_FALTA from \"V_MOV_CABIMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode()
                + "' group by \"PAI_IDMOV\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\"";
    }

}
