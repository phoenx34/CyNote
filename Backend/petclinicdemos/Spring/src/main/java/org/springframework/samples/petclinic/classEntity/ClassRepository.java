package org.springframework.samples.petclinic.classEntity;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
 
/**
 * The ClassRepository connect to the database 
 * @author Shen Chen 
 * @author Marc Issac 
 *
 */
public interface ClassRepository extends JpaRepository<ClEnt, Long> {
}






//package org.springframework.samples.petclinic.classEntity;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.samples.petclinic.user.User;
//import org.springframework.stereotype.Repository;
//
///**
// * 
// * @author Shen Chen
// * @author Marc Issac
// */
//
//@Repository
//public interface ClassRepository extends CrudRepository<ClEnt, Integer> {
//	ClEnt save(ClEnt persisted); 
//	Optional<ClEnt> findById(Integer CID);
//	List<ClEnt> findAll();
//	
//
//}
