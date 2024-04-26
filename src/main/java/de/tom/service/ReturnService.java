package de.tom.service;

import de.tom.domain.*;
import de.tom.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReturnService {

    @Autowired
    private DatabaseRepository databaseRepository;

    public List<Return> getAllReturns() {
        return databaseRepository.findAll(Return.class);
    }

    public Optional<Return> getReturnById(int id) {
        return Optional.ofNullable(databaseRepository.findById(Return.class, id));
    }

    @Transactional
    public void deleteReturnBySaleId(Sale sale) {

        Inventory inventoryItem = databaseRepository.findByForeignKey(Inventory.class, "article", sale.getArticleId().getId());
        inventoryItem.setAmount(inventoryItem.getAmount() - sale.getAmount());

        Return returns = databaseRepository.findByForeignKey(Return.class, "sale", sale);
        databaseRepository.delete(Return.class, returns.getId());
    }

    @Transactional
    public Return createReturn(ReturnDTO returnDTO) {

        Sale sale = databaseRepository.findById(Sale.class, returnDTO.getSaleId().getId());
        Article article = databaseRepository.findById(Article.class, sale.getArticleId().getId());

        //change Inventory
        if (databaseRepository.findByForeignKey(Inventory.class, "article", article) == null) {
            Inventory newInventoryItem = new Inventory();
            Cost cost = databaseRepository.findById(Cost.class, sale.getCostId().getId());

            newInventoryItem.setArticle(article);
            newInventoryItem.setAmount(sale.getAmount());
            newInventoryItem.setPurchaseDate(cost.getPurchaseDate());

            databaseRepository.save(newInventoryItem);
        }else {
            Inventory inventoryItem = databaseRepository.findByForeignKey(Inventory.class, "article", article);
            inventoryItem.setAmount(inventoryItem.getAmount() + returnDTO.getAmount());

            databaseRepository.save(inventoryItem);
        }

        Return newReturn = new Return();

        newReturn.setCustomerId(returnDTO.getCustomerId());
        newReturn.setSaleId(returnDTO.getSaleId());
        newReturn.setAmount(returnDTO.getAmount());
        newReturn.setReturnDate(returnDTO.getReturnDate());
        newReturn.setNote(returnDTO.getNote());

        return databaseRepository.save(newReturn);
    }

    public void deleteReturn(int id) {
        if (databaseRepository.findById(Return.class, id) == null) {
            throw new EntityNotFoundException("Return not found with ID: " + id);
        }else {
            databaseRepository.delete(Return.class, id);
        }
    }

}
