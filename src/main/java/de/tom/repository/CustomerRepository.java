package de.tom.repository;

import de.tom.domain.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerById(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO customer(id, first_name, last_name, address, zip_code, city, email_address, phone_number)" +
            "VALUES (:id, :firstName, :lastName, :address, :zipCode, :city, :emailAddress, :phoneNumber)",
            nativeQuery = true)
    void saveCustomer(@Param("id") int id, @Param("firstName") String firstName, @Param("lastName") String lastName,
                      @Param("address") String address, @Param("zipCode") String zipCode, @Param("city") String city,
                      @Param("emailAddress") String emailAddress, @Param("phoneNumber") String phoneNumber);

}
