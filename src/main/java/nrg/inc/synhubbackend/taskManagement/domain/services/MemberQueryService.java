package nrg.inc.synhubbackend.taskManagement.domain.services;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllMembersQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetMemberByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MemberQueryService {
    /**
     * Retrieves a member by their ID.
     *
     * @param memberId the ID of the member to retrieve
     * @return the member with the specified ID
     */
    Optional<Member> handle(GetMemberByIdQuery memberId);

    /**
     * Retrieves all members.
     *
     * @return a list of all members
     */
    List<Member> handle(GetAllMembersQuery membersQuery);
}
