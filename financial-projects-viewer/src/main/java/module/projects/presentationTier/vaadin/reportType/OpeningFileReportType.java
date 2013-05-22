package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.data.Item;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class OpeningFileReportType extends ReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public OpeningFileReportType(Map<String, String> args, Project project) {
        super(args, project);
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

        projectHeaderLayout.addComponent(new Label("<b>Projecto Nº: </b>" + readProperty(i, "PROJECTO"), Label.CONTENT_XHTML));
        projectHeaderLayout.addComponent(new Label("<b>Acrónimo:</b> " + readProperty(i, "ACRONIMO"), Label.CONTENT_XHTML));
        projectHeaderLayout.addComponent(new Label("<b>Unid Expl:</b> " + readProperty(i, "UNIDEXPL"), Label.CONTENT_XHTML));
        addComponent(projectHeaderLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.coordinator") + "</b></h3>",
                Label.CONTENT_XHTML));
        GridLayout projectResponsibleLayout = new GridLayout(2, 2);
        projectResponsibleLayout.setSpacing(true);
        projectResponsibleLayout.setWidth("100%");
        projectResponsibleLayout.addComponent(new Label("<b>Nº Mecanográfico:</b> " + readProperty(i, "NUMCOORDENADOR"),
                Label.CONTENT_XHTML));
        projectResponsibleLayout
                .addComponent(new Label("<b>Nome:</b> " + readProperty(i, "NOMECOORDENADOR"), Label.CONTENT_XHTML));
        projectResponsibleLayout.addComponent(new Label("<b>ext.:</b> " + readProperty(i, "CONTACTO"), Label.CONTENT_XHTML));
        projectResponsibleLayout.addComponent(new Label("<b>Unid. Académica:</b> " + readProperty(i, "UNIACAD") + " - "
                + readProperty(i, "UNIACADDESCRICAO"), Label.CONTENT_XHTML));
        projectResponsibleLayout.setStyleName("layout-grey-background");
        addComponent(projectResponsibleLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.info") + "</b></h3>",
                Label.CONTENT_XHTML));
        GridLayout projectInfoLayout = new GridLayout(2, 12);
        projectInfoLayout.setSpacing(true);
        projectInfoLayout.setWidth("100%");
        projectInfoLayout.addComponent(new Label("<b>Origem:</b> " + readProperty(i, "ORIGEM"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Tipo:</b> " + readProperty(i, "TIPO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Custo:</b> " + readProperty(i, "CUSTOS"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Coordenação:</b> " + readProperty(i, "COORDENACAO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Unid. Operacional:</b> " + readProperty(i, "UNIDOPER") + " - "
                + readProperty(i, "UNIDOPERDESCRICAO"), Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("<b>Moeda:</b> " + readProperty(i, "MOEDA"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>NIB:</b> " + readProperty(i, "NIB"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Nº Contrato:</b> " + readProperty(i, "NUMCONTRACTO"), Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("<b>Nº Projecto Anterior:</b> " + readProperty(i, "OLDPROJ"),
                Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("<b>DG:</b> " + readProperty(i, "DG"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Programa:</b> " + readProperty(i, "PROGRAMA") + " - "
                + readProperty(i, "PROGRAMADESCRICAO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Início:</b> " + readProperty(i, "INICIO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Duração:</b> " + readProperty(i, "DURACAO"), Label.CONTENT_XHTML));
        projectInfoLayout.addComponent(new Label("<b>Título:</b> ", Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label(readProperty(i, "TITULO"), Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("<b>Resumo:</b> ", Label.CONTENT_XHTML));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label(readProperty(i, "RESUMO"), Label.CONTENT_XHTML));
        projectInfoLayout.setStyleName("layout-grey-background");
        addComponent(projectInfoLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.financing") + "</b></h3>",
                Label.CONTENT_XHTML));
        HorizontalLayout projectFinancingLayout = new HorizontalLayout();
        projectFinancingLayout.setSpacing(true);
        projectFinancingLayout.setWidth("100%");
        projectFinancingLayout.addComponent(new Label("<b>Controlo Orçamental de Membros:</b> "
                + readProperty(i, "CONTROLOORCAMMEMB"), Label.CONTENT_XHTML));
        projectFinancingLayout.addComponent(new Label("<b>Contabilidade IVA:</b> " + readProperty(i, "CONTIVA"),
                Label.CONTENT_XHTML));
        projectFinancingLayout.addComponent(new Label("<b>IVA Elegível:</b> " + readProperty(i, "CONTIVAILEGIVEL"),
                Label.CONTENT_XHTML));
        projectFinancingLayout.setStyleName("layout-grey-background");
        addComponent(projectFinancingLayout);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.overheadDistribution")
                + "</b></h3>", Label.CONTENT_XHTML));
        GridLayout overheadDistributionLayout = new GridLayout(6, 2);
        overheadDistributionLayout.setSpacing(true);
        overheadDistributionLayout.setWidth("100%");
        overheadDistributionLayout.setStyleName("layout-grey-background");
        overheadDistributionLayout.addComponent(new Label("<b>Data Início</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>Org Gestão</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>Unid. Exploração</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>Unid. Académica</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>Unid. Operacional</b>", Label.CONTENT_XHTML));
        overheadDistributionLayout.addComponent(new Label("<b>Coordenador</b>", Label.CONTENT_XHTML));
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

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.researchTeam") + "</b></h3>",
                Label.CONTENT_XHTML));
        ReportViewerComponent teamReportViewer =
                new ReportViewerComponent("select \"CODE\", \"DESCRIPTION\" from V_EQUIPA_INVESTIGACAO where PROJECTCODE='"
                        + getProjectCode() + "'", getCustomFormatter());
        teamReportViewer.getTable().setPageLength(0);
        addComponent(teamReportViewer);

        addComponent(new Label("<h3><b>" + getMessage("financialprojectsreports.openingFile.label.membersBudget") + "</b></h3>",
                Label.CONTENT_XHTML));
        ReportViewerComponent consorciumMemberBudgetViewer =
                new ReportViewerComponent(
                        "select \"INSTITUICAO\", \"DESCRICAOINSTITUICAO\", \"TIPO\", \"OVH\", \"GRP\", \"PCTFINANCIAMENTO\" from V_MEMBROS_CONSORCIO where PROJECTO='"
                                + getProjectCode() + "'", getCustomFormatter());
        consorciumMemberBudgetViewer.getTable().setPageLength(0);
        addComponent(consorciumMemberBudgetViewer);

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
}
