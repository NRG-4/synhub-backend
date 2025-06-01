package nrg.inc.synhubbackend.groups.interfaces.rest.resources;

public record CreateInvitationRequestResource(
        Long memberId,
        Long groupId
) {
}
