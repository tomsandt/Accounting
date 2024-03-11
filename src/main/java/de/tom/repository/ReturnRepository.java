package de.tom.repository;

import de.tom.domain.Article;
import de.tom.domain.Return;
import de.tom.domain.Sale;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Integer> {

    Return findById(int id);
    Return findBySaleId(Sale sale);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO return(id, customer_id, sale_id, amount, returnDate, note)" +
            "VALUES (:id, :customerId, :saleId, :amount, :returnDate, :note)",
            nativeQuery = true)
    void saveReturn(@Param("id") int id, @Param("customerId") int customerId, @Param("amount") int amount,
                    @Param("saleId") int saleId, @Param("returnDate") LocalDate returnDate, @Param("note") String note);

}
