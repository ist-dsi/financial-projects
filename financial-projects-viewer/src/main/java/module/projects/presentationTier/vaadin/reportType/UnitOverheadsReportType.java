package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.domain.AccessControl;
import module.projects.presentationTier.vaadin.IllegalAccessException;
import module.projects.presentationTier.vaadin.reportType.components.UnitOverheadHeaderComponent;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;

public abstract class UnitOverheadsReportType extends ReportType {
    String costCenterCoordinator;
    UnitOverheadHeaderComponent header;

    protected UnitOverheadsReportType(Map<String, String> args) {
        super(args);
        String unitID = args.get("unit");
        Unit unit = FenixFramework.getDomainObject(unitID);
        final User user = UserView.getCurrentUser();
        if (unit.isProject() || !AccessControl.isUserAllowedToViewDetailedProjectInfo(unit, user)) {
            throw new IllegalAccessException();
        }

        costCenterCoordinator = "99" + unit.getCostCenterUnit().getCostCenter();
        header = new UnitOverheadHeaderComponent(unit, getLabel());
        addComponent(header);

    }

    protected String getCostCenterCoordinator() {
        return costCenterCoordinator;
    }

    protected UnitOverheadHeaderComponent getOverheadHeader() {
        return header;
    }

}
