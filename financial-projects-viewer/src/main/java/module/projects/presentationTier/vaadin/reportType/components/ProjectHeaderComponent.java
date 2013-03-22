package module.projects.presentationTier.vaadin.reportType.components;

import module.projects.presentationTier.vaadin.Reportable;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class ProjectHeaderComponent extends CustomComponent implements Reportable {
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

        GridLayout subLayout = new GridLayout(4, 5);
        subLayout.setSpacing(true);
        layout.addComponent(subLayout);

        subLayout.addComponent(new Label("Acronimo "));
        subLayout.addComponent(new Label(project.getShortIdentifier() + " "));
        subLayout.addComponent(new Label("Gestor de conta "));
        subLayout.addComponent(new Label(project.getAccountManager().getName() + " "));
        subLayout.addComponent(new Label("Nº Projecto"));
        subLayout.addComponent(new Label(project.getExternalId() + " "));
        subLayout.addComponent(new Label("telefone/ext"));
        subLayout.addComponent(new Label("??? "));
        subLayout.addComponent(new Label("Tipo "));
        subLayout.addComponent(new Label(project.getType() + " "));
        subLayout.addComponent(new Label("e-mail "));
        subLayout.addComponent(new Label("??? "));
        subLayout.addComponent(new Label("Coordenador "));
        subLayout.addComponent(new Label("?? "));
        subLayout.addComponent(new Label(" "));
        subLayout.addComponent(new Label(" "));
        subLayout.addComponent(new Label("Data "));
        subLayout.addComponent(new Label("?? "));

        setCompositionRoot(layout);
    }
}
