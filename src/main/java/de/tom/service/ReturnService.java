package de.tom.service;

import de.tom.domain.Return;
import de.tom.domain.ReturnDTO;
import de.tom.repository.ReturnRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReturnService {

    @Autowired
    private ReturnRepository returnRepository;

    public List<Return> getAllReturns() {
        return returnRepository.findAll();
    }

    public Optional<Return> getReturnById(int id) {
        return Optional.ofNullable(returnRepository.findById(id));
    }

    public Return createReturn(ReturnDTO returnDTO) {



        Return newReturn = new Return();

        newReturn.setCustomerId(returnDTO.getCustomerId());
        newReturn.setArticleId(returnDTO.getArticleId());
        newReturn.setSaleId(returnDTO.getSaleId());
        newReturn.setReturnDate(returnDTO.getReturnDate());
        newReturn.setNote(returnDTO.getNote());

        return returnRepository.save(newReturn);
    }

    public void deleteReturn(int id) {
        if(!returnRepository.existsById(id)) {
            throw new EntityNotFoundException("Return not found with ID: " + id);
        }else {
            returnRepository.deleteById(id);
        }
    }

}
