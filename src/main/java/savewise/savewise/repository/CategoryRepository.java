package savewise.savewise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import savewise.savewise.entity.Category;
import savewise.savewise.entity.User;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {

    List<Category> findByUser(User user);
}