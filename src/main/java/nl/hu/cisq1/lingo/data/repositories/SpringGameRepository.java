package nl.hu.cisq1.lingo.data.repositories;

import nl.hu.cisq1.lingo.data.LingoGameDM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringGameRepository extends JpaRepository<LingoGameDM, Integer>{
	Optional<LingoGameDM> findById(Integer id);
}