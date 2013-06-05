package module.projects.presentationTier.vaadin.reportType.components;

import java.util.Map;
import java.util.Map.Entry;

import module.projects.presentationTier.vaadin.Reportable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.vaadinframework.EmbeddedApplication;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;

public class TableLineFilterComponent extends CustomComponent implements Reportable {
    Table table;
    HorizontalLayout layout;
    Map<String, String> filters;
    Select select;

    public TableLineFilterComponent(final Map<String, String> filters, final String url, final Map<String, String> otherArgs,
            String currentFilter) {
        this.filters = filters;
        layout = new HorizontalLayout();
        setCompositionRoot(layout);
        select = new Select(getMessage("financialprojectsreports.tableLineFiltererComponent.filter") + ": ", filters.keySet());
        select.setImmediate(true);
        select.setNullSelectionAllowed(false);

        select.addItem(getMessage("financialprojectsreports.tableLineFiltererComponent.all"));

        if (currentFilter != null && !filters.containsValue(currentFilter)) {
            throw new RuntimeException("Invalid Option");
        }

        String current = getMessage("financialprojectsreports.tableLineFiltererComponent.all");
        for (Entry<String, String> entry : filters.entrySet()) {
            if (entry.getValue().equals(currentFilter)) {
                current = entry.getKey();
                break;
            }
        }
        select.setValue(current);

        layout.addComponent(select);

        select.addListener(new Field.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                String urlQueriedString = url + "?";
                for (Entry<String, String> entry : otherArgs.entrySet()) {
                    if (!entry.getKey().equals("filter")) {
                        urlQueriedString += entry.getKey() + "=" + entry.getValue() + "&";
                    }
                }
                if (!event.getProperty().getValue().toString()
                        .equals(getMessage("financialprojectsreports.tableLineFiltererComponent.all"))) {
                    urlQueriedString += "filter=" + filters.get(event.getProperty().getValue().toString());
                }
                EmbeddedApplication.open(getApplication(), urlQueriedString);

            }
        });
    }

    @Override
    public void write(HSSFSheet sheet, HSSFFont headersFont) {
        int rowNum = sheet.getLastRowNum() + 2;
        HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFont(headersFont);

        HSSFCell cell = sheet.createRow(rowNum++).createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue(getMessage("financialprojectsreports.tableLineFilterer.currentFilter"));
        cell = sheet.createRow(rowNum++).createCell(0);
        cell.setCellValue(select.getValue().toString());
    }

    public String currentValue() {
        return select.getValue().toString();
    }

    public final String RESOURCE_BUNDLE = "resources/projectsResources";

    public String getMessage(String message) {
        return BundleUtil.getFormattedStringFromResourceBundle(RESOURCE_BUNDLE, message);
    }

}
