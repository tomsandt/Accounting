package de.tom.service;

import de.tom.domain.Article;
import de.tom.domain.Cost;
import de.tom.domain.Dealer;
import de.tom.domain.DealerDTO;
import de.tom.repository.DealerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    public List<Dealer> getAllDealers() {
        return dealerRepository.findAll();
    }

    public Optional<Dealer> getDealerById(int id) {
        return dealerRepository.findById(id);
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

        return dealerRepository.save(newDealer);
    }

    public void deleteDealer(int id) {
        if(!dealerRepository.existsById(id)) {
            throw new EntityNotFoundException("Dealer not found with ID: " + id);
        }else {
            dealerRepository.deleteById(id);
        }

    }

}
