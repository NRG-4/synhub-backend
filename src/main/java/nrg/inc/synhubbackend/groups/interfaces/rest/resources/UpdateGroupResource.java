package nrg.inc.synhubbackend.groups.interfaces.rest.resources;

public record UpdateGroupResource(
        String name,
        String description,
        String imgUrl
) {
}
