package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.data.Property;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class CabimentosReportType extends ReportType {

    public CabimentosReportType(Map<String, String> args, Project project) {
        super(args, project);
        ReportViewerComponent reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);
        addComponent(new TableSummaryComponent(reportViewer.getTable(), getLabel(), "PAI_VALOR_TOTAL", "TOTAL_EXECUCOES",
                "EXECUCOES_EM_FALTA"));
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
}
