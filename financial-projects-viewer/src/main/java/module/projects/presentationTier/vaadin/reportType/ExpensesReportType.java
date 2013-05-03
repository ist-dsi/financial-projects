package module.projects.presentationTier.vaadin.reportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableLineFilterComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableNavigatorComponent;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.Table;

public class ExpensesReportType extends ReportType {
    ReportViewerComponent reportViwer;
    String filter;
    static final List<String> filterOptions = new ArrayList<String>();

    {
        filterOptions.add("TR");
        filterOptions.add("ES");
        filterOptions.add("PB");
        filterOptions.add("DS");
        filterOptions.add("OE");
        filterOptions.add("DE");
        filterOptions.add("JU");
        filterOptions.add("OO");
        filterOptions.add("OG");
        filterOptions.add("OA");
    }

    protected ExpensesReportType(Map<String, String> args, Project project) {
        super(args, project);
        filter = args.get("filter");
        if (filter != null && !filterOptions.contains(filter)) {
            throw new RuntimeException("Invalid Option");
        }
        reportViwer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViwer);
        Table t = reportViwer.getTable();
        addComponent(new TableNavigatorComponent(t));

        TableLineFilterComponent filterer = new TableLineFilterComponent(t, filterOptions, "projectsService", args);
        addComponent(filterer);
    }

    @Override
    public void write(HSSFSheet sheet) {
        reportViwer.write(sheet);
    }

    @Override
    public String getLabel() {
        return EXPENSES_LABEL;
    }

    @Override
    public String getQuery() {
        String query =
                "SELECT \"idMov\", \"Membro\", \"Rubrica\", \"Tipo\", \"data\", \"Descrição\", \"Valor\", \"Iva\", \"Total\" FROM V_LST_TODOS_MOV_TESOUR_EUR WHERE PROJECTCODE='"
                        + getProjectCode() + "' ";
        if (filter != null) {
            query += "AND \"Tipo\"='" + filter + "' ";
        }
        query += " order by \"data\", \"idMov\"";
        return query;
    }
}
