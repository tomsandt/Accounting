package de.tom.service;

import de.tom.domain.Dealer;
import de.tom.domain.DealerDTO;
import de.tom.repository.DatabaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealerService {

    @Autowired
    private DatabaseRepository databaseRepository;

    public List<Dealer> getAllDealers() {
        return databaseRepository.findAll(Dealer.class);
    }

    public Dealer getDealerById(int id) {
        return databaseRepository.findById(Dealer.class, id);
    }

    public Dealer createDealer(DealerDTO dealerDTO) {

        Dealer newDealer = new Dealer();

        newDealer.setDealerType(dealerDTO.getDealerType());
        newDealer.setName(dealerDTO.getName());
        newDealer.setAddress(dealerDTO.getAddress());
        newDealer.setZipCode(dealerDTO.getZipCode());
        newDealer.setCity(dealerDTO.getCity());
        newDealer.setEmailAddress(dealerDTO.getEmailAddress());
        newDealer.setPhoneNumber(dealerDTO.getPhoneNumber());

        return databaseRepository.save(newDealer);
    }

    public void deleteDealer(int id) {
        if(databaseRepository.findById(Dealer.class, id) == null) {
            throw new EntityNotFoundException("Dealer not found with ID: " + id);
        }else {
            databaseRepository.delete(Dealer.class, id);
        }

    }

}
