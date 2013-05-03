package module.projects.presentationTier.vaadin.reportType.components;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import pt.ist.vaadinframework.EmbeddedApplication;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;

public class TableLineFilterComponent extends CustomComponent {
    Table table;
    HorizontalLayout layout;

    public TableLineFilterComponent(Table table, final Collection<String> options, final String url,
            final Map<String, String> otherArgs) {
        layout = new HorizontalLayout();
        setCompositionRoot(layout);
        Select select = new Select("Filtros: ", options);
        select.setImmediate(true);

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
                urlQueriedString += "filter=" + event.getProperty().getValue().toString();
                EmbeddedApplication.open(getApplication(), urlQueriedString);

            }
        });
    }
}
