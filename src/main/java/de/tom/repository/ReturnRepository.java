package de.tom.repository;

import de.tom.domain.Article;
import de.tom.domain.Return;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Integer> {

    Return findById(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO return(id, customer_id, article_id, sale_id, returnDate, note)" +
            "VALUES (:id, :customerId, :articleId, :saleId, :returnDate, :note)",
            nativeQuery = true)
    void saveReturn(@Param("id") int id, @Param("customerId") int customerId, @Param("articleId") int articleId,
                    @Param("saleId") int saleId, @Param("returnDate") LocalDate returnDate, @Param("note") String note);

}
