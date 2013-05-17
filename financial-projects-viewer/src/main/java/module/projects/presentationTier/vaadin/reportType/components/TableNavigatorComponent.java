package module.projects.presentationTier.vaadin.reportType.components;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class TableNavigatorComponent extends CustomComponent {
    Table table;
    HorizontalLayout layout;

    public TableNavigatorComponent(Table table) {
        this(table, 20);
    }

    public TableNavigatorComponent(final Table table, final int pageLength) {
        layout = new HorizontalLayout();

        layout.setSpacing(true);
        setCompositionRoot(layout);
        this.table = table;

        table.setPageLength(pageLength);

        for (int i = 0; i * pageLength < table.getItemIds().size(); i++) {
            final int index = i * pageLength;
            Label l = new Label(new Integer(i).toString());
            l.setStyleName("v-table-cell-content-slotcell");
            VerticalLayout subLayout = new VerticalLayout();
            subLayout.addComponent(l);
            layout.addComponent(subLayout);
            subLayout.addListener(new LayoutClickListener() {

                @Override
                public void layoutClick(LayoutClickEvent event) {
                    table.setCurrentPageFirstItemIndex(index);
                }
            });
        }
    }
}
