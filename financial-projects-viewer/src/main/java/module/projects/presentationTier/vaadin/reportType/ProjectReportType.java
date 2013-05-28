package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.IllegalAccessException;
import module.projects.presentationTier.vaadin.reportType.components.ProjectHeaderComponent;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;
import pt.ist.expenditureTrackingSystem.domain.organization.SubProject;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;

public abstract class ProjectReportType extends ReportType {
    private final String projectCode;
    private final String projectID;
    private Project project;

    protected Project getProject() {
        return project;
    }

    protected String getProjectCode() {
        return projectCode;
    }

    protected String getProjectID() {
        return projectID;
    }

    protected ProjectReportType(Map<String, String> args) {
        super(args);
        setHeaderVisibility(true);
        projectID = args.get("unit");
        Project project = getProjectFromID(projectID);

        if (project != null && project.isResponsible(UserView.getCurrentUser().getExpenditurePerson())) {
            this.project = project;
            projectCode = project.getProjectCode();
            ProjectHeaderComponent header = new ProjectHeaderComponent(getLabel(), project);
            setHeader(header);
            addComponent(header);
        } else {
            throw new IllegalAccessException();
        }
    }

    private Project getProjectFromID(String projectID) {
        Unit project = FenixFramework.getDomainObject(projectID);
        if (project instanceof Project) {
            return (Project) project;
        } else if (project instanceof SubProject) {
            return (Project) ((SubProject) project).getParentUnit();
        }
        return null;
    }

}
