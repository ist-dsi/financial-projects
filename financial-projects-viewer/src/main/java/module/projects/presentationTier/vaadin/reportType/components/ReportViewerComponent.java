package module.projects.presentationTier.vaadin.reportType.components;

import java.sql.SQLException;

import module.projects.presentationTier.vaadin.Reportable;
import module.projects.presentationTier.vaadin.reportType.ReportType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.bennu.core._development.PropertiesManager;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

public class ReportViewerComponent extends CustomComponent implements Reportable {
    Table viewTable;
    SQLContainer reportData;
    TableQuery tableQuery;

    public ReportViewerComponent(String queryString, ReportType.CustomTableFormatter formatter) {

        try {
            //needed to assure the class is loaded and registred as a driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            SimpleJDBCConnectionPool connectionPool =
                    new SimpleJDBCConnectionPool(PropertiesManager.getProperty("db.projectManagement.driver"),
                            PropertiesManager.getProperty("db.projectManagement.alias"),
                            PropertiesManager.getProperty("db.projectManagement.user"),
                            PropertiesManager.getProperty("db.projectManagement.pass"), 2, 5);
            //FreeformQuery query = new FreeformQuery("select * from web_report", connectionPool);
            //FreeformQuery query = new FreeformQuery(getQueryForReportType(projectID, reportType), connectionPool);

            FreeformQuery query = new FreeformQuery(queryString, connectionPool);

            reportData = new SQLContainer(query);
            viewTable = new Table("A minha tabela....");
            viewTable.setContainerDataSource(reportData);
            viewTable.setEditable(false);

            viewTable.setContainerDataSource(reportData);

            formatter.format(viewTable);
            setCompositionRoot(viewTable);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Table getTable() {
        return viewTable;
    }

    @Override
    public void write(HSSFSheet sheet) {
        int rowNum = sheet.getLastRowNum() + 2;
        for (Object itemId : viewTable.getItemIds()) {
            Item i = viewTable.getItem(itemId);
            HSSFRow row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (Object propertyID : i.getItemPropertyIds()) {
                HSSFCell cell = row.createCell(cellNum++);
                cell.setCellValue(i.getItemProperty(propertyID).getValue().toString());
            }
        }
    }
}
