package module.projects.presentationTier.vaadin.reportType.movementReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.MovementsReportType;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import com.vaadin.ui.Table;

public class CabimentosReportType extends MovementsReportType {

    public CabimentosReportType(Map<String, String> args) {
        super(args);
        setTableSummaryReport(new TableSummaryComponent(getReportViewer().getTable(), getLabel(), "PAI_VALOR_TOTAL",
                "TOTAL_EXECUCOES", "EXECUCOES_EM_FALTA"));

    }

    @Override
    public String getLabel() {
        return getMessage("financialprojectsreports.reportTitle.cabimentosListing");
    }

    @Override
    public String getQuery() {
        return "select \"PAI_IDMOV\", \"PAI_IDRUB\", \"PAI_TIPO\", TO_CHAR(\"PAI_DATA\",'YYYY-MM-DD') as DATA, \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\",SUM(FILHO_VALOR) + SUM(FILHO_IVA) as TOTAL_EXECUCOES, \"PAI_VALOR_TOTAL\" - SUM(FILHO_VALOR) - SUM(FILHO_IVA) EXECUCOES_EM_FALTA from \"V_MOV_CABIMENTOS\" where \"PAI_IDPROJ\"='"
                + getProjectCode()
                + "' group by \"PAI_IDMOV\", \"PAI_TIPO\", \"PAI_IDPROJ\", \"PAI_IDRUB\", \"PAI_DATA\", \"PAI_DESCRICAO\", \"PAI_VALOR_TOTAL\"";
    }

    @Override
    public void setColumnNames(Table table) {
        table.setColumnHeader("PAI_IDMOV", getMessage("financialprojectsreports.column.id"));
        table.setColumnHeader("PAI_IDRUB", getMessage("financialprojectsreports.column.rubric"));
        table.setColumnHeader("PAI_TIPO", getMessage("financialprojectsreports.column.type"));
        table.setColumnHeader("DATA", getMessage("financialprojectsreports.column.date"));
        table.setColumnHeader("PAI_DESCRICAO", getMessage("financialprojectsreports.column.description"));
        table.setColumnHeader("PAI_VALOR_TOTAL", getMessage("financialprojectsreports.column.value"));
        table.setColumnHeader("TOTAL_EXECUCOES", getMessage("financialprojectsreports.cabimentos.column.executed"));
        table.setColumnHeader("EXECUCOES_EM_FALTA", getMessage("financialprojectsreports.column.missing"));
    }

    @Override
    protected String getChildReportName() {
        return "cabimentosDetailsReport";
    }

    @Override
    public String getTypeName() {
        return "Cabimento";
    }

    @Override
    public String getChildTypeName() {
        return "Execuções";
    }

}
