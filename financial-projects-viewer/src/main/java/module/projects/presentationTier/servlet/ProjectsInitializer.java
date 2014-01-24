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
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
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
                ReportViewerComponent eurRevenue;
                ReportViewerComponent cabimentos, adiantamentos;
                String projectCode = getProjectFromID(unit.getExternalId()).getProjectCode();
                eurRevenue =
                        new ReportViewerComponent(
                                "select \"RECEITA\", \"DESPESA\", \"TOTAL\" from V_RESUMO_EURO where PROJECTCODE='" + projectCode
                                        + "'", new NoBehaviourCustomTableFormatter());

                adiantamentos =
                        new ReportViewerComponent(
                                "select distinct \"PAI_IDMOV\", \"PAI_IDRUB\", \"PAI_TIPO\", TO_CHAR(\"PAI_DATA\",'YYYY-MM-DD') as DATA, \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\", SUM(\"FILHO_VALOR\") + SUM(\"FILHO_IVA\") as EXECUTED, (\"PAI_VALOR_TOTAL\" - SUM(\"FILHO_VALOR\") - SUM(\"FILHO_IVA\")) as MISSING from \"V_MOV_ADIANTAMENTOS\" where \"PAI_IDPROJ\"='"
                                        + projectCode
                                        + "' group by \"PAI_IDMOV\", \"PAI_IDRUB\", \"PAI_TIPO\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\" order by DATA",
                                new NoBehaviourCustomTableFormatter());

                cabimentos =
                        new ReportViewerComponent(
                                "select \"PAI_IDMOV\", \"PAI_IDRUB\", \"PAI_TIPO\", TO_CHAR(\"PAI_DATA\",'YYYY-MM-DD') as DATA, \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\",SUM(FILHO_VALOR) + SUM(FILHO_IVA) as TOTAL_EXECUCOES, \"PAI_VALOR_TOTAL\" - SUM(FILHO_VALOR) - SUM(FILHO_IVA) EXECUCOES_EM_FALTA from \"V_MOV_CABIMENTOS\" where \"PAI_IDPROJ\"='"
                                        + projectCode
                                        + "' group by \"PAI_IDMOV\", \"PAI_TIPO\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\" order by DATA",
                                new NoBehaviourCustomTableFormatter());

                TableSummaryComponent cabimentosSummaray =
                        new TableSummaryComponent(cabimentos.getTable(), ReportType.CABIMENTOS_STRING, "EXECUCOES_EM_FALTA");

                TableSummaryComponent adiantamentosSummary =
                        new TableSummaryComponent(adiantamentos.getTable(), ReportType.ADIANTAMENTOS_STRING, "MISSING");

                Table t = eurRevenue.getTable();

                t.setColumnHeader("RECEITA", getMessage("financialprojectsreports.summary.treasury.revenue"));
                t.setColumnHeader("DESPESA", getMessage("financialprojectsreports.summary.treasury.expense"));
                t.setColumnHeader("TOTAL", getMessage("financialprojectsreports.summary.treasury.total"));

                Item item = t.getItem(t.getItemIds().toArray()[0]);
                for (Object column : item.getItemPropertyIds()) {
                    String itemString = ReportType.formatCurrency(item.getItemProperty(column).toString());
                    list.get(0).add(t.getColumnHeader(column));
                    list.get(1).add(itemString);
                }

                for (String column : cabimentosSummaray.getResults().keySet()) {
                    String value = ReportType.formatCurrency(cabimentosSummaray.getResults().get(column));
                    list.get(0).add(getMessage("financialprojectsreports.summary.cabimentos"));
                    list.get(1).add(value);
                }

                for (String column : adiantamentosSummary.getResults().keySet()) {
                    String value = ReportType.formatCurrency(adiantamentosSummary.getResults().get(column));
                    list.get(0).add(getMessage("financialprojectsreports.summary.adiantamentos"));
                    list.get(1).add(value);
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
