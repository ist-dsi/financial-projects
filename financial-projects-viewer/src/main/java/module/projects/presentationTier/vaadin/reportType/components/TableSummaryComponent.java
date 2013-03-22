package module.projects.presentationTier.vaadin.reportType.components;

import java.math.BigDecimal;

import module.projects.presentationTier.vaadin.Reportable;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class TableSummaryComponent extends CustomComponent implements Reportable {
    public TableSummaryComponent(Table table, String reportType, String... collumns) {
        Layout layout = new VerticalLayout();
        layout.addComponent(new Label("Resumo de " + reportType));
        for (String collumn : collumns) {
            BigDecimal result = new BigDecimal(0);
            for (Object itemID : table.getItemIds()) {
                Object value = table.getItem(itemID).getItemProperty(collumn).getValue();
                result = result.add((BigDecimal) value);
            }
            layout.addComponent(new Label(collumn + " Total: " + result + "€"));
        }
        setCompositionRoot(layout);
    }
}
