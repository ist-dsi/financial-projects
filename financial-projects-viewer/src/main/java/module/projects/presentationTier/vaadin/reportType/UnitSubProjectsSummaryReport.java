package module.projects.presentationTier.vaadin.reportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import module.projects.presentationTier.vaadin.IllegalAccessException;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public class UnitSubProjectsSummaryReport extends ReportType {
    private final String unitID;
    final List<String> projectCodes;

    protected UnitSubProjectsSummaryReport(Map<String, String> args) {
        super(args);
        projectCodes = new ArrayList<String>();

        unitID = args.get("unit");
        Unit unit = FenixFramework.getDomainObject(unitID);
        //|| !UserView.getCurrentUser().getExpenditurePerson().getDirectResponsibleUnits().contains(unit)
        if (unit.isProject()) {
            throw new IllegalAccessException();
        }

        for (Unit subUnit : unit.getSubUnitsSet()) {
            if (subUnit instanceof Project) {
                Project project = (Project) subUnit;
                if (!project.hasResponsiblesInUnit()) {
                    projectCodes.add(project.getProjectCode());
                }
            }
        }

        String query = getQuery();
        addComponent(new Label(getLabel()));
        if (query == null) {
            addComponent(new Label(getMessage("financialprojectsreports.unitReport.label.noProjects")));
        } else {
            ReportViewerComponent reportViewer = new ReportViewerComponent(query, getCustomFormatter());
            setColumnNames(reportViewer.getTable());
            Panel panel = new Panel();
            panel.addComponent(reportViewer);
            panel.getContent().setSizeUndefined();
            addComponent(panel);
            TableSummaryComponent summary =
                    new TableSummaryComponent(reportViewer.getTable(), getLabel(), "ORCBRUTO", "MAXFINANC", "RECEITA",
                            "TRF_PARCEIROS", "DESP_VALOR", "JU_AD_VALOR", "TREASURY_BALANCE", "CB_AD_VALOR", "BUDGET_BALANCE");
            addComponent(summary);
        }
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.unitSummary");
    }

    @Override
    public String getQuery() {
        String subQuery = getProjectsSubQuery();
        if (subQuery == null) {
            return null;
        }
        return "select proj, acronimo, proj_ue, proj_tipo, orcbruto, maxfinanc, receita, trf_parceiros, desp_valor, ju_ad_valor , cb_ad_valor,receita - (trf_parceiros + desp_valor + ju_ad_valor) as treasury_balance ,maxfinanc - (desp_valor + cb_ad_valor) as budget_balance from V_LST_RESUMOCOORDENADOR "
                + subQuery;
    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setColumnNames(Table table) {
        table.setColumnHeader("PROJ", getMessage("financialprojectsreports.coordinatorReport.column.projectNumber"));
        table.setColumnHeader("ACRONIMO", getMessage("financialprojectsreports.coordinatorReport.column.acronym"));
        table.setColumnHeader("PROJ_UE", getMessage("financialprojectsreports.coordinatorReport.column.explorationUnit"));
        table.setColumnHeader("PROJ_TIPO", getMessage("financialprojectsreports.coordinatorReport.column.type"));
        table.setColumnHeader("ORCBRUTO", getMessage("financialprojectsreports.coordinatorReport.column.budget"));
        table.setColumnHeader("MAXFINANC", getMessage("financialprojectsreports.coordinatorReport.column.financiableMaximum"));
        table.setColumnHeader("RECEITA", getMessage("financialprojectsreports.coordinatorReport.column.revenue"));
        table.setColumnHeader("TRF_PARCEIROS", getMessage("financialprojectsreports.coordinatorReport.column.parteners"));
        table.setColumnHeader("DESP_VALOR", getMessage("financialprojectsreports.coordinatorReport.column.expense"));
        table.setColumnHeader("JU_AD_VALOR", getMessage("financialprojectsreports.coordinatorReport.column.adperJust"));
        table.setColumnHeader("CB_AD_VALOR", getMessage("financialprojectsreports.coordinatorReport.column.cabimentsToExecute"));
        table.setColumnHeader("TREASURY_BALANCE", getMessage("financialprojectsreports.coordinatorReport.column.treasuryBalance"));
        table.setColumnHeader("BUDGET_BALANCE", getMessage("financialprojectsreports.coordinatorReport.column.budgetatyBalance"));
    }

    String getProjectsSubQuery() {
        if (projectCodes.size() == 0) {
            return "";
        } else {
            String subquery = "where proj='" + projectCodes.get(0) + "' ";
            for (int i = 1; i < projectCodes.size(); i++) {
                subquery += "or proj='" + projectCodes.get(i) + "' ";
            }
            return subquery;
        }
    }
}
