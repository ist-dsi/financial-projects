package module.projects.presentationTier.vaadin.reportType.overheadReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.UnitOverheadsReportType;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.ui.Panel;

public class UnitTransferedOverheadsReportType extends UnitOverheadsReportType {
    String costCenterCoordinator;

    public UnitTransferedOverheadsReportType(Map<String, String> args) {
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

        return "select \"UE\", \"IDMOV\", \"DATE_AUTOR\", \"TIPO\", \"DESCRICAO\", \"VALOR\" from V_OVH_TRANSFERIDOS where CC_COORD='"
                + getCostCenterCoordinator() + "' order by \"DATE_AUTOR\", \"UE\", \"IDMOV\"";

    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }

}
