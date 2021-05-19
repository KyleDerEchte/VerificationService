package de.kyleonaut.verification.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<User> findUsersByStatus(User.STATUS status) {
        return List.copyOf(userRepository.findAllByStatus(status));
    }

    public void remove(UUID uuid) {
        final User user = findUser(uuid);
        if (findUser(uuid) == null) {
            return;
        }
        userRepository.delete(user);
    }
}
