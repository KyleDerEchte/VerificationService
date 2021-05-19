package de.kyleonaut.verification.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUuid(String uuid);

    Collection<User> findAllByStatus(User.STATUS status);
}
