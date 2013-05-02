package module.projects.presentationTier.vaadin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.ReportType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;
import pt.ist.expenditureTrackingSystem.domain.organization.SubProject;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.vaadinframework.annotation.EmbeddedComponent;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;

import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;

@EmbeddedComponent(path = { "projectsService" })
public class ProjectsComponent extends CustomComponent implements EmbeddedComponentContainer {
    Layout layout;
    Panel panel;
    ReportType reportType;

    public ProjectsComponent() {
        layout = new HorizontalLayout();
        panel = new Panel(layout);

        setCompositionRoot(panel);

    }

    @Override
    public void setArguments(Map<String, String> arguments) {
        // TODO Auto-generated method stub
        String projectID = arguments.get("unit");
        String reportTypeString = arguments.get("reportType");
        Project project = getProjectFromID(projectID);

        reportType = ReportType.getReportFromType(reportTypeString, arguments, project);

        if (reportType != null && project != null && project.isResponsible(UserView.getCurrentUser().getExpenditurePerson())) {
            layout.addComponent(reportType.getComponent(project.getProjectCode()));
        }
        Button testButton = new Button("Excel Report");
        testButton.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                exportToExcel();
            }
        });
        panel.addComponent(testButton);

    }

    @Override
    public boolean isAllowedToOpen(Map<String, String> parameters) {
        // TODO Auto-generated method stub
        return true;
    }

    private Project getProjectFromID(String projectID) {
        Unit project = FenixFramework.getDomainObject(projectID);
        if (project instanceof Project) {
            return (Project) project;
        } else if (project instanceof SubProject) {
            return (Project) ((SubProject) project).getParentUnit();
        }
        return null;
    }

    public void exportToExcel() {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("My report");
        sheet.setGridsPrinted(false);

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);

        cell.setCellValue(reportType.getLabel());

        reportType.getHeader().write(sheet);
        reportType.write(sheet);

        byte[] buffer = new byte[256];
        ByteArrayInputStream inStream = new ByteArrayInputStream(buffer);
        DownloadStream downStream = new DownloadStream(inStream, "xls", "report");
        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        StreamResource.StreamSource source = new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                byte[] buffer = new byte[256];
                ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
                return inStream;
            }
        };

        try {
            wb.write(outStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StreamResource resource = new StreamResource(source, "report.xls", panel.getApplication());
        panel.getWindow().open(resource, "myname");
    }

}
