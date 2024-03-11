package de.tom.repository;

import de.tom.domain.Sale;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    Sale findSaleById(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sale(id, customer_id, cost_id, amount, date_of_sale, price, shipping, fee, reserve, profit)" +
            "VALUES (:id, :customerId, :costId, :amount, :dateOfSale, :price, :shipping, :fee, :reserve, :profit)",
            nativeQuery = true)
    void saveSale(@Param("id") int id, @Param("customerId") int customerId, @Param("costId") int costId,
                   @Param("amount") int amount, @Param("dateOfSale") LocalDate dateOfSale, @Param("price") double price,
                   @Param("shipping") double shipping, @Param("fee") double fee, @Param("reserve") double reserve, @Param("profit") double profit);

}
