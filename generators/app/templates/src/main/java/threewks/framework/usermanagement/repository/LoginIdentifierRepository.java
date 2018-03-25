package threewks.framework.usermanagement.repository;

import threewks.framework.usermanagement.model.LoginIdentifier;
import org.springframework.contrib.gae.objectify.repository.ObjectifyStringRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginIdentifierRepository extends ObjectifyStringRepository<LoginIdentifier> {

}
