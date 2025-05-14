package nrg.inc.synhubbackend.groups.domain.model.commands;

public record UpdateGroupCommand(
        Long groupId,
        String name,
        String description,

        Integer memberCount,
        String imgUrl
) {
}
