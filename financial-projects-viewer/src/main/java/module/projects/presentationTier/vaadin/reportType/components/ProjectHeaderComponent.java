package module.projects.presentationTier.vaadin.reportType.components;

import module.projects.presentationTier.vaadin.Reportable;
import module.projects.presentationTier.vaadin.reportType.ReportType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.joda.time.DateTime;

import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.expenditureTrackingSystem.domain.organization.Person;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ProjectHeaderComponent extends CustomComponent implements Reportable {
    Label acronym, accountManager, projectID, mobileNumber, projectType, email, coordinator, date;

    ReportableGridLayout subLayout;

    public ProjectHeaderComponent(String reportTypeLabel, Project project) {

        String query =
                "select title, c.nome, tp.descricao, p.origem, p.tipo, p.custo, p.coordenacao, p.UNID_EXPLORACAO, p.gestor from V_Projectos p, V_COORD c , V_TIPOS_PROJECTOS tp  where p.idCoord = c.idCoord and tp.cod = p.tipo and p.projectCode ='"
                        + project.getProjectCode() + "'";
        ReportViewerComponent reportViewer = new ReportViewerComponent(query, new ReportType.NoBehaviourCustomTableFormatter());

        Table table = reportViewer.getTable();
        Object itemId = table.getItemIds().toArray()[0];
        Item item = table.getItem(itemId);

        Layout layout = new VerticalLayout();
        layout.addComponent(new Label("<h3><b>" + reportTypeLabel + "</b></h3>", Label.CONTENT_XHTML));

        subLayout = new ReportableGridLayout(4, 5);
        subLayout.setSpacing(true);
        layout.addComponent(subLayout);

        Person manager = User.findByUsername(readProperty(item, "GESTOR")).getExpenditurePerson();

        subLayout
                .addComponent(getMessageBlacked("financialprojectsreports.header.label.acronymfinancialprojectsreports.header.label.acronym"));
        subLayout.addComponent(new Label(readProperty(item, "TITLE")));
        subLayout
                .addComponent(getMessageBlacked("financialprojectsreports.header.label.acronymfinancialprojectsreports.header.label.accountManager"));
        subLayout.addComponent(new Label(manager.getFirstAndLastName()));
        subLayout
                .addComponent(getMessageBlacked("financialprojectsreports.header.label.acronymfinancialprojectsreports.header.label.projectNumber"));
        subLayout.addComponent(new Label(readProperty(item, "UNID_EXPLORACAO") + readProperty(item, "ORIGEM")
                + readProperty(item, "TIPO") + readProperty(item, "CUSTO") + readProperty(item, "COORDENACAO")
                + project.getExternalId()));
        subLayout
                .addComponent(getMessageBlacked("financialprojectsreports.header.label.acronymfinancialprojectsreports.header.label.id"));
        subLayout.addComponent(new Label(readProperty(item, "GESTOR")));
        subLayout
                .addComponent(getMessageBlacked("financialprojectsreports.header.label.acronymfinancialprojectsreports.header.label.type"));
        subLayout.addComponent(new Label(readProperty(item, "TIPO") + " - " + readProperty(item, "DESCRICAO")));
        subLayout
                .addComponent(getMessageBlacked("financialprojectsreports.header.label.acronymfinancialprojectsreports.header.label.email"));
        subLayout.addComponent(new Label(manager.getEmail()));
        subLayout
                .addComponent(getMessageBlacked("financialprojectsreports.header.label.acronymfinancialprojectsreports.header.label.coordinator"));
        subLayout.addComponent(new Label(readProperty(item, "NOME")));
        subLayout.addComponent(new Label(""));
        subLayout.addComponent(new Label(""));

        subLayout
                .addComponent(getMessageBlacked("financialprojectsreports.header.label.acronymfinancialprojectsreports.header.label.date"));
        subLayout.addComponent(new Label(new DateTime().toString()));
        setCompositionRoot(layout);
    }

    @Override
    public void write(HSSFSheet sheet) {
        subLayout.write(sheet);
    }

    String readProperty(Item i, Object propertyID) {
        Object obj = i.getItemProperty(propertyID);
        if (obj == null) {
            return "";
        }
        String value = obj.toString();
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    class ReportableGridLayout extends GridLayout implements Reportable {

        public ReportableGridLayout(int i, int j) {
            super(i, j);
        }

        @Override
        public void write(HSSFSheet sheet) {
            int rowNum = sheet.getLastRowNum() + 2;
            for (int i = 0; i < getRows(); i++) {
                HSSFRow row = sheet.createRow(rowNum++);
                for (int j = 0; j < getColumns(); j++) {
                    HSSFCell cell = row.createCell(j * 2);
                    Component c = getComponent(j, i);
                    if (c != null) {
                        cell.setCellValue(c.toString());
                    }
                }
            }

        }
    }

    public Label getMessageBlacked(String message) {
        return new Label(
                "<b>" + BundleUtil.getFormattedStringFromResourceBundle("resources/projectsResources", message) + "</b>",
                Label.CONTENT_XHTML);
    }
}
