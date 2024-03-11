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
    private ReturnRepository returnRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CostRepository costRepository;

    public List<Return> getAllReturns() {
        return returnRepository.findAll();
    }

    public Optional<Return> getReturnById(int id) {
        return Optional.ofNullable(returnRepository.findById(id));
    }

    @Transactional
    public void deleteReturnBySaleId(Sale sale) {

        Inventory inventoryItem = inventoryRepository.findByArticleId_Id(sale.getArticleId().getId());
        inventoryItem.setAmount(inventoryItem.getAmount() - sale.getAmount());

        Return returns = returnRepository.findBySaleId(sale);
        returnRepository.delete(returns);
    }

    @Transactional
    public Return createReturn(ReturnDTO returnDTO) {

        Sale sale = saleRepository.findSaleById(returnDTO.getSaleId().getId());
        Article article = articleRepository.findById(sale.getArticleId().getId());

        //change Inventory
        if (inventoryRepository.findByArticleId_Id(article.getId()) == null) {
            Inventory newInventoryItem = new Inventory();
            Cost cost = costRepository.findCostById(sale.getCostId().getId());

            newInventoryItem.setArticleId(article);
            newInventoryItem.setAmount(sale.getAmount());
            newInventoryItem.setPurchaseDate(cost.getPurchaseDate());

            inventoryRepository.save(newInventoryItem);
        }else {
            Inventory inventoryItem = inventoryRepository.findByArticleId_Id(article.getId());
            inventoryItem.setAmount(inventoryItem.getAmount() + returnDTO.getAmount());

            inventoryRepository.save(inventoryItem);
        }

        Return newReturn = new Return();

        newReturn.setCustomerId(returnDTO.getCustomerId());
        newReturn.setSaleId(returnDTO.getSaleId());
        newReturn.setAmount(returnDTO.getAmount());
        newReturn.setReturnDate(returnDTO.getReturnDate());
        newReturn.setNote(returnDTO.getNote());

        return returnRepository.save(newReturn);
    }

    public void deleteReturn(int id) {
        if (!returnRepository.existsById(id)) {
            throw new EntityNotFoundException("Return not found with ID: " + id);
        }else {
            returnRepository.deleteById(id);
        }
    }

}
