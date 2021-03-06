package module.projects.presentationTier.vaadin.reportType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import module.projects.presentationTier.vaadin.IllegalAccessException;
import module.projects.presentationTier.vaadin.reportType.components.CoordinatorHeaderComponent;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.domain.User;
import pt.ist.expenditureTrackingSystem.domain.authorizations.Authorization;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;
import pt.ist.expenditureTrackingSystem.domain.organization.SubProject;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public class CoordinatorReportType extends ReportType {
    final ReportViewerComponent reportViewer;
    TableSummaryComponent summary;
    CoordinatorHeaderComponent header;
    User user;

    protected CoordinatorReportType(Map<String, String> args) {
        super(args);
        user = User.findByUsername(args.get("user"));

        if (!(UserView.getCurrentUser().equals(user) || UserView.getCurrentUser().hasRoleType(RoleType.MANAGER))) {
            throw new IllegalAccessException();
        }
        Panel panel = new Panel();
        addComponent(new Label("<b>" + getLabel() + "</b>", Label.CONTENT_XHTML));
        String query = getQuery();
        if (query != null) {
            header = new CoordinatorHeaderComponent(user.getPresentationName(), getLabel());

            addComponent(header);
            reportViewer = new ReportViewerComponent(query, getCustomFormatter());
            setColumnNames(reportViewer.getTable());
            summary =
                    new TableSummaryComponent(reportViewer.getTable(), getLabel(), "ORCBRUTO", "MAXFINANC", "RECEITA",
                            "TRF_PARCEIROS", "DESP_VALOR", "JU_AD_VALOR", "TREASURY_BALANCE", "CB_AD_VALOR", "BUDGET_BALANCE");

            addComponent(panel);

            panel.setScrollable(true);
            panel.addComponent(reportViewer);
            panel.setWidth("100%");
            panel.getContent().setSizeUndefined();
            addComponent(summary);
            addComponent(new Label(getMessage("financialprojectsreports.coordinatorReport.label.warning")));

        } else {
            reportViewer = null;
            addComponent(new Label(getMessage("financialprojectsreports.coordinatorReport.label.noProjects")));
        }

    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        header.write(sheet, headersFont);
        reportViewer.write(sheet, headersFont);
        summary.write(sheet, headersFont);
    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.byCoordinatorsummary");
    }

    @Override
    public String getQuery() {
        List<Unit> directResponsibleUnits = user.getExpenditurePerson().getDirectResponsibleUnits();
        directResponsibleUnits.addAll(UserView.getCurrentUser().getExpenditurePerson().getObservableUnitsSet());

        List<String> directResponsibleProjectCodes = new LinkedList<String>();
        for (Unit unit : directResponsibleUnits) {
            Project project = getProjectFromID(unit.getExternalId());
            if (project != null) {
                Set<Authorization> projectAuthorizations = project.getAuthorizationsSet();
                for (Authorization auth : projectAuthorizations) {
                    if (auth.getPerson().equals(user.getExpenditurePerson())) {
                        if (auth.isValid()) {
                            String projectCode = project.getProjectCode();
                            if (projectCode.matches("[a-zA-Z][a-zA-Z]\\d{1,4}")) {
                                projectCode = projectCode.substring(2);
                            }
                            directResponsibleProjectCodes.add(projectCode);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        String queryProjectInfo = "";
        if (directResponsibleProjectCodes.size() == 0) {
            return null;
        }
        if (directResponsibleProjectCodes.size() >= 1) {
            queryProjectInfo = "proj='" + directResponsibleProjectCodes.get(0) + "'";
            for (int i = 1; i < directResponsibleProjectCodes.size(); i++) {
                queryProjectInfo += " OR proj='" + directResponsibleProjectCodes.get(i) + "'";
            }
        }

        return "select proj, acronimo, proj_ue, proj_tipo, orcbruto, maxfinanc, receita, trf_parceiros, desp_valor, ju_ad_valor , cb_ad_valor,receita - (trf_parceiros + desp_valor + ju_ad_valor) as treasury_balance ,maxfinanc - (desp_valor + cb_ad_valor) as budget_balance from V_LST_RESUMOCOORDENADOR where "
                + queryProjectInfo;
    }

    @Override
    protected ReportViewerComponent getReportViewer() {
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

    private Project getProjectFromID(String projectID) {
        Unit project = FenixFramework.getDomainObject(projectID);
        if (project instanceof Project) {
            return (Project) project;
        } else if (project instanceof SubProject) {
            return (Project) ((SubProject) project).getParentUnit();
        }
        return null;
    }
}
