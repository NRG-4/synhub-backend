package nrg.inc.synhubbackend.taskManagement.domain.services;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllMembersQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetMemberByIdQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetMembersByGroupIdQuery;

import java.util.List;
import java.util.Optional;

public interface MemberQueryService {

    /**
     * Retrieves a member by their ID.
     *
     * @param query the query containing the member ID
     * @return an Optional containing the member if found, or empty if not found
     */
    Optional<Member> handle(GetMemberByIdQuery query);

    /**
     * Retrieves all members.
     *
     * @return a list of all members
     */
    List<Member> handle(GetAllMembersQuery query);

    /**
     * Retrieves all members by group ID.
     *
     * @param query the query containing the group ID
     * @return a list of members belonging to the specified group
     */
    List<Member> handle(GetMembersByGroupIdQuery query);
}
