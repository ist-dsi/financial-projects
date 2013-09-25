package module.projects.presentationTier.vaadin.reportType.components;

import java.sql.SQLException;

import module.projects.presentationTier.vaadin.Reportable;
import module.projects.presentationTier.vaadin.reportType.ReportType;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.IndexedColors;

import pt.ist.bennu.core._development.PropertiesManager;
import pt.ist.bennu.core.domain.VirtualHost;

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
    String[] originalHeader;
    FreeformQuery query;
    String queryString;

    public ReportViewerComponent(String queryString, ReportType.CustomTableFormatter formatter) {

        getDatabaseContainer(queryString);
        viewTable.setContainerDataSource(reportData);
        viewTable.setSizeUndefined();
        originalHeader = viewTable.getColumnHeaders();
        formatter.format(viewTable);

        setCompositionRoot(viewTable);

    }

    private void getDatabaseContainer(String queryString) {
        this.queryString = queryString;
        try {
            //needed to assure the class is loaded and registred as a driver
            final String driverName = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driverName);
            final String propPrefix = "db.mgp" + getHostPropertyPart();
            SimpleJDBCConnectionPool connectionPool =
                    new SimpleJDBCConnectionPool(driverName, getAlias(propPrefix), PropertiesManager.getProperty(propPrefix
                            + ".user"), PropertiesManager.getProperty(propPrefix + ".pass"), 2, 5);

            query = new FreeformQuery(queryString, connectionPool);
            reportData = new SQLContainer(query);
            viewTable = new Table();
            viewTable.setContainerDataSource(reportData);
            viewTable.setEditable(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAlias(final String propPrefix) {
        final String alias = "jdbc:oracle:thin:@//" + PropertiesManager.getProperty(propPrefix + ".alias");
        final int i = alias.lastIndexOf(':');
        return alias.substring(0, i) + '/' + alias.substring(i + 1);
    }

    public static String getHostPropertyPart() {
        final String title = VirtualHost.getVirtualHostForThread().getApplicationTitle().getContent();
        return StringUtils.lowerCase(title);
    }

    public Table getTable() {
        return viewTable;
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {

        int rowNum = writeHeader(sheet, headersFont);
        HSSFRow row;
        int cellNum;
        HSSFCell cell;
        for (Object itemId : viewTable.getItemIds()) {
            Item i = viewTable.getItem(itemId);
            row = sheet.createRow(rowNum++);
            cellNum = 0;
            for (Object propertyID : i.getItemPropertyIds()) {
                cell = row.createCell(cellNum++);
                Object value = i.getItemProperty(propertyID).getValue();
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }
    }

    public int writeHeader(HSSFSheet sheet, HSSFFont headersFont) {
        HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFont(headersFont);

        int position = sheet.getLastRowNum() + 2;
        HSSFRow row = sheet.createRow(position);
        int cellNum = 0;
        HSSFCell cell;

        for (String s : originalHeader) {
            cell = row.createCell(cellNum++);
            cell.setCellValue(viewTable.getColumnHeader(s));
            cell.setCellStyle(style);
        }
        return position + 1;
    }
}
