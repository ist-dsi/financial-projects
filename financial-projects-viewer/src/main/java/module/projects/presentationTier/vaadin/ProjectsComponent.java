package module.projects.presentationTier.vaadin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.ReportType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;
import pt.ist.expenditureTrackingSystem.domain.organization.SubProject;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.vaadinframework.annotation.EmbeddedComponent;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;

import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

@EmbeddedComponent(path = { "projectsService" })
public class ProjectsComponent extends CustomComponent implements EmbeddedComponentContainer {
    Layout layout;

    ReportType reportType;

    public ProjectsComponent() {

        layout = new VerticalLayout();
        setCompositionRoot(layout);

    }

    @Override
    public void setArguments(Map<String, String> arguments) {
        // TODO Auto-generated method stub
        String projectID = arguments.get("unit");
        String reportTypeString = arguments.get("reportType");
        Project project = getProjectFromID(projectID);

        reportType = ReportType.getReportFromType(reportTypeString, arguments, project);

        if (reportType != null && project != null && project.isResponsible(UserView.getCurrentUser().getExpenditurePerson())) {

            Button generateExcellButton =
                    new Button(BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources",
                            "financialprojectsreports.button.excelExport"));
            generateExcellButton.setIcon(new ThemeResource("icons/excel.gif"));
            generateExcellButton.addListener(new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    exportToExcel();
                }
            });
            if (reportType.isToExport()) {
                layout.addComponent(generateExcellButton);
            }
            layout.addComponent(reportType.getComponent(project.getProjectCode()));
        }

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
        HSSFFont headersFont = wb.createFont();
        headersFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(headersFont);

        HSSFSheet sheet = wb.createSheet(reportType.getLabel());
        sheet.setGridsPrinted(false);

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);

        cell.setCellValue(reportType.getLabel());
        cell.setCellStyle(style);

        if (reportType.getHeader() != null) {
            reportType.getHeader().write(sheet, headersFont);
        }

        reportType.write(sheet, headersFont);

        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        StreamResource.StreamSource source = new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
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
        StreamResource resource = new StreamResource(source, reportType.getLabel() + ".xls", layout.getApplication());
        layout.getWindow().open(resource, reportType.getLabel());
    }

}
