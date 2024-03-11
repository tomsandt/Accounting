package de.tom.repository;

import de.tom.domain.Dealer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Integer> {

    Dealer findDealerById(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO dealer(id, dealer_type, name, address, zip_code, city, email_address, phone_number)" +
            "VALUES (:id, :dealerType, :name, :address, :zipCode, :city, :emailAddress, :phoneNumber)",
            nativeQuery = true)
    void saveDealer(@Param("id") int id, @Param("dealerType") Dealer.dealerType dealerType, @Param("name") String name,
                    @Param("address") String address, @Param("zipCode") String zipCode, @Param("city") String city,
                    @Param("emailAddress") String emailAddress, @Param("phoneNumber") String phoneNumber);

}
