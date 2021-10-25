package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Sessions;

public interface SessionRepository extends JpaRepository<Sessions, Long> {

}
