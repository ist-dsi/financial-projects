package module.projects.presentationTier.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import module.projects.presentationTier.vaadin.reportType.ReportType;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.expenditureTrackingSystem.domain.ExpenditureTrackingSystem;
import pt.ist.expenditureTrackingSystem.domain.organization.Person;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;

@WebListener
public class ProjectsInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ExpenditureTrackingSystem.registerInfoProvider(new ProjectReportsInfoProvider());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private class ProjectReportsInfoProvider implements ExpenditureTrackingSystem.InfoProvider {

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

        public String getMessage(String message) {
            return BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources", message);
        }
    }

}
