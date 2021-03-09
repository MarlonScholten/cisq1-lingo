package nl.hu.cisq1.lingo.data;

import nl.hu.cisq1.lingo.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringGameRepository extends JpaRepository<Game, Integer>{
	Optional<Game> findById(Integer id);
}