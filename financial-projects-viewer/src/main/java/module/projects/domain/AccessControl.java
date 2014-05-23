package module.projects.domain;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.domain.User;
import pt.ist.expenditureTrackingSystem.domain.ExpenditureTrackingSystem;
import pt.ist.expenditureTrackingSystem.domain.organization.Person;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;

public class AccessControl {

    public static boolean isUserAllowedToViewDetailedProjectInfo(final Unit unit, final User user) {
        final Person person = user.getExpenditurePerson();

        return unit.isResponsible(person)

        || unit.getObserversSet().contains(person)

        || UserView.getCurrentUser().hasRoleType(RoleType.MANAGER)

        || unit.isAccountingEmployee(person)

        || unit.isProjectAccountingEmployee(person)

        || unit.isAccountManager(person)

        || unit.isAccountingResponsible(person)

        || ExpenditureTrackingSystem.isAccountingManagerGroupMember(user)

        || ExpenditureTrackingSystem.isProjectAccountingManagerGroupMember(user)

        || ExpenditureTrackingSystem.isFundCommitmentManagerGroupMember(user)

        || ExpenditureTrackingSystem.isTreasuryMemberGroupMember(user)

        || ExpenditureTrackingSystem.isAcquisitionsProcessAuditorGroupMember(user);
    }

}
