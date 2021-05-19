package de.kyleonaut.verification.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUser(UUID uuid) {
        return userRepository.findUserByUuid(uuid.toString());
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Collection<User> findUsersByStatus(User.STATUS status) {
        return userRepository.findAllByStatus(status);
    }

    public void remove(UUID uuid) {
        final User user = findUser(uuid);
        if (findUser(uuid) == null) {
            return;
        }
        userRepository.delete(user);
    }
}
