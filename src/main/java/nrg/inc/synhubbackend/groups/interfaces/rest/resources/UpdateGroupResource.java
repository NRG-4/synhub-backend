package nrg.inc.synhubbackend.groups.interfaces.rest.resources;

public record UpdateGroupResource(
        String name,
        String description,
        Integer memberCount,
        String imgUrl
) {
}
