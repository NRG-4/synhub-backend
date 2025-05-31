package nrg.inc.synhubbackend.iam.interfaces.acl;

import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.iam.domain.model.commands.SignUpCommand;
import nrg.inc.synhubbackend.iam.domain.model.entities.Role;
import nrg.inc.synhubbackend.iam.domain.model.queries.GetUserByIdQuery;
import nrg.inc.synhubbackend.iam.domain.model.queries.GetUserByLeaderId;
import nrg.inc.synhubbackend.iam.domain.model.queries.GetUserByMemberId;
import nrg.inc.synhubbackend.iam.domain.model.queries.GetUserByUsernameQuery;
import nrg.inc.synhubbackend.iam.domain.services.UserCommandService;
import nrg.inc.synhubbackend.iam.domain.services.UserQueryService;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IamContextFacade
 * <p>
 *     This class is a facade for the IAM context. It provides a simple interface for other
 *     bounded contexts to interact with the
 *     IAM context.
 *     This class is a part of the ACL layer.
 * </p>
 *
 */
public interface IamContextFacade {

  Long createUser(String username, String name, String surname, String imgUrl, String email, String password);
  Long createUser(String username, String name, String surname, String imgUrl, String email, String password, List<String> roleNames);
  Long fetchUserIdByUsername(String username);
  String fetchUsernameByUserId(Long userId);
  Optional<User> fetchByMemberId(Long memberId);
  Optional<User> fetchByLeaderId(Long leaderId);
  Optional<User> fetchById(Long userId);
}
