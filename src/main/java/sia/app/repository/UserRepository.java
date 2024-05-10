package sia.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sia.app.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByLoginAndPassword(String login, String password);
}
