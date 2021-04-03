package nl.hu.cisq1.lingo.data.repositories;

import nl.hu.cisq1.lingo.data.LingoGameDM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringGameRepository extends JpaRepository<LingoGameDM, Long>{
}