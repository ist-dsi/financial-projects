package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

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
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        Object itemID = reportViewer.getTable().getItemIds().toArray()[0];
        Item i = reportViewer.getTable().getItem(itemID);

        HorizontalLayout projectHeaderLayout = new HorizontalLayout();
        projectHeaderLayout.addComponent(new Label("Projecto Nº: " + readProperty(i, "PROJECTO")));
        projectHeaderLayout.addComponent(new Label("Acrónimo: " + readProperty(i, "ACRONIMO")));
        projectHeaderLayout.addComponent(new Label("Unid Expl: " + readProperty(i, "UNIDEXPL")));
        addComponent(projectHeaderLayout);

        GridLayout projectResponsibleLayout = new GridLayout(2, 2);
        projectResponsibleLayout.addComponent(new Label("Nº Mecanográfico: " + readProperty(i, "NUMCOORDENADOR")));
        projectResponsibleLayout.addComponent(new Label("Nome: " + readProperty(i, "NOMECOORDENADOR")));
        projectResponsibleLayout.addComponent(new Label("ext.: " + readProperty(i, "CONTACTO")));
        projectResponsibleLayout.addComponent(new Label("Unid. Académica: " + readProperty(i, "UNIACAD") + " - "
                + readProperty(i, "UNIACADDESCRICAO")));
        addComponent(projectResponsibleLayout);

        GridLayout projectInfoLayout = new GridLayout(2, 12);
        projectInfoLayout.addComponent(new Label("Origem: " + readProperty(i, "ORIGEM")));
        projectInfoLayout.addComponent(new Label("Tipo: " + readProperty(i, "TIPO")));
        projectInfoLayout.addComponent(new Label("Custo: " + readProperty(i, "CUSTOS")));
        projectInfoLayout.addComponent(new Label("Coordenação: " + readProperty(i, "COORDENACAO")));
        projectInfoLayout.addComponent(new Label("Unid. Operacional: " + readProperty(i, "UNIDOPER") + " - "
                + readProperty(i, "UNIDOPERDESCRICAO")));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("Moeda: " + readProperty(i, "MOEDA")));
        projectInfoLayout.addComponent(new Label("NIB: " + readProperty(i, "NIB")));
        projectInfoLayout.addComponent(new Label("Nº Contrato: " + readProperty(i, "NUMCONTRACTO")));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("Nº Projecto Anterior: " + readProperty(i, "OLDPROJ")));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("DG: " + readProperty(i, "DG")));
        projectInfoLayout.addComponent(new Label("Programa: " + readProperty(i, "PROGRAMA") + " - "
                + readProperty(i, "PROGRAMADESCRICAO")));
        projectInfoLayout.addComponent(new Label("Início: " + readProperty(i, "INICIO")));
        projectInfoLayout.addComponent(new Label("Duração: " + readProperty(i, "DURACAO")));
        projectInfoLayout.addComponent(new Label("Título: "));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label(readProperty(i, "TITULO")));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label("Resumo: "));
        projectInfoLayout.newLine();
        projectInfoLayout.addComponent(new Label(readProperty(i, "RESUMO")));
        addComponent(projectInfoLayout);

        HorizontalLayout projectFinancingLayout = new HorizontalLayout();
        projectFinancingLayout.addComponent(new Label("Controlo Orçamental de Membros: " + readProperty(i, "CONTROLOORCAMMEMB")));
        projectFinancingLayout.addComponent(new Label("Contabilidade IVA: " + readProperty(i, "CONTIVA")));
        projectFinancingLayout.addComponent(new Label("IVA Elegível: " + readProperty(i, "CONTIVAILEGIVEL")));
        addComponent(projectFinancingLayout);

//Data Início   Org Gestão  Unid. Exploração    Unid. Académica     Unid. Operacional   Coordenador
//02/01/2005  10.0%   5.0%    4.0%    6.0%    0.0%
//\"OVHDATA\", \"OVHGESTAO\", \"OVHUNIDEXPL\", \"OVHUNIDACAD\", \"OVHUNIDOPER\", \"OVHCOORD\"
        GridLayout overheadDistributionLayout = new GridLayout(6, 2);
        overheadDistributionLayout.addComponent(new Label("Data Início"));
        overheadDistributionLayout.addComponent(new Label("Org Gestão"));
        overheadDistributionLayout.addComponent(new Label("Unid. Exploração"));
        overheadDistributionLayout.addComponent(new Label("Unid. Académica"));
        overheadDistributionLayout.addComponent(new Label("Unid. Operacional"));
        overheadDistributionLayout.addComponent(new Label("Coordenador"));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHDATA")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHGESTAO")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHUNIDEXPL")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHUNIDACAD")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHUNIDOPER")));
        overheadDistributionLayout.addComponent(new Label(readProperty(i, "OVHCOORD")));
        addComponent(overheadDistributionLayout);

        GridLayout projectConsorciumMembersBudgetLayout = new GridLayout();

        ReportViewerComponent financingEntitiesReportViewer =
                new ReportViewerComponent(
                        "select \"CODE\", \"DESCRIPTION\", \"VALUE\" from V_ENTIDADES_PROJECTO where PROJECTCODE='"
                                + getProjectCode() + "'", getCustomFormatter());

        addComponent(financingEntitiesReportViewer);

        ReportViewerComponent budgetByRubricReportViewer =
                new ReportViewerComponent(
                        "select \"CODE\", \"DESCRIPTION\", \"VALUE\" from V_ORCAMENTO_RUBRICA where PROJECTCODE='"
                                + getProjectCode() + "'", getCustomFormatter());
        addComponent(budgetByRubricReportViewer);

        ReportViewerComponent teamReportViewer =
                new ReportViewerComponent("select \"CODE\", \"DESCRIPTION\" from V_EQUIPA_INVESTIGACAO where PROJECTCODE='"
                        + getProjectCode() + "'", getCustomFormatter());
        addComponent(teamReportViewer);

        ReportViewerComponent consorciumMemberBudgetViewer =
                new ReportViewerComponent(
                        "select \"INSTITUICAO\", \"DESCRICAOINSTITUICAO\", \"TIPO\", \"OVH\", \"GRP\", \"PCTFINANCIAMENTO\" from V_MEMBROS_CONSORCIO where PROJECTO='"
                                + getProjectCode() + "'", getCustomFormatter());
        addComponent(consorciumMemberBudgetViewer);

    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        return reportViewer;
    }

    @Override
    public void write(HSSFSheet sheet) {
        //reportViewer.write(sheet);
        //tableSummary.write(sheet);
    }

    @Override
    public String getLabel() {
        return OPENING_PROJECT_FILE_LABEL;
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

}
