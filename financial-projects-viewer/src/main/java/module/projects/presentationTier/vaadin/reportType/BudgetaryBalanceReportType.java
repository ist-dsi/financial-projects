package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.Label;

public class BudgetaryBalanceReportType extends ReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public BudgetaryBalanceReportType(Map<String, String> args, Project project) {
        super(args, project);
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);
        addComponent(new Label(getMessage("financialprojectsreports.balanceWarning")));
    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        return reportViewer;
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        reportViewer.write(sheet, headersFont);
        sheet.createRow(sheet.getLastRowNum() + 2).createCell(0)
                .setCellValue(getMessage("financialprojectsreports.balanceWarning"));
    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.budgetaryBalance");
    }

    @Override
    public String getQuery() {
        return "select \"RUBRICA\", \"DESCRICAORUBRICA\", \"ORÇAMENTADO\", \"EXECUTADO\", \"SALDO\" from V_SALDO_PROJECTO where PROJECTO='"
                + getProjectCode() + "'";
    }
}
