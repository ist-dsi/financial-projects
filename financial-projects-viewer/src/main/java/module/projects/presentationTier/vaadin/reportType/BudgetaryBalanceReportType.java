package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

public class BudgetaryBalanceReportType extends ReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public BudgetaryBalanceReportType(Map<String, String> args, Project project) {
        super(args, project);
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);
    }

    @Override
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
        return PROJECT_BUDGETARY_BALANCE_LABEL;
    }

    @Override
    public String getQuery() {
        return "select \"RUBRICA\", \"DESCRICAORUBRICA\", \"ORÇAMENTADO\", \"EXECUTADO\", \"SALDO\" from V_SALDO_PROJECTO where PROJECTO='"
                + getProjectCode() + "'";
    }
}
