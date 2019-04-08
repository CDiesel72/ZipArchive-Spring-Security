package CDiesel72.Service;

import CDiesel72.Entity.CustomUser;
import CDiesel72.Entity.UserRole;
import CDiesel72.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void deleteUsers(long[] ids) {
        if (ids == null) return;

        for (long id : ids) {
            userRepository.delete(id);
        }
    }

    @Transactional
    public boolean addUser(String login, String passHash,
                           UserRole role) {
        if (userRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(login, passHash, role);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public void updateUser(String login, String passHash) {
        CustomUser user = userRepository.findByLogin(login);

        user.setPassword(passHash);

        userRepository.save(user);
    }
}
