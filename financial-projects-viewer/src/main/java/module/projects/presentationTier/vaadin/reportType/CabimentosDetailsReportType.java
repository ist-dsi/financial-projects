package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public class CabimentosDetailsReportType extends ReportType {
    String paiIDMOV;
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public CabimentosDetailsReportType(Map<String, String> args, Project project) {
        // TODO Auto-generated constructor stub
        super(args, project);
        paiIDMOV = args.get("PAI_IDMOV");
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);

        //TODO change this to the right columns
        //tableSummary = new TableSummaryComponent(reportViewer.getTable(), getLabel(), "PAI_VALOR_TOTAL");
    }

    @Override
    public String getQuery() {
        // TODO Auto-generated method stub
        return "select distinct \"FILHO_IDMOV\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_TIPO\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\" from \"V_MOV_CABIMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode() + "' AND \"PAI_IDMOV\"='" + paiIDMOV + "'";
    }

    @Override
    public String getLabel() {
        return ReportType.CABIMENTOS_DETAILS_STRING;
    }

    @Override
    public void write(HSSFSheet sheet) {
        reportViewer.write(sheet);
        //tableSummary.write(sheet);
    }

}
