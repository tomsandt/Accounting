package de.tom.repository;

import de.tom.domain.Cost;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CostRepository extends JpaRepository<Cost, Integer> {

    Cost findCostById(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cost(id, expenditure_type, article_id, amount, purchase_date, shipping, tax, total_price)" +
            "VALUES (:id, :expenditureType, :articleId, :amount, :purchaseDate, :shipping, :tax, :totalPrice)",
            nativeQuery = true)
    void saveCost(@Param("id") int id, @Param("expenditureType") Cost.expenditureType expenditureType, @Param("articleId") int articleId,
                  @Param("amount") int amount, @Param("purchaseDate") LocalDate purchaseDate,
                  @Param("shipping") double shipping, @Param("tax") double tax, @Param("totalPrice") double totalPrice);

}
