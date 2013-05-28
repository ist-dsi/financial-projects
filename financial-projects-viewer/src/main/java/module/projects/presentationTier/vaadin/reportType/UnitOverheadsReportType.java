package module.projects.presentationTier.vaadin.reportType;

import java.util.Map;

import pt.ist.expenditureTrackingSystem.domain.organization.Unit;
import pt.ist.fenixframework.FenixFramework;

public abstract class UnitOverheadsReportType extends ReportType {
    String costCenterCoordinator;

    protected UnitOverheadsReportType(Map<String, String> args) {
        super(args);
        String unitID = args.get("unit");
        Unit unit = FenixFramework.getDomainObject(unitID);
        //|| !UserView.getCurrentUser().getExpenditurePerson().getDirectResponsibleUnits().contains(unit)
        costCenterCoordinator = "99" + unit.getCostCenterUnit().getCostCenter();
    }

    protected String getCostCenterCoordinator() {
        return costCenterCoordinator;
    }

}
