package nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources;

public record UserMemberResource(
        String username,
        String name,
        String surname,
        String imgUrl,
        String email,
        Long groupId
) {
}
