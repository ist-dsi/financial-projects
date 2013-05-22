package module.projects.presentationTier.vaadin.reportType;

import java.util.HashMap;
import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableLineFilterComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableNavigatorComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ExpensesReportType extends ReportType {
    ReportViewerComponent reportViwer;
    String filter;
    TableLineFilterComponent filterer;
    ReportViewerComponent eurRevenue, pteRevenue;
    TableSummaryComponent cabimentosSummary, adiantamentosSummary;

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
        filterer = new TableLineFilterComponent(rubricsMap, "projectsService", args, args.get("filter"));
        filter = args.get("filter");

        //filterer.setCurrentSelection(filter);
        reportViwer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        setVisibleColumns(reportViwer.getTable());
        addComponent(reportViwer);

        Table t = reportViwer.getTable();
        addComponent(new TableNavigatorComponent(t));
        addComponent(filterer);

        HorizontalLayout subLayout = new HorizontalLayout();
        subLayout.setWidth("100%");
        subLayout.setSpacing(true);

        eurRevenue =
                new ReportViewerComponent(
                        "select \"RECEITA\", \"DESPESA\", \"IVA\", \"AD_POR_JUST\", \"TOTAL\" from V_RESUMO_EURO where PROJECTCODE='"
                                + getProjectCode() + "'", getCustomFormatter());
        pteRevenue =
                new ReportViewerComponent(
                        "select \"RECEITA\", \"DESPESA\", \"IVA\", \"TOTAL\" from  V_RESUMO_PTE where PROJECTCODE='"
                                + getProjectCode() + "'", getCustomFormatter());

        addComponent(new Label("<b>" + getMessage("financialprojectsreports.label.treasury") + "</b>", Label.CONTENT_XHTML));
        subLayout.setStyleName("layout-grey-background");
        subLayout.addComponent(tableToComponent(eurRevenue.getTable(), getMessage("financialprojectsreports.label.eur")));
        subLayout.addComponent(tableToComponent(pteRevenue.getTable(), getMessage("financialprojectsreports.label.pte")));
        addComponent(subLayout);

        cabimentosSummary = ReportType.getReportFromType(ReportType.CABIMENTOS_STRING, args, project).getSummary();
        cabimentosSummary.setStyleName("layout-grey-background");
        adiantamentosSummary = ReportType.getReportFromType(ReportType.ADIANTAMENTOS_STRING, args, project).getSummary();
        adiantamentosSummary.setStyleName("layout-grey-background");

        addComponent(adiantamentosSummary);
        addComponent(new Label(getMessage("financialprojectsreports.adiantamentosWarning")));
        addComponent(cabimentosSummary);
        addComponent(new Label(getMessage("financialprojectsreports.cabimentosWarning")));

    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        reportViwer.write(sheet, headersFont);

        //write applied filter
        filterer.write(sheet, headersFont);
        //write eurRevenue, pteRevenue
        sheet.createRow(sheet.getLastRowNum() + 2).createCell(0)
                .setCellValue(getMessage("financialprojectsreports.expensescalculationwarning"));

        HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFont(headersFont);

        HSSFCell cell = sheet.createRow(sheet.getLastRowNum() + 2).createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue(getMessage("financialprojectsreports.label.revenue") + " "
                + getMessage("financialprojectsreports.label.eur"));
        eurRevenue.write(sheet, headersFont);

        cell = sheet.createRow(sheet.getLastRowNum() + 2).createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue(getMessage("financialprojectsreports.label.revenue") + " "
                + getMessage("financialprojectsreports.label.pte"));
        pteRevenue.write(sheet, headersFont);

        adiantamentosSummary.write(sheet, headersFont);
        sheet.createRow(sheet.getLastRowNum() + 2).createCell(0)
                .setCellValue(getMessage("financialprojectsreports.adiantamentosWarning"));

        cabimentosSummary.write(sheet, headersFont);
        sheet.createRow(sheet.getLastRowNum() + 2).createCell(0)
                .setCellValue(getMessage("financialprojectsreports.cabimentosWarning"));
    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.expenses");
    }

    @Override
    public String getQuery() {
        String query =

                "SELECT \"id Mov.\", \"Membro Cons.\", \"desc Fornecedor\", \"Tipo Doc.\", \"Nº Doc.\",\"Fonte Financ.\", \"Rubrica\", \"Tipo Mov.\", TO_CHAR(\"Data Doc\",'YYYY-MM-DD') as DATA, \"Descrição\", \"pct Iva\",\"Valor\", \"IVA\", \"Total\", \"pct imput.\" FROM v_mov_tesour_eur_completos WHERE PROJECTCODE='"
                        + getProjectCode() + "' ";
        if (filter != null) {
            query += "AND \"Rubrica\"='" + filter + "' ";
        }
        query += " order by \"Data Doc\", \"id Mov.\"";
        return query;
    }

    public Component tableToComponent(Table t, String tableName) {
        Layout layout = new VerticalLayout();
        Object itemId = t.getItemIds().toArray()[0];
        for (String column : t.getColumnHeaders()) {
            layout.addComponent(new Label("<b>" + column + " " + tableName + ":</b> "
                    + t.getItem(itemId).getItemProperty(column).toString(), Label.CONTENT_XHTML));
        }
        return layout;
    }

    @Override
    public ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setVisibleColumns(Table table) {
        table.setVisibleColumns(new String[] { "id Mov.", "Membro Cons.", "Rubrica", "Tipo Mov.", "DATA", "Descrição", "Valor",
                "IVA", "Total" });
    }

    public void setColumnNames(Table table) {
        table.setColumnHeader("ID MOV.", getMessage("financialprojectsreports.expenses.column.id"));
        table.setColumnHeader("MEMBRO CONS.", getMessage("financialprojectsreports.expenses.column.member"));
        table.setColumnHeader("DESC FORNECEDOR", getMessage("financialprojectsreports.expenses.column.suplier"));
        table.setColumnHeader("TIPO DOC.", getMessage("financialprojectsreports.expenses.column.docType"));
        table.setColumnHeader("Nº DOC.", getMessage("financialprojectsreports.expenses.column.docNumber"));
        table.setColumnHeader("FONTE FINANC.", getMessage("financialprojectsreports.expenses.column.financialSource"));
        table.setColumnHeader("RUBRICA", getMessage("financialprojectsreports.expenses.column.rubric"));
        table.setColumnHeader("TIPO MOV.", getMessage("financialprojectsreports.expenses.column.movType"));
        table.setColumnHeader("DATA", getMessage("financialprojectsreports.expenses.column.docData"));
        table.setColumnHeader("DESCRIÇÃO", getMessage("financialprojectsreports.expenses.column.description"));
        table.setColumnHeader("PCT IVA", getMessage("financialprojectsreports.expenses.column.ivapct"));
        table.setColumnHeader("VALOR", getMessage("financialprojectsreports.expenses.column.value"));
        table.setColumnHeader("IVA", getMessage("financialprojectsreports.expenses.column.iva"));
        table.setColumnHeader("TOTAL", getMessage("financialprojectsreports.expenses.column.total"));
        table.setColumnHeader("PCT IMPUT.", getMessage("financialprojectsreports.expenses.column.imptpct"));

    }
}
