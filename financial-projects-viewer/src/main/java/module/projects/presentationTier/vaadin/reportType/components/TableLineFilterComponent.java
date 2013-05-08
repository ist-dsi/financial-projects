package module.projects.presentationTier.vaadin.reportType.components;

import java.util.Map;
import java.util.Map.Entry;

import module.projects.presentationTier.vaadin.Reportable;

import org.apache.poi.hssf.usermodel.HSSFSheet;

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
        select = new Select("Filtros: ", filters.keySet());
        select.setImmediate(true);
        select.setNullSelectionAllowed(false);

        select.addItem("Todos");

        if (currentFilter != null && !filters.containsValue(currentFilter)) {
            throw new RuntimeException("Invalid Option");
        }

        String current = "Todos";
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
                if (!event.getProperty().getValue().toString().equals("Todos")) {
                    urlQueriedString += "filter=" + filters.get(event.getProperty().getValue().toString());
                }
                EmbeddedApplication.open(getApplication(), urlQueriedString);

            }
        });
    }

    @Override
    public void write(HSSFSheet sheet) {
        // TODO write current filter

    }
}
