package nrg.inc.synhubbackend.iam.domain.model.commands;

import nrg.inc.synhubbackend.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<Role> roles) {
}
