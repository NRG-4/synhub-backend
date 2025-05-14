package nrg.inc.synhubbackend.taskManagement.domain.services;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.AddGroupToMemberCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateMemberCommand;

import java.util.Optional;

public interface MemberCommandService {
    Optional<Member> handle(CreateMemberCommand command);
    Optional<Member> handle(AddGroupToMemberCommand command);
}
