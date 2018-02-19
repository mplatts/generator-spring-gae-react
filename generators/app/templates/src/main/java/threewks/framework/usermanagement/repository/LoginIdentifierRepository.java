package threewks.framework.usermanagement.repository;

import org.springframework.contrib.gae.objectify.repository.ObjectifyStringRepository;
import org.springframework.stereotype.Repository;
import threewks.framework.usermanagement.model.LoginIdentifier;

@Repository
public interface LoginIdentifierRepository extends ObjectifyStringRepository<LoginIdentifier> {

}
