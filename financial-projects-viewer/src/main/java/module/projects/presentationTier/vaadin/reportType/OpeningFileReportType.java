package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.data.Item;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class OpeningFileReportType extends ProjectReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public OpeningFileReportType(Map<String, String> args) {
        super(args);
        setHeaderVisibility(false);
        //reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        //addComponent(reportViewer);

        addComponent(new Label("<h3><b>" + getLabel() + "</b></h3>", Label.CONTENT_XHTML));

        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        Object itemID = reportViewer.getTable().getItemIds().toArray()[0];
        Item i = reportViewer.getTable().getItem(itemID);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.project") + "</b></h3>",
                Label.CONTENT_XHTML));
        HorizontalLayout projectHeaderLayout = new HorizontalLayout();
        projectHeaderLayout.setSpacing(true);
        projectHeaderLayout.setWidth("100%");
        projectHeaderLayout.setStyleName("layout-grey-background");

        projectHeaderLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.projectNumber") + ": </b>"
                + readProperty(i, "PROJECTO"), Label.CONTENT_XHTML));
        projectHeaderLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.acronym")
                + ":</b> " + readProperty(i, "ACRONIMO"), Label.CONTENT_XHTML));
        projectHeaderLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.exploringUnit") + ":</b> "
                + readProperty(i, "UNIDEXPL"), Label.CONTENT_XHTML));
        addComponent(projectHeaderLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.coordinator") + "</b></h3>",
                Label.CONTENT_XHTML));
        GridLayout projectResponsibleLayout = new GridLayout(2, 2);
        projectResponsibleLayout.setSpacing(true);
        projectResponsibleLayout.setWidth("100%");
        projectResponsibleLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.coordinatorNumber") + ":</b> "
                + readProperty(i, "NUMCOORDENADOR"), Label.CONTENT_XHTML));
        projectResponsibleLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.coordinatorName") + ":</b> "
                + readProperty(i, "NOMECOORDENADOR"), Label.CONTENT_XHTML));
        projectResponsibleLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.coordinatorExtension") + ":</b> "
                + readProperty(i, "CONTACTO"), Label.CONTENT_XHTML));
        projectResponsibleLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.academicUnit") + ":</b> "
                + readProperty(i, "UNIACAD") + " - " + readProperty(i, "UNIACADDESCRICAO"), Label.CONTENT_XHTML));
        projectResponsibleLayout.setStyleName("layout-grey-background");
        addComponent(projectResponsibleLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.info") + "</b></h3>",
                Label.CONTENT_XHTML));
        GridLayout projectInfoLayout = new GridLayout(2, 12);
        projectInfoLayout.setSpacing(true);
        projectInfoLayout.setWidth("100%");
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.origin")
                + ":</b> " + readProperty(i, "ORIGEM"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.type")
                + ":</b> " + readProperty(i, "TIPO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.cost")
                + ":</b> " + readProperty(i, "CUSTOS"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.coordination") + ":</b> "
                + readProperty(i, "COORDENACAO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.operationalUnit") + ":</b> "
                + readProperty(i, "UNIDOPER") + " - " + readProperty(i, "UNIDOPERDESCRICAO"), Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.coin")
                + ":</b> " + readProperty(i, "MOEDA"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.bankInfo")
                + ":</b> " + readProperty(i, "NIB"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.contractNumber") + ":</b> "
                + readProperty(i, "NUMCONTRACTO"), Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.previousProjectNumber") + ":</b> "
                + readProperty(i, "OLDPROJ"), Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.dg")
                + ":</b> " + readProperty(i, "DG"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.program")
                + ":</b> " + readProperty(i, "PROGRAMA") + " - " + readProperty(i, "PROGRAMADESCRICAO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.begin")
                + ":</b> " + readProperty(i, "INICIO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.duration")
                + ":</b> " + readProperty(i, "DURACAO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.title")
                + ":</b> ", Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label(readProperty(i, "TITULO"), Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFileReport.label.summary")
                + ":</b> ", Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label(readProperty(i, "RESUMO"), Label.CONTENT_XHTML));
        projectInfoLayout.setStyleName("layout-grey-background");
        addComponent(projectInfoLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.financing") + "</b></h3>",
                Label.CONTENT_XHTML));
        HorizontalLayout projectFinancingLayout = new HorizontalLayout();
        projectFinancingLayout.setSpacing(true);
        projectFinancingLayout.setWidth("100%");
        projectFinancingLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.membersBudgetaryControl") + ":</b> "
                + readProperty(i, "CONTROLOORCAMMEMB"), Label.CONTENT_XHTML));
        projectFinancingLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.accountabilityIva") + ":</b> "
                + readProperty(i, "CONTIVA"), Label.CONTENT_XHTML));
        projectFinancingLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.eligibleIva") + ":</b> "
                + readProperty(i, "CONTIVAILEGIVEL"), Label.CONTENT_XHTML));
        projectFinancingLayout.setStyleName("layout-grey-background");
        addComponent(projectFinancingLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.overheadDistribution")
                + "</b></h3>", Label.CONTENT_XHTML));
        GridLayout overheadDistributionLayout = new GridLayout(6, 2);
        overheadDistributionLayout.setSpacing(true);
        overheadDistributionLayout.setWidth("100%");
        overheadDistributionLayout.setStyleName("layout-grey-background");
        overheadDistributionLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.beginDate") + "</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.managementOrg") + "</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.explorationUnit") + "</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.academicUnit") + "</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.operationalUnit") + "</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>"
                + getMessage("financialprojectsreports.openingFileReport.label.coordinator") + "</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHDATA")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHGESTAO")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHUNIDEXPL")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHUNIDACAD")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHUNIDOPER")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHCOORD")));
        addComponent(overheadDistributionLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.financingEntities")
                + "</b></h3>", Label.CONTENT_XHTML));
        ReportViewerComponent financingEntitiesReportViewer =
                new ReportViewerComponent(
                        "select \"CODE\", \"DESCRIPTION\", \"VALUE\" from V_ENTIDADES_PROJECTO where PROJECTCODE='"
                                + getProjectCode() + "'", getCustomFormatter());
        financingEntitiesReportViewer.getTable().setPageLength(0);

        addComponent(financingEntitiesReportViewer);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.budgetByRubric") + "</b></h3>",
                Label.CONTENT_XHTML));
        ReportViewerComponent budgetByRubricReportViewer =
                new ReportViewerComponent(
                        "select \"CODE\", \"DESCRIPTION\", \"VALUE\" from V_ORCAMENTO_RUBRICA where PROJECTCODE='"
                                + getProjectCode() + "'", getCustomFormatter());
        budgetByRubricReportViewer.getTable().setPageLength(0);
        addComponent(budgetByRubricReportViewer);
        addComponent(new Label(getMessage("financialprojectsreports.openingFile.label.warning"), Label.CONTENT_XHTML));

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.researchTeam") + "</b></h3>",
                Label.CONTENT_XHTML));
        ReportViewerComponent teamReportViewer =
                new ReportViewerComponent("select \"CODE\", \"DESCRIPTION\" from V_EQUIPA_INVESTIGACAO where PROJECTCODE='"
                        + getProjectCode() + "'", getCustomFormatter());
        teamReportViewer.getTable().setPageLength(0);
        addComponent(teamReportViewer);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.membersBudget") + "</b></h3>",
                Label.CONTENT_XHTML));

        ReportViewerComponent arePartnersView =
                new ReportViewerComponent("select controloorcammemb from V_FICHA_ABERTURA where PROJECTO='" + getProjectCode()
                        + "'", getCustomFormatter());

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
                            "select \"INSTITUICAO\", \"DESCRICAOINSTITUICAO\", \"TIPO\", \"OVH\", \"GRP\", \"PCTFINANCIAMENTO\" from V_MEMBROS_CONSORCIO where PROJECTO='"
                                    + getProjectCode() + "'", getCustomFormatter());
            consorciumBudgetViewer.getTable().setPageLength(0);
            addComponent(consorciumBudgetViewer);
            setColumnsHeaders(financingEntitiesReportViewer.getTable(), budgetByRubricReportViewer.getTable(),
                    teamReportViewer.getTable(), consorciumBudgetViewer.getTable());

            for (Object itemId : consorciumBudgetViewer.getTable().getItemIds()) {
                String member = consorciumBudgetViewer.getTable().getItem(itemId).getItemProperty("INSTITUICAO").toString();
                String memberName =
                        consorciumBudgetViewer.getTable().getItem(itemId).getItemProperty("DESCRICAOINSTITUICAO").toString();
                addComponent(new Label("<b>" + getMessage("financialprojectsreports.openingFile.label.perMembersBudget") + " "
                        + member + " (" + memberName + ")" + "</b>", Label.CONTENT_XHTML));
                ReportViewerComponent consorciumMemberBudgetViewer =
                        new ReportViewerComponent(
                                "select RUBRICA,DESCRIPTIONRUBRICA, VALOR from V_RUBRICAS_MEMBROS where PROJECTO='"
                                        + getProjectCode() + "' AND INSTITUICAO='" + member + "'", getCustomFormatter());
                consorciumMemberBudgetViewer.getTable().setPageLength(0);
                addComponent(consorciumMemberBudgetViewer);
                setBudgetPerMemberTableHeaders(consorciumMemberBudgetViewer.getTable());
            }

        } else {
            ReportViewerComponent consorciumMemberBudgetViewer =
                    new ReportViewerComponent(
                            "select \"INSTITUICAO\", \"DESCRICAOINSTITUICAO\", \"TIPO\", \"OVH\", \"GRP\", \"PCTFINANCIAMENTO\" from V_MEMBROS_CONSORCIO where PROJECTO='"
                                    + getProjectCode() + "'", getCustomFormatter());
            consorciumMemberBudgetViewer.getTable().setPageLength(0);
            addComponent(consorciumMemberBudgetViewer);
            setColumnsHeaders(financingEntitiesReportViewer.getTable(), budgetByRubricReportViewer.getTable(),
                    teamReportViewer.getTable(), consorciumMemberBudgetViewer.getTable());
        }

    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        return reportViewer;
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        //reportViewer.write(sheet);
        //tableSummary.write(sheet);
    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.projectOpeningFile");
    }

    @Override
    public String getQuery() {
        return "select \"PROJECTO\", \"ACRONIMO\", \"UNIDEXPL\", \"NUMCOORDENADOR\", \"NOMECOORDENADOR\", \"UNIACAD\", \"UNIACADDESCRICAO\", \"CONTACTO\", \"ORIGEM\", \"TIPO\", \"CUSTOS\", \"COORDENACAO\", \"UNIDOPER\", \"UNIDOPERDESCRICAO\", \"MOEDA\", \"NIB\", \"NUMCONTRACTO\", \"OLDPROJ\", \"DG\", \"PROGRAMA\", \"PROGRAMADESCRICAO\", \"INICIO\", \"DURACAO\", \"TITULO\", \"RESUMO\", \"CONTROLOORCAMMEMB\", \"CONTIVA\", \"CONTIVAILEGIVEL\", \"OVHDATA\", \"OVHGESTAO\", \"OVHUNIDEXPL\", \"OVHUNIDACAD\", \"OVHUNIDOPER\", \"OVHCOORD\" from V_FICHA_ABERTURA where PROJECTO='"
                + getProjectCode() + "'";
    }

    String readProperty(Item i, Object propertyID) {
        String value = i.getItemProperty(propertyID).toString();
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    @Override
    public boolean isToExport() {
        return false;
    }

    public void setColumnsHeaders(Table financingEntitiesTable, Table budgetbyRubricTable, Table researchTeamTable,
            Table membersBudgetTable) {
        setBudgetTableHeaders(financingEntitiesTable);

        setBudgetTableHeaders(budgetbyRubricTable);

        researchTeamTable.setColumnHeader("CODE", getMessage("financialprojectsreports.openingFileReport.column.code"));
        researchTeamTable.setColumnHeader("DESCRIPTION",
                getMessage("financialprojectsreports.openingFileReport.column.description"));

        membersBudgetTable.setColumnHeader("INSTITUICAO",
                getMessage("financialprojectsreports.openingFileReport.column.institution"));
        membersBudgetTable.setColumnHeader("DESCRICAOINSTITUICAO",
                getMessage("financialprojectsreports.openingFileReport.column.description"));
        membersBudgetTable.setColumnHeader("TIPO", getMessage("financialprojectsreports.openingFileReport.column.type"));
        membersBudgetTable.setColumnHeader("OVH", getMessage("financialprojectsreports.openingFileReport.column.overhead"));
        membersBudgetTable.setColumnHeader("GRP", getMessage("financialprojectsreports.openingFileReport.column.grp"));
        membersBudgetTable.setColumnHeader("PCTFINANCIAMENTO",
                getMessage("financialprojectsreports.openingFileReport.column.financingPercentage"));
    }

    private void setBudgetTableHeaders(Table budgetbyRubricTable) {
        budgetbyRubricTable.setColumnHeader("CODE", getMessage("financialprojectsreports.openingFileReport.column.code"));
        budgetbyRubricTable.setColumnHeader("DESCRIPTION",
                getMessage("financialprojectsreports.openingFileReport.column.description"));
        budgetbyRubricTable.setColumnHeader("VALUE", getMessage("financialprojectsreports.openingFileReport.column.value"));
    }

    private void setBudgetPerMemberTableHeaders(Table budgetbyRubricTable) {
        budgetbyRubricTable.setColumnHeader("CODE", getMessage("financialprojectsreports.openingFileReport.column.code"));
        budgetbyRubricTable.setColumnHeader("DESCRIPTIONRUBRICA",
                getMessage("financialprojectsreports.openingFileReport.column.description"));
        budgetbyRubricTable.setColumnHeader("VALUE", getMessage("financialprojectsreports.openingFileReport.column.value"));
    }

}
