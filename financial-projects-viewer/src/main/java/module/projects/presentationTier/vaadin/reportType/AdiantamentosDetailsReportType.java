package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public class AdiantamentosDetailsReportType extends ReportType {

    String paiIDMOV;

    public AdiantamentosDetailsReportType(Map<String, String> args, Project project) {
        // TODO Auto-generated constructor stub
        super(args, project);
        paiIDMOV = args.get("PAI_IDMOV");
        addComponent(new ReportViewerComponent(getQuery(), getCustomFormatter()));
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

    @Override
    public void write(HSSFSheet sheet) {
        // TODO Auto-generated method stub

    }
}
