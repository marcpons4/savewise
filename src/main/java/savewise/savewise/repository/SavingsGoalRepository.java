package savewise.savewise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import savewise.savewise.entity.SavingsGoal;
import savewise.savewise.entity.User;

public interface SavingsGoalRepository
        extends JpaRepository<SavingsGoal, Long> {

    List<SavingsGoal> findByUser(User user);
}