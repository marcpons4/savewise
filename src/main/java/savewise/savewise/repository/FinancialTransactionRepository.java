package savewise.savewise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import savewise.savewise.entity.FinancialTransaction;
import savewise.savewise.entity.User;

@Repository
public interface FinancialTransactionRepository
        extends JpaRepository<FinancialTransaction, Long> {

    List<FinancialTransaction> findByUser(User user);
}