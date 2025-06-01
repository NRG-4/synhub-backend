package nrg.inc.synhubbackend.groups.interfaces.rest.resources;

public record UserMemberInvitationResource(
        Long memberId,
        String username,
        String name,
        String surname,
        String imgUrl
) {
}
