package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import module.projects.presentationTier.vaadin.IllegalAccessException;
import module.projects.presentationTier.vaadin.reportType.components.UnitOverheadHeaderComponent;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.expenditureTrackingSystem.domain.organization.Person;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;

public abstract class UnitOverheadsReportType extends ReportType {
    String costCenterCoordinator;
    UnitOverheadHeaderComponent header;

    protected UnitOverheadsReportType(Map<String, String> args) {
        super(args);
        String unitID = args.get("unit");
        Unit unit = FenixFramework.getDomainObject(unitID);
        Person currentUser = UserView.getCurrentUser().getExpenditurePerson();
        if (unit.isProject()
                || !(unit.isResponsible(currentUser) || unit.getObserversSet().contains(currentUser) || currentUser.getUser()
                        .hasRoleType(RoleType.MANAGER))) {
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
