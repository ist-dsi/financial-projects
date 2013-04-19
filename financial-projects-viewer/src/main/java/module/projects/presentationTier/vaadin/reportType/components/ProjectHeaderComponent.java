package module.projects.presentationTier.vaadin.reportType.components;

import module.projects.presentationTier.vaadin.Reportable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class ProjectHeaderComponent extends CustomComponent implements Reportable {
    Label acronym, accountManager, projectID, mobileNumber, projectType, email, coordinator, date;

    ReportableGridLayout subLayout;

    public ProjectHeaderComponent(String reportTypeLabel, Project project) {
        /*
         * Acrónimo:    CPAR
        Projecto Nº:    20NCML2006
        Tipo:   C - Contr/Prest de Serviços
        Coordenador:    ARLINDO MANUEL LIMEDE DE OLIVEIRA
        Data:   22-03-2013 às 15:00
        
        Gestor de conta:    Xxx XxxXxx
        Telefone/Ext.:  

        21221595899

        Email:  

        suporte@dotist.utl.pt
         */
        Panel p = new Panel();
        Layout layout = new VerticalLayout();
        layout.addComponent(new Label(reportTypeLabel));

        subLayout = new ReportableGridLayout(4, 5);
        subLayout.setSpacing(true);
        layout.addComponent(subLayout);

        acronym = new Label(project.getShortIdentifier() + " ");
        accountManager = new Label(project.getAccountManager().getName() + " ");
        projectID = new Label(project.getExternalId() + " ");
        mobileNumber = new Label("??? ");
        projectType = new Label(project.getType() + " ");
        email = new Label("??? ");
        coordinator = new Label("?? ");
        date = new Label("?? ");

        subLayout.addComponent(new Label("Acronimo "));
        subLayout.addComponent(acronym);
        subLayout.addComponent(new Label("Gestor de conta "));
        subLayout.addComponent(accountManager);
        subLayout.addComponent(new Label("Nº Projecto"));
        subLayout.addComponent(projectID);
        subLayout.addComponent(new Label("telefone/ext"));
        subLayout.addComponent(mobileNumber);
        subLayout.addComponent(new Label("Tipo "));
        subLayout.addComponent(projectType);
        subLayout.addComponent(new Label("e-mail "));
        subLayout.addComponent(email);
        subLayout.addComponent(new Label("Coordenador "));
        subLayout.addComponent(coordinator);
        subLayout.addComponent(new Label(" "));
        subLayout.addComponent(new Label(" "));
        subLayout.addComponent(new Label("Data "));
        subLayout.addComponent(date);

        setCompositionRoot(layout);
    }

    @Override
    public void write(HSSFSheet sheet) {
        /*
        int rowNum = sheet.getLastRowNum() + 2;
        HSSFRow row = sheet.createRow(rowNum++);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue((String) acronym.getValue());
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue((String) accountManager.getValue());
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue((String) projectID.getValue());
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue((String) mobileNumber.getValue());
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue((String) projectType.getValue());
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue((String) email.getValue());
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue((String) coordinator.getValue());
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue((String) date.getValue());
        */
        subLayout.write(sheet);
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
}
