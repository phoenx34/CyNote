package profile;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.user.User;

public interface ProfileRepository extends CrudRepository<Profile, String> {

	Optional<User> findByUserId(String id);

	Object findAllById(String uID);

	Optional<User> findByUsername(String username);

}
