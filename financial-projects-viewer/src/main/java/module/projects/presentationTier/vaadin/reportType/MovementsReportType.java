package module.projects.presentationTier.vaadin.reportType;

import java.util.HashMap;
import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.components.ReportViewerComponent;
import module.projects.presentationTier.vaadin.reportType.components.TableSummaryComponent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public abstract class MovementsReportType extends ReportType {
    ReportViewerComponent reportViewer;
    TableSummaryComponent tableSummary;

    public MovementsReportType(Map<String, String> args, Project project) {
        super(args, project);
        reportViewer = new ReportViewerComponent(getQuery(), getCustomFormatter());
        addComponent(reportViewer);
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

    protected ReportViewerComponent getReportViewer() {
        return reportViewer;
    }

    protected void setTableSummaryReport(TableSummaryComponent table) {
        tableSummary = table;
        addComponent(tableSummary);
    }

    @Override
    public void write(HSSFSheet sheet) {
        reportViewer.write(sheet);
        tableSummary.write(sheet);

        HashMap<String, String> fakeArguments = getArgs();
        fakeArguments.put("reportType", "cabimentosDetailsReport");

        Table t = reportViewer.getTable();

        for (Object itemId : t.getItemIds()) {
            Item item = t.getItem(itemId);
            String parentID = item.getItemProperty("PAI_IDMOV").getValue().toString();

            int rowNum = reportViewer.writeHeader(sheet);
            HSSFRow row = sheet.createRow(rowNum);
            int i = 0;
            for (Object propertyId : item.getItemPropertyIds()) {
                Property p = item.getItemProperty(propertyId);
                HSSFCell cell = row.createCell(i++);
                cell.setCellValue(p.getValue().toString());
            }

            fakeArguments.put("PAI_IDMOV", parentID);
            ReportType subReport = ReportType.getReportFromType("cabimentosDetailsReport", fakeArguments, getProject());
            subReport.write(sheet);
        }
    }

    @Override
    public TableSummaryComponent getSummary() {
        return tableSummary;
    }
}
