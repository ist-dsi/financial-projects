/*
 * Copied from net.sourceforge.fenixedu.util.projectsManagement.ReportType, on 14/03/2013
 *
 */
package module.projects.presentationTier.vaadin.reportType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import module.projects.presentationTier.vaadin.IllegalAccessException;
import module.projects.presentationTier.vaadin.Reportable;
import module.projects.presentationTier.vaadin.reportType.components.ProjectHeaderComponent;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
import module.projects.presentationTier.vaadin.reportType.movementReportType.AdiantamentosDetailsReportType;
import module.projects.presentationTier.vaadin.reportType.movementReportType.AdiantamentosReportType;
import module.projects.presentationTier.vaadin.reportType.movementReportType.CabimentosDetailsReportType;
import module.projects.presentationTier.vaadin.reportType.movementReportType.CabimentosReportType;
import pt.ist.bennu.core.util.BundleUtil;

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

    private final VerticalLayout layout;
    ProjectHeaderComponent header;
    private boolean headerVisibility;

    public Component getComponent() {
        return layout;
    }

    public void addComponent(Component component) {
        layout.addComponent(component);
    }

    public static ReportType getReportFromType(String reportType, Map<String, String> args) {
        try {
            if (reportType.equals(CABIMENTOS_STRING)) {
                return new CabimentosReportType(args);
            }
            if (reportType.equals(ADIANTAMENTOS_STRING)) {
                return new AdiantamentosReportType(args);
            }
            if (reportType.equals(CABIMENTOS_DETAILS_STRING)) {
                return new CabimentosDetailsReportType(args);
            }
            if (reportType.equals(ADIANTAMENTOS_DETAILS_STRING)) {
                return new AdiantamentosDetailsReportType(args);
            }
            if (reportType.equals(REVENUE_STRING)) {
                return new RevenueReportType(args);
            }
            if (reportType.equals(EXPENSES_STRING)) {
                return new ExpensesReportType(args);
            }
            if (reportType.equals(PROJECT_BUDGETARY_BALANCE_STRING)) {
                return new BudgetaryBalanceReportType(args);
            }
            if (reportType.equals(OPENING_PROJECT_FILE_STRING)) {
                return new OpeningFileReportType(args);
            }
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public abstract String getLabel();

    protected ReportType(Map<String, String> args) {
        layout = new VerticalLayout();
        layout.setHeight("100%");
        layout.setSpacing(true);

    }

    public abstract String getQuery();

    public CustomTableFormatter getCustomFormatter() {
        return new NoBehaviourCustomTableFormatter();
    };

    static public abstract interface CustomTableFormatter extends Serializable {
        public abstract void format(Table table);
    }

    static public class NoBehaviourCustomTableFormatter implements CustomTableFormatter {

        @Override
        public void format(Table table) {
        }

    }

    protected HashMap<String, String> getArgs() {
        HashMap<String, String> args = new HashMap<String, String>();
        return args;
    }

    protected Layout getLayout() {
        return layout;
    }

    public Reportable getHeader() {
        return header;
    }

    public TableSummaryComponent getSummary() {
        throw new UnsupportedOperationException();
    }

    protected abstract ReportViewerComponent getReportViewer();

    protected void setHeaderVisibility(boolean visibility) {
        headerVisibility = visibility;
        if (!visibility) {
            layout.removeComponent(header);
            header = null;
        }
    }

    public final String RESOURCE_BUNDLE = "resources/projectsResources";

    public String getMessage(String message) {
        return BundleUtil.getFormattedStringFromResourceBundle(RESOURCE_BUNDLE, message);
    }

    public boolean isToExport() {
        return true;
    }

    protected void setHeader(ProjectHeaderComponent header) {
        this.header = header;
    }
}
