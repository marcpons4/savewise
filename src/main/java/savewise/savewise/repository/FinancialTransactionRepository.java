package savewise.savewise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import savewise.savewise.entity.FinancialTransaction;

@Repository
public interface FinancialTransactionRepository
        extends JpaRepository<FinancialTransaction, Long> {
}