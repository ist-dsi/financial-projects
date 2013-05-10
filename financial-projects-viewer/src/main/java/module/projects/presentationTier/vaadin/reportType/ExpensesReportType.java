package module.projects.presentationTier.vaadin.reportType;

import java.util.HashMap;
import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableLineFilterComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableNavigatorComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ExpensesReportType extends ReportType {
    ReportViewerComponent reportViwer;
    String filter;

    protected ExpensesReportType(Map<String, String> args, Project project) {
        super(args, project);

        ReportViewerComponent tempReportViewer =
                new ReportViewerComponent(
                        "SELECT DISTINCT r.COD, r.DESCRICAO FROM v_rub_despesa r, V_LST_TODOS_MOV_TESOUR_EUR p where p.PROJECTCODE='"
                                + getProjectCode() + "' and p.\"Rubrica\"=r.COD", getCustomFormatter());
        Table rubricsTable = tempReportViewer.getTable();
        Map<String, String> rubricsMap = new HashMap<String, String>();
        for (Object itemId : rubricsTable.getItemIds()) {
            String code = rubricsTable.getItem(itemId).getItemProperty("COD").toString();
            String description = rubricsTable.getItem(itemId).getItemProperty("DESCRICAO").toString();
            rubricsMap.put(description + " - " + code, code);
        }
        TableLineFilterComponent filterer = new TableLineFilterComponent(rubricsMap, "projectsService", args, args.get("filter"));
        filter = args.get("filter");

        //filterer.setCurrentSelection(filter);
        reportViwer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViwer);
        Table t = reportViwer.getTable();
        addComponent(new TableNavigatorComponent(t));
        addComponent(filterer);

        Table eurRevenue =
                new ReportViewerComponent(
                        "select \"RECEITA\", \"DESPESA\", \"IVA\", \"AD_POR_JUST\", \"TOTAL\" from V_RESUMO_EURO where PROJECTCODE='"
                                + getProjectCode() + "'", getCustomFormatter()).getTable();
        Table pteRevenue =
                new ReportViewerComponent(
                        "select \"RECEITA\", \"DESPESA\", \"IVA\", \"TOTAL\" from  V_RESUMO_PTE where PROJECTCODE='"
                                + getProjectCode() + "'", getCustomFormatter()).getTable();

        addComponent(tableToComponent(eurRevenue));
        addComponent(tableToComponent(pteRevenue));

        TableSummaryComponent cabimentosSummary =
                ReportType.getReportFromType(ReportType.CABIMENTOS_STRING, args, project).getSummary();
        TableSummaryComponent adiantamentosSummary =
                ReportType.getReportFromType(ReportType.ADIANTAMENTOS_STRING, args, project).getSummary();

        addComponent(cabimentosSummary);
        addComponent(adiantamentosSummary);

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

                "SELECT \"id Mov.\", \"Membro Cons.\", \"Fornecedor\", \"desc Fornecedor\", \"Tipo Doc.\", \"Nº Doc.\",\"Fonte Financ.\", \"Rubrica\", \"Tipo Mov.\", \"Data Doc\", \"Descrição\", \"pct Iva\",\"Valor\", \"IVA\", \"Total\", \"pct imput.\" FROM v_mov_tesour_eur_completos WHERE PROJECTCODE='"
                        + getProjectCode() + "' ";
        if (filter != null) {
            query += "AND \"Rubrica\"='" + filter + "' ";
        }
        query += " order by \"Data Doc\", \"id Mov.\"";
        return query;
    }

    public Component tableToComponent(Table t) {
        Layout layout = new VerticalLayout();
        //this code only works in this scenario in which we know one line will be at the table
        Object itemId = t.getItemIds().toArray()[0];
        for (String column : t.getColumnHeaders()) {
            layout.addComponent(new Label(column + " " + t.getItem(itemId).getItemProperty(column).toString()));
        }
        return layout;
    }

    @Override
    public ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }
}
