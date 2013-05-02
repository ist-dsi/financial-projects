package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public abstract class MovementsDetailsReportType extends ReportType {
    String parentID;
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public MovementsDetailsReportType(Map<String, String> args, Project project) {
        // TODO Auto-generated constructor stub
        super(args, project);
        parentID = args.get("PAI_IDMOV");
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);

        //TODO change this to the right columns

    }

    protected String getParentId() {
        return parentID;
    }

    protected void setTableSummaryReport(TableSummaryComponent table) {
        tableSummary = table;
        addComponent(tableSummary);
    }

    protected ReportViewerComponent getReportViewer() {
        return reportViewer;
    }

    @Override
    public void write(HSSFSheet sheet) {

        reportViewer.write(sheet);
        tableSummary.write(sheet);
    }

}
