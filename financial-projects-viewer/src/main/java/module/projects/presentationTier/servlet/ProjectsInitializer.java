package module.projects.presentationTier.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import module.projects.presentationTier.vaadin.reportType.ReportType;
import module.projects.presentationTier.vaadin.reportType.ReportType.NoBehaviourCustomTableFormatter;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.expenditureTrackingSystem.domain.ExpenditureTrackingSystem;
import pt.ist.expenditureTrackingSystem.domain.organization.Person;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;
import pt.ist.expenditureTrackingSystem.domain.organization.SubProject;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;

@WebListener
public class ProjectsInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ExpenditureTrackingSystem.registerInfoProvider(new ProjectReportsInfoProvider());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    public class ProjectReportsInfoProvider implements ExpenditureTrackingSystem.InfoProvider {

        @Override
        public String getTitle() {
            return getMessage("financialprojectsreports.infoProvider.title");
        }

        @Override
        public Map<String, String> getLinks(String page, Object object) {
            Map<String, String> map = null;
            if (page.equals("viewOrganization.jsp")) {
                if (!(object instanceof Unit)) {
                    return null;
                }

                Unit unit = (Unit) object;
                Person currentUser = UserView.getCurrentUser().getExpenditurePerson();
                if (!(unit.isResponsible(currentUser) || unit.getObserversSet().contains(currentUser) || UserView
                        .getCurrentUser().hasRoleType(RoleType.MANAGER))) {
                    return null;
                }

                map = new HashMap<String, String>();
                String baseLink = "/vaadinContext.do?method=forwardToVaadin#projectsService?unit=" + unit.getExternalId();
                if (unit.isProject()) {
                    map.put(getMessage("financialprojectsreports.infoProvider.cabimentos"), baseLink + "&reportType="
                            + ReportType.CABIMENTOS_STRING);
                    map.put(getMessage("financialprojectsreports.infoProvider.adiantamentos"), baseLink + "&reportType="
                            + ReportType.ADIANTAMENTOS_STRING);
                    map.put(getMessage("financialprojectsreports.infoProvider.revenue"), baseLink + "&reportType="
                            + ReportType.REVENUE_STRING);
                    map.put(getMessage("financialprojectsreports.infoProvider.expenses"), baseLink + "&reportType="
                            + ReportType.EXPENSES_STRING);
                    map.put(getMessage("financialprojectsreports.infoProvider.budgetaryBalance"), baseLink + "&reportType="
                            + ReportType.PROJECT_BUDGETARY_BALANCE_STRING);
                    map.put(getMessage("financialprojectsreports.infoProvider.openingFile"), baseLink + "&reportType="
                            + ReportType.OPENING_PROJECT_FILE_STRING);
                } else {
                    map.put(getMessage("financialprojectsreports.infoProvider.unitSummary"), baseLink + "&reportType="
                            + ReportType.UNIT_SUMMARY_STRING);
                    map.put(getMessage("financialprojectsreports.infoProvider.generatedOverheads"), baseLink + "&reportType="
                            + ReportType.GENERATED_OVERHEADS_STRING);
                    map.put(getMessage("financialprojectsreports.infoProvider.transferedOverheads"), baseLink + "&reportType="
                            + ReportType.TRANSFERED_OVERHEADS_STRING);
                    map.put(getMessage("financialprojectsreports.infoProvider.overheadsSummary"), baseLink + "&reportType="
                            + ReportType.OVERHEADS_SUMMARY_STRING);
                }
            }
            if (page.equals("viewPerson.jsp")) {
                if (!(object instanceof Person)) {
                    return null;
                }
                Person person = (Person) object;

                if (!(UserView.getCurrentUser().getExpenditurePerson().equals(person) || UserView.getCurrentUser().hasRoleType(
                        RoleType.MANAGER))) {
                    return null;
                }
                map = new HashMap<String, String>();
                map.put(getMessage("financialprojectsreports.infoProvider.coordinatorSummary"),
                        "/vaadinContext.do?method=forwardToVaadin#projectsService?reportType=" + ReportType.SUMMARY_STRING
                                + "&user=" + person.getUsername());
            }
            return map;
        }

        public List<List<String>> getSummary(String page, Object object) {
            List<List<String>> list = null;
            if (page.equals("viewOrganization.jsp")) {
                if (!(object instanceof Unit)) {
                    return null;
                }

                Unit unit = (Unit) object;
                Person currentUser = UserView.getCurrentUser().getExpenditurePerson();
                if (!(unit.isResponsible(currentUser) || unit.getObserversSet().contains(currentUser) || UserView
                        .getCurrentUser().hasRoleType(RoleType.MANAGER))) {
                    return null;
                }

                list = new ArrayList<List<String>>();
                list.add(new ArrayList<String>());
                list.add(new ArrayList<String>());

                ReportViewerComponent projectSummary;
                Project project = getProjectFromID(unit.getExternalId());
                String projectCode = project.getProjectCode();
                System.out.println(projectCode);

                projectSummary =
                        new ReportViewerComponent(
                                "SELECT V.\"Orçamento\", V.\"Máximo Financiável\", V.\"Receita\", V.\"Despesa\", V.\"Adiantamentos por Justificar\", V.\"Cabimentos por Executar\" , V.\"Saldo Tesouraria\" FROM V_RESPROJPROF V WHERE V.\"NºProj\"='"
                                        + projectCode + "'", new NoBehaviourCustomTableFormatter());

                Table t = projectSummary.getTable();

                if (t.getItemIds().size() > 0) {
                    for (Object a : t.getItemIds()) {
                        Item item = t.getItem(a);

                        for (Object column : item.getItemPropertyIds()) {
                            String itemString = ReportType.formatCurrency(item.getItemProperty(column).toString());
                            list.get(0).add(t.getColumnHeader(column));
                            list.get(1).add(itemString);
                        }
                    }
                }
            }
            return list;
        }

        public String getMessage(String message) {
            return BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources", message);
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

}
