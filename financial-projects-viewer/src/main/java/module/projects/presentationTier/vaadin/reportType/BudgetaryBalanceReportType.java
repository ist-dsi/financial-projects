package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class BudgetaryBalanceReportType extends ProjectReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public BudgetaryBalanceReportType(Map<String, String> args) {
        super(args);

        ReportViewerComponent arePartnersView =
                new ReportViewerComponent("select controloorcammemb from V_FICHA_ABERTURA where PROJECTO='" + getProjectCode()
                        + "'", getCustomFormatter());

        ReportViewerComponent consorciumMemberBudgetViewer =
                new ReportViewerComponent(
                        "select \"RUBRICA\", \"DESCRICAORUBRICA\", \"ORÇAMENTADO\", \"EXECUTADO\", \"SALDO\" from V_SALDO_PROJECTO where PROJECTO='"
                                + getProjectCode() + "'", getCustomFormatter());
        consorciumMemberBudgetViewer.getTable().setPageLength(0);
        setColumnNames(consorciumMemberBudgetViewer.getTable());
        addComponent(consorciumMemberBudgetViewer);
        addComponent(new Label(getMessage("financialprojectsreports.balanceWarning")));

        Table arePartnersTable = arePartnersView.getTable();
        boolean areTherePartners = false;

        for (Object itemId : arePartnersTable.getItemIds()) {
            String partner = arePartnersTable.getItem(itemId).getItemProperty("CONTROLOORCAMMEMB").toString();
            if (partner.equals("Y")) {
                areTherePartners = true;
                break;
            }
        }

        if (areTherePartners) {
            ReportViewerComponent consorciumBudgetViewer =
                    new ReportViewerComponent(
                            "select \"INSTITUICAO\", \"DESCRICAOINSTITUICAO\", \"TIPO\" from V_MEMBROS_CONSORCIO where PROJECTO='"
                                    + getProjectCode() + "'", getCustomFormatter());
            consorciumBudgetViewer.getTable().setPageLength(0);
            setColumnNames(consorciumBudgetViewer.getTable());
            addComponent(consorciumBudgetViewer);

            for (Object itemId : consorciumBudgetViewer.getTable().getItemIds()) {
                String member = consorciumBudgetViewer.getTable().getItem(itemId).getItemProperty("INSTITUICAO").toString();
                String memberName =
                        consorciumBudgetViewer.getTable().getItem(itemId).getItemProperty("DESCRICAOINSTITUICAO").toString();
                addComponent(new Label("<b>"
                        + getMessage("financialprojectsreports.budgetaryBalanceReport.label.perMembersBudget") + " " + member
                        + " (" + memberName + ")" + "</b>", Label.CONTENT_XHTML));
                consorciumMemberBudgetViewer =
                        new ReportViewerComponent(
                                "select RUBRICA, DESCRICAORUBRICA, ORÇAMENTADO, EXECUTADO, SALDO from V_SALDO_MEMBRO where PROJECTO='"
                                        + getProjectCode() + "' AND MEMBRO='" + member + "'", getCustomFormatter());
                consorciumMemberBudgetViewer.getTable().setPageLength(0);
                setColumnNames(consorciumMemberBudgetViewer.getTable());
                addComponent(consorciumMemberBudgetViewer);
            }
        }
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

    public void setColumnNames(Table table) {
        table.setColumnHeader("RUBRICA", getMessage("financialprojectsreports.budgetaryBalance.column.rubric"));
        table.setColumnHeader("DESCRICAORUBRICA", getMessage("financialprojectsreports.budgetaryBalance.column.description"));
        table.setColumnHeader("ORÇAMENTADO", getMessage("financialprojectsreports.budgetaryBalance.column.budget"));
        table.setColumnHeader("EXECUTADO", getMessage("financialprojectsreports.budgetaryBalance.column.executed"));
        table.setColumnHeader("SALDO", getMessage("financialprojectsreports.budgetaryBalance.column.balance"));
        table.setColumnHeader("DESCRICAOINSTITUICAO", getMessage("financialprojectsreports.budgetaryBalance.column.description"));
    }

    @Override
    public String getQuery() {
        // TODO Auto-generated method stub
        return null;
    }
}
