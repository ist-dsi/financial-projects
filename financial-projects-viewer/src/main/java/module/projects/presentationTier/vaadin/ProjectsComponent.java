package module.projects.presentationTier.vaadin;

import java.util.Map;

import module.projects.presentationTier.vaadin.reportType.ReportType;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;
import pt.ist.expenditureTrackingSystem.domain.organization.SubProject;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.vaadinframework.annotation.EmbeddedComponent;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;

@EmbeddedComponent(path = { "projectsService" })
public class ProjectsComponent extends CustomComponent implements EmbeddedComponentContainer {
    Layout layout;
    Panel panel;

    public ProjectsComponent() {
        layout = new HorizontalLayout();
        panel = new Panel(layout);

        setCompositionRoot(panel);

    }

    @Override
    public void setArguments(Map<String, String> arguments) {
        // TODO Auto-generated method stub
        String projectID = arguments.get("unit");
        String reportTypeString = arguments.get("reportType");
        Project project = getProjectFromID(projectID);

        ReportType reportType = ReportType.getReportFromType(reportTypeString, arguments, project);

        if (reportType != null && project != null && project.isResponsible(UserView.getCurrentUser().getExpenditurePerson())) {
            layout.addComponent(reportType.getComponent(project.getProjectCode()));
        }
    }

    @Override
    public boolean isAllowedToOpen(Map<String, String> parameters) {
        // TODO Auto-generated method stub
        return true;
    }

    private Project getProjectFromID(String projectID) {
        Unit project = AbstractDomainObject.fromExternalId(projectID);
        if (project instanceof Project) {
            return (Project) project;
        } else if (project instanceof SubProject) {
            return (Project) ((SubProject) project).getParentUnit();
        }
        return null;
    }

}
