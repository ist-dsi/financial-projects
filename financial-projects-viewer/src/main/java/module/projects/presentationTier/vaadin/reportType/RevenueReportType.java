package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public class RevenueReportType extends ReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public RevenueReportType(Map<String, String> args, Project project) {
        super(args, project);
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);
        addComponent(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "Valor"));

    }

    protected ReportViewerComponent getReportViewer() {
        return reportViewer;
    }

    @Override
    public void write(HSSFSheet sheet) {
        reportViewer.write(sheet);
        //tableSummary.write(sheet);
    }

    @Override
    public String getLabel() {
        return REVENUE_LABEL;
    }

    @Override
    public String getQuery() {
        return "select distinct * from V_MOVRECEUR where PROJECTCODE='" + getProjectCode() + "'";
    }
}
