package de.tom.service;

import de.tom.domain.Article;
import de.tom.domain.Cost;
import de.tom.domain.CostDTO;
import de.tom.domain.Inventory;
import de.tom.repository.ArticleRepository;
import de.tom.repository.CostRepository;
import de.tom.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostService {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Cost> getAllCosts() {
        return costRepository.findAll();
    }

    public Optional<Cost> getCostById(int id) {
        return costRepository.findById(id);
    }

    @Transactional
    public Cost createCost(CostDTO costDTO) {

        Article article_Cost = articleRepository.findById(costDTO.getArticleId().getId());
        if(article_Cost == null) {
            throw new EntityNotFoundException("Article not found with ID: " + costDTO.getArticleId().getId());
        }

        Inventory inventoryItem = inventoryRepository.findByArticleId_Id(costDTO.getArticleId().getId());
        if (inventoryItem == null) {
            inventoryItem = new Inventory();
            inventoryItem.setArticleId(article_Cost);
            inventoryItem.setAmount(costDTO.getAmount());
            inventoryItem.setPurchaseDate(costDTO.getPurchaseDate());
        }else {
            inventoryItem.setAmount(inventoryItem.getAmount() + costDTO.getAmount());
        }

        inventoryRepository.save(inventoryItem);

        Cost newCost = new Cost();

        newCost.setExpenditureType(costDTO.getExpenditureType());
        newCost.setArticleId(costDTO.getArticleId());
        newCost.setAmount(costDTO.getAmount());
        newCost.setPurchaseDate(costDTO.getPurchaseDate());
        newCost.setTotalPrice(costDTO.getTotalPrice());
        newCost.setShipping(costDTO.getShipping());
        newCost.setTax(costDTO.getTax());

        return costRepository.save(newCost);
    }

    public void deleteCost(int id) {
        if(!costRepository.existsById(id)) {
            throw new EntityNotFoundException("Cost not found with ID: " + id);
        }else {
            costRepository.deleteById(id);
        }

    }

}
