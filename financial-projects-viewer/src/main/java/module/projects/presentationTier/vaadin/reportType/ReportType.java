/*
 * Copied from net.sourceforge.fenixedu.util.projectsManagement.ReportType, on 14/03/2013
 *
 */
package module.projects.presentationTier.vaadin.reportType;

import java.io.Serializable;
import java.util.Map;

import module.projects.presentationTier.vaadin.Reportable;
import module.projects.presentationTier.vaadin.reportType.components.ProjectHeaderComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Susana Fernandes
 * 
 */
public abstract class ReportType implements Reportable {

    public static final String REVENUE_STRING = "revenueReport";

    public static final String EXPENSES_STRING = "expensesReport";

    public static final String SUMMARY_STRING = "summaryReport";

    public static final String SUMMARY_PTE_STRING = "summary_PTE";

    public static final String SUMMARY_EUR_STRING = "summary_EUR";

    public static final String SUMMARY_ADIANTAMENTOS_STRING = "summaryAdiantamentosReport";

    public static final String SUMMARY_CABIMENTOS_STRING = "summaryCabimentosReport";

    public static final String GENERATED_OVERHEADS_STRING = "generatedOverheadsReport";

    public static final String TRANSFERED_OVERHEADS_STRING = "transferedOverheadsReport";

    public static final String OVERHEADS_SUMMARY_STRING = "overheadsSummaryReport";

    public static final String CABIMENTOS_STRING = "cabimentosReport";

    public static final String ADIANTAMENTOS_STRING = "adiantamentosReport";

    public static final String COMPLETE_EXPENSES_STRING = "completeExpensesReport";

    public static final String OPENING_PROJECT_FILE_STRING = "openingProjectFileReport";

    public static final String PROJECT_BUDGETARY_BALANCE_STRING = "projectBudgetaryBalanceReport";

    public static final String CABIMENTOS_DETAILS_STRING = "cabimentosDetailsReport";

    public static final String ADIANTAMENTOS_DETAILS_STRING = "adiantamentosDetailsReport";

    public static final String REVENUE_LABEL = "Listagem de Receita em Euros";

    public static final String EXPENSES_LABEL = "Listagem de Despesa em Euros";

    public static final String SUMMARY_LABEL = "Resumo por Coordenador";

    public static final String GENERATED_OVERHEADS_LABEL = "Listagem de Overheads Gerados";

    public static final String TRANSFERED_OVERHEADS_LABEL = "Listagem de Overheads Transferidos";

    public static final String OVERHEADS_SUMMARY_LABEL = "Resumo de Overheads";

    public static final String CABIMENTOS_LABEL = "Listagem de Cabimentos";

    public static final String ADIANTAMENTOS_LABEL = "Listagem de Adiantamentos";

    public static final String COMPLETE_EXPENSES_LABEL = "Listagem de Despesas Detalhada";

    public static final String OPENING_PROJECT_FILE_LABEL = "Ficha de Abertura de Projecto";

    public static final String PROJECT_BUDGETARY_BALANCE_LABEL = "Saldo Orçamental por Rubrica";

    private final String projectID;
    private final Layout layout;
    private final String projectCode;

    protected String getProjectCode() {
        return projectCode;
    }

    protected String getProjectID() {
        return projectID;
    }

    public Component getComponent(String projectCode) {
        return layout;
    }

    public void addComponent(Component component) {
        layout.addComponent(component);
    }

    public static ReportType getReportFromType(String reportType, Map<String, String> args, Project project) {
        if (reportType.equals(CABIMENTOS_STRING)) {
            return new CabimentosReportType(args, project);
        }
        if (reportType.equals(ADIANTAMENTOS_STRING)) {
            return new AdiantamentosReportType(args, project);
        }
        if (reportType.equals(CABIMENTOS_DETAILS_STRING)) {
            return new CabimentosDetailsReportType(args, project);
        }
        if (reportType.equals(ADIANTAMENTOS_DETAILS_STRING)) {
            return new AdiantamentosDetailsReportType(args, project);
        }
        return null;
    }

    public abstract String getLabel();

    protected ReportType(Map<String, String> args, Project project) {
        layout = new VerticalLayout();
        projectID = args.get("unit");
        if (project != null) {
            projectCode = project.getProjectCode();
            layout.addComponent(new ProjectHeaderComponent(getLabel(), project));
        } else {
            projectCode = null;
        }
    }

    public abstract String getQuery();

    public CustomTableFormatter getCustomFormatter() {
        return new NoBehaviourCustomTableFormatter();
    };

    static public abstract interface CustomTableFormatter extends Serializable {
        public abstract void format(Table table);
    }

    static class NoBehaviourCustomTableFormatter implements CustomTableFormatter {

        @Override
        public void format(Table table) {
            // TODO Auto-generated method stub

        }

    }

}
