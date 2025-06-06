package nrg.inc.synhubbackend.tasks.interfaces.rest.resources;

public record MemberResource(
        String username,
        String name,
        String surname,
        String imgUrl,
        String email
) {
}
