package module.projects.presentationTier.vaadin.reportType.movementReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.MovementsDetailsReportType;

import com.vaadin.ui.Table;

public class AdiantamentosDetailsReportType extends MovementsDetailsReportType {

    public AdiantamentosDetailsReportType(Map<String, String> args) {
        // TODO Auto-generated constructor stub
        super(args);
        //setTableSummaryReport(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "TOTAL"));
    }

    @Override
    public String getQuery() {
        // TODO Auto-generated method stub
        return "select distinct \"FILHO_IDMOV\", \"FILHO_IDRUB\", \"FILHO_TIPO\", \"FILHO_DATA\", \"FILHO_DESCRICAO\", \"FILHO_VALOR\", \"FILHO_IVA\", \"FILHO_VALOR\" + \"FILHO_IVA\" as TOTAL from \"V_MOV_ADIANTAMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode() + "' AND \"PAI_IDMOV\"='" + getParentId() + "'";
    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.adiantamentosDetailsListing");
    }

    @Override
    public void setColumnNames(Table table) {
        table.setColumnHeader("FILHO_IDMOV", getMessage("financialprojectsreports.column.id"));
        table.setColumnHeader("FILHO_IDRUB", getMessage("financialprojectsreports.column.rubric"));
        table.setColumnHeader("FILHO_TIPO", getMessage("financialprojectsreports.column.type"));
        table.setColumnHeader("FILHO_DATA", getMessage("financialprojectsreports.column.date"));
        table.setColumnHeader("PAI_IDMOV", getMessage("financialprojectsreports.column.description"));
        table.setColumnHeader("FILHO_VALOR", getMessage("financialprojectsreports.column.base"));
        table.setColumnHeader("FILHO_IVA", getMessage("financialprojectsreports.column.iva"));
        table.setColumnHeader("TOTAL", getMessage("financialprojectsreports.column.value"));

    }
}
