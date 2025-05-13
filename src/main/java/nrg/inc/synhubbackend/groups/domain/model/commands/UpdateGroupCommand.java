package nrg.inc.synhubbackend.groups.domain.model.commands;

public record UpdateGroupCommand(
        String groupId,
        String name,
        String imgUrl
) {
}
