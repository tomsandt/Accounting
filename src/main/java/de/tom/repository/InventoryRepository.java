package de.tom.repository;

import de.tom.domain.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Inventory findByArticleId_Id(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO inventory(articleId, amount, purchaseDate)" +
            "VALUES (:articleId, :amount, :purchaseDate)",
            nativeQuery = true)
    void saveInventory(@Param("articleId") int articleId, @Param("amount") int amount,
                       @Param("purchaseDate") LocalDate purchaseDate);

}
