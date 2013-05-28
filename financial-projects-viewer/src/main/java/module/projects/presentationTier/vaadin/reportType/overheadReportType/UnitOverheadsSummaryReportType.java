package module.projects.presentationTier.vaadin.reportType.overheadReportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.UnitOverheadsReportType;
import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.vaadin.ui.Panel;

public class UnitOverheadsSummaryReportType extends UnitOverheadsReportType {
    String costCenterCoordinator;

    public UnitOverheadsSummaryReportType(Map<String, String> args) {
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
        return "select \"ANO\", \"UE\", \"COST_CENTER\", \"REC_OG\", \"OVH_OG\", \"REC_OA\", \"OVH_OA\",\"REC_OO\", \"OVH_OO\", \"REC_OE\", \"OVH_OE\", \"TOTAL_OVH\", \"OVH_TRANSF\", \"SALDO\" from V_OVH_RESUMO where CC_COORD='"
                + getCostCenterCoordinator() + "' order by \"ANO\", \"UE\"";

    }

    @Override
    protected ReportViewerComponent getReportViewer() {
        // TODO Auto-generated method stub
        return null;
    }

}
