package nrg.inc.synhubbackend.tasks.interfaces.rest.resources;

public record UserMemberResource(
        String username,
        String name,
        String surname,
        String imgUrl,
        String email,
        Long groupId
) {
}
