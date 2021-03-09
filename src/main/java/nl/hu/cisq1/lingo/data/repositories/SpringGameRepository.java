package nl.hu.cisq1.lingo.data.repositories;

import nl.hu.cisq1.lingo.domain.LingoGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringGameRepository extends JpaRepository<LingoGame, Integer>{
	Optional<LingoGame> findById(Integer id);
}