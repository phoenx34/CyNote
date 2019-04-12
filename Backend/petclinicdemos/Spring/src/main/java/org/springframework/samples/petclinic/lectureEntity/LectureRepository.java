package org.springframework.samples.petclinic.lectureEntity;

import java.util.List;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface LectureRepository extends CrudRepository<Lecture, Integer>{
	Lecture save(Lecture persisted);
	Optional<Lecture> findById(Integer Lid);
	List<Lecture> findAll();
	List<Lecture> findByClEntId(Integer clEntId);
}
