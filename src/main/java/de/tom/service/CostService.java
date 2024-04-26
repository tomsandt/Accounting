package de.tom.service;

import de.tom.domain.Article;
import de.tom.domain.Cost;
import de.tom.domain.CostDTO;
import de.tom.domain.Inventory;
import de.tom.repository.DatabaseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostService {

    @Autowired
    private DatabaseRepository databaseRepository;

    public List<Cost> getAllCosts() {
        return databaseRepository.findAll(Cost.class);
    }

    public Cost getCostById(int id) {
        return databaseRepository.findById(Cost.class, id);
    }

    @Transactional
    public Cost createCost(CostDTO costDTO) {

        Article article_Cost = databaseRepository.findById(Article.class, costDTO.getArticleId().getId());
        if(article_Cost == null) {
            throw new EntityNotFoundException("Article not found with ID: " + costDTO.getArticleId().getId());
        }

        Inventory inventoryItem = databaseRepository.findById(Inventory.class, costDTO.getArticleId().getId());
        if (inventoryItem == null) {
            inventoryItem = new Inventory();
            inventoryItem.setArticle(article_Cost);
            inventoryItem.setAmount(costDTO.getAmount());
            inventoryItem.setPurchaseDate(costDTO.getPurchaseDate());
        }else {
            inventoryItem.setAmount(inventoryItem.getAmount() + costDTO.getAmount());
        }

        databaseRepository.save(inventoryItem);

        Cost newCost = new Cost();

        newCost.setExpenditureType(costDTO.getExpenditureType());
        newCost.setArticleId(costDTO.getArticleId());
        newCost.setAmount(costDTO.getAmount());
        newCost.setPurchaseDate(costDTO.getPurchaseDate());
        newCost.setTotalPrice(costDTO.getTotalPrice());
        newCost.setShipping(costDTO.getShipping());
        newCost.setTax(costDTO.getTax());

        return databaseRepository.save(newCost);
    }

    public void deleteCost(int id) {
        if(databaseRepository.findById(Cost.class, id) == null) {
            throw new EntityNotFoundException("Cost not found with ID: " + id);
        }
        Cost cost = databaseRepository.findById(Cost.class, id);
        Inventory inventoryItem = databaseRepository.findByForeignKey(Inventory.class, "article", cost.getArticleId().getId());
        inventoryItem.setAmount(inventoryItem.getAmount() - cost.getAmount());

        if (inventoryItem.getAmount() == 0) {
            databaseRepository.delete(Inventory.class, databaseRepository.findById(Inventory.class, inventoryItem.getId()).getId());
        }else {
            databaseRepository.save(inventoryItem);
        }

        databaseRepository.delete(Cost.class, id);

    }

}
