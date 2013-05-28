package module.projects.presentationTier.vaadin.reportType.overheadReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.UnitOverheadsReportType;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.ui.Panel;

public class UnitGeneratedOverheadsReportType extends UnitOverheadsReportType {
    String costCenterCoordinator;

    public UnitGeneratedOverheadsReportType(Map<String, String> args) {
        super(args);
        Panel panel = new Panel();
        ReportViewerComponent reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        panel.addComponent(reportViewer);
        panel.getContent().setSizeUndefined();
        addComponent(panel);
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getQuery() {
        return "select \"UNID_EXPL\", \"ID_PROJ\", \"ACRONIMO\", \"ID_COORD\", \"NOME\", \"TIPO_OVH\", \"DATE_AUTOR\", \"DESCRICAO\", \"VALOR_RECEITA\", \"PCT_OVH\", \"VALOR_OVH\" from V_OVH_GERADOS where cc_coord='"
                + getCostCenterCoordinator() + "' order by \"DATE_AUTOR\", \"UNID_EXPL\", \"ID_PROJ\"";

    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }

}
