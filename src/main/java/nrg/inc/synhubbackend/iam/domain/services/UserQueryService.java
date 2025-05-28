package nrg.inc.synhubbackend.iam.domain.services;


import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.iam.domain.model.queries.GetAllUsersQuery;
import nrg.inc.synhubbackend.iam.domain.model.queries.GetUserByIdQuery;
import nrg.inc.synhubbackend.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
  List<User> handle(GetAllUsersQuery query);
  Optional<User> handle(GetUserByIdQuery query);
  Optional<User> handle(GetUserByUsernameQuery query);
}
