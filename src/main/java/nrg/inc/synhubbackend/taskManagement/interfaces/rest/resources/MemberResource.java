package nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources;

public record MemberResource(
        String username,
        String name,
        String surname,
        String imgUrl,
        String email
) {
}
