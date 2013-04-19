package module.projects.presentationTier.vaadin.reportType;

import java.util.HashMap;
import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class CabimentosReportType extends ReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public CabimentosReportType(Map<String, String> args, Project project) {
        super(args, project);
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);
        tableSummary =
                new TableSummaryComponent(reportViewer.getTable(), getLabel(), "PAI_VALOR_TOTAL", "TOTAL_EXECUCOES",
                        "EXECUCOES_EM_FALTA");
        addComponent(tableSummary);
    }

    @Override
    public String getQuery() {
        return "select \"PAI_IDMOV\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\",SUM(FILHO_VALOR) + SUM(FILHO_IVA) as TOTAL_EXECUCOES, \"PAI_VALOR_TOTAL\" - SUM(FILHO_VALOR) - SUM(FILHO_IVA) EXECUCOES_EM_FALTA from \"V_MOV_CABIMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode()
                + "' group by \"PAI_IDMOV\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\"";

    }

    @Override
    public CustomTableFormatter getCustomFormatter() {
        return new CustomTableFormatter() {
            @Override
            public void format(Table table) {
                table.addGeneratedColumn("mycolumn", new ColumnGenerator() {
                    @Override
                    public Object generateCell(Table source, Object itemId, Object columnId) {
                        Property paiIDMOV = source.getItem(itemId).getItemProperty("PAI_IDMOV");
                        Link detailsLink =
                                new Link("Details", new ExternalResource(
                                        "#projectsService?reportType=cabimentosDetailsReport&unit=" + getProjectID()
                                                + "&PAI_IDMOV=" + paiIDMOV));
                        //line.addItemProperty(columnId, new ObjectProperty<Link>(detailsLink));
                        return detailsLink;
                    }
                });
            }
        };

    }

    @Override
    public String getLabel() {
        return CABIMENTOS_LABEL;
    }

    @Override
    public void write(HSSFSheet sheet) {
        reportViewer.write(sheet);
        tableSummary.write(sheet);

        HashMap<String, String> fakeArguments = getArgs();
        fakeArguments.put("reportType", "cabimentosDetailsReport");
        //fakeArguments.put("PAI_IDMOV", value)
        Table t = reportViewer.getTable();

        for (Object i : t.getItemIds()) {
            Item item = t.getItem(i);
            String parentID = item.getItemProperty("PAI_IDMOV").getValue().toString();
            fakeArguments.put("PAI_IDMOV", parentID);
            ReportType subReport = ReportType.getReportFromType("cabimentosDetailsReport", fakeArguments, getProject());
            subReport.write(sheet);
        }
    }
}
