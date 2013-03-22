package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.data.Property;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class AdiantamentosReportType extends ReportType {

    public AdiantamentosReportType(Map<String, String> args, Project project) {
        // TODO Auto-generated constructor stub
        super(args, project);
        addComponent(new ReportViewerComponent(getQuery(), getCustomFormatter()));
    }

    @Override
    public String getQuery() {
        return "select distinct \"PAI_IDMOV\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_TIPO\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\" from \"V_MOV_ADIANTAMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode() + "' order by \"PAI_IDMOV\"";
    }

    @Override
    public CustomTableFormatter getCustomFormatter() {
        return new CustomTableFormatter() {
            @Override
            public void format(Table table) {
                table.addGeneratedColumn("Details", new ColumnGenerator() {
                    @Override
                    public Object generateCell(Table source, Object itemId, Object columnId) {
                        Property paiIDMOV = source.getItem(itemId).getItemProperty("PAI_IDMOV");
                        Link detailsLink =
                                new Link("Details", new ExternalResource(
                                        "#projectsService?reportType=adiantamentosDetailsReport&unit=" + getProjectID()
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
        return ADIANTAMENTOS_LABEL;
    }
}
