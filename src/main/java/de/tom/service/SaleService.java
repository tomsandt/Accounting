package de.tom.service;

import de.tom.domain.*;
import de.tom.repository.ArticleRepository;
import de.tom.repository.CostRepository;
import de.tom.repository.InventoryRepository;
import de.tom.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CostRepository costRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(int id) {
        return saleRepository.findById(id);
    }

    @Transactional
    public Sale createSale(SaleDTO saleDTO) {

        Cost cost = costRepository.findCostById(saleDTO.getCostId().getId());
        Article article = articleRepository.findById(cost.getArticleId().getId());
        if (article == null) {
            throw new EntityNotFoundException("Article not found with ID: " + saleDTO.getCostId().getArticleId().getId());
        }else {
            Inventory inventoryItem = inventoryRepository.findByArticleId_Id(article.getId());

            if (inventoryItem != null) {
                inventoryItem.setAmount(inventoryItem.getAmount() - saleDTO.getAmount());

                if (inventoryItem.getAmount() == 0) {
                    inventoryRepository.delete(inventoryItem);
                }else {
                    inventoryRepository.save(inventoryItem);
                }
            }
        }

        Cost cost_Sale = costRepository.findCostById(saleDTO.getCostId().getId());

        BigDecimal purchasePrice = BigDecimal.valueOf(cost_Sale.getTotalPrice());
        BigDecimal sellPrice = BigDecimal.valueOf(saleDTO.getPrice());
        BigDecimal earning = sellPrice.subtract(purchasePrice);

        BigDecimal reserve = earning.multiply(BigDecimal.valueOf(0.35)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal profit = earning.subtract(reserve).setScale(2, RoundingMode.HALF_UP);

        saleDTO.setReserve(reserve.doubleValue());
        saleDTO.setProfit(profit.doubleValue());

        Sale newSale = new Sale();

        newSale.setCustomerId(saleDTO.getCustomerId());
        newSale.setCostId(saleDTO.getCostId());
        newSale.setAmount(saleDTO.getAmount());
        newSale.setDateOfSale(saleDTO.getDateOfSale());
        newSale.setPrice(saleDTO.getPrice());
        newSale.setShipping(saleDTO.getShipping());
        newSale.setFee(saleDTO.getFee());
        newSale.setReserve(saleDTO.getReserve());
        newSale.setProfit(saleDTO.getProfit());

        return saleRepository.save(newSale);
    }


    public void deleteSale(int id) {
        if(!saleRepository.existsById(id)) {
            throw new EntityNotFoundException("Sale not found with ID: " + id);
        }else {
            saleRepository.deleteById(id);
        }

    }

}
