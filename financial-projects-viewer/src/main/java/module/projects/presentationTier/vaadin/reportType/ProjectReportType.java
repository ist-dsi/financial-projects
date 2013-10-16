package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.IllegalAccessException;
import module.projects.presentationTier.vaadin.reportType.components.ProjectHeaderComponent;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.expenditureTrackingSystem.domain.organization.Person;
import pt.ist.expenditureTrackingSystem.domain.organization.Project;
import pt.ist.expenditureTrackingSystem.domain.organization.SubProject;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;

public abstract class ProjectReportType extends ReportType {
    private String projectCode;
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

        if (project != null && checkAccessControl(projectID)) {
            this.project = project;
            projectCode = project.getProjectCode();
            ProjectHeaderComponent header = new ProjectHeaderComponent(getLabel(), project);
            setHeader(header);
            addComponent(header);
        } else {
            throw new IllegalAccessException();
        }
    }

    private boolean checkAccessControl(String unit) {
        Unit project = FenixFramework.getDomainObject(unit);
        Person currentUser = UserView.getCurrentUser().getExpenditurePerson();
        return project.isResponsible(currentUser) || project.getObserversSet().contains(currentUser)
                || currentUser.getUser().hasRoleType(RoleType.MANAGER);
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
