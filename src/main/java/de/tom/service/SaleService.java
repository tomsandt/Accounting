package de.tom.service;

import de.tom.domain.*;
import de.tom.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private ReturnService returnService;

    public List<Sale> getAllSales() {
        return databaseRepository.findAll(Sale.class);
    }

    public Sale getSaleById(int id) {
        return databaseRepository.findById(Sale.class, id);
    }

    @Transactional
    public Sale createSale(SaleDTO saleDTO) {

        Cost cost = databaseRepository.findById(Cost.class, saleDTO.getCostId().getId());
        Article article = databaseRepository.findById(Article.class, cost.getArticleId().getId());
        if (article == null) {
            throw new EntityNotFoundException("Article not found with ID: " + saleDTO.getCostId().getArticleId().getId());
        }else {
            Inventory inventoryItem = databaseRepository.findByForeignKey(Inventory.class, "article", article);

            if (inventoryItem != null) {
                inventoryItem.setAmount(inventoryItem.getAmount() - saleDTO.getAmount());

                if (inventoryItem.getAmount() == 0) {
                    databaseRepository.delete(Inventory.class, inventoryItem.getId());
                }else {
                    databaseRepository.save(inventoryItem);
                }
            }
        }

        Cost cost_Sale = databaseRepository.findById(Cost.class, saleDTO.getCostId().getId());

        BigDecimal purchasePrice = BigDecimal.valueOf(cost_Sale.getTotalPrice());
        BigDecimal sellPrice = BigDecimal.valueOf(saleDTO.getPrice());
        BigDecimal earning = sellPrice.subtract(purchasePrice);

        BigDecimal reserve = earning.multiply(BigDecimal.valueOf(0.35)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal profit = earning.subtract(reserve).setScale(2, RoundingMode.HALF_UP);

        saleDTO.setReserve(reserve.doubleValue());
        saleDTO.setProfit(profit.doubleValue());

        //get ArticleId from referenced Cost
        saleDTO.setArticleId(article);

        Sale newSale = new Sale();

        newSale.setCustomerId(saleDTO.getCustomerId());
        newSale.setCostId(saleDTO.getCostId());
        newSale.setArticleId(saleDTO.getArticleId());
        newSale.setAmount(saleDTO.getAmount());
        newSale.setDateOfSale(saleDTO.getDateOfSale());
        newSale.setPrice(saleDTO.getPrice());
        newSale.setShipping(saleDTO.getShipping());
        newSale.setFee(saleDTO.getFee());
        newSale.setReserve(saleDTO.getReserve());
        newSale.setProfit(saleDTO.getProfit());
        newSale.setStatus(Sale.Status.COMPLETED);

        return databaseRepository.save(newSale);
    }

    @Transactional
    public Sale updateStatus(int id, SaleDTO saleDTO) {

        Sale existingSaleOptional = databaseRepository.findById(Sale.class, id);
        if (existingSaleOptional == null) {
            throw new EntityNotFoundException("Sale not found with ID: " + id);
        }else {
            if (saleDTO.getStatus() == Sale.Status.RETURNED) {
                handleSaleReturned(existingSaleOptional);
            }else if (saleDTO.getStatus() == Sale.Status.COMPLETED) {
                handleSaleCompleted(existingSaleOptional);
            }
            return databaseRepository.save(existingSaleOptional);
        }
    }

    private void handleSaleReturned(Sale existingSale) {
        ReturnDTO returnDTO = new ReturnDTO();

        returnDTO.setCustomerId(databaseRepository.findById(Customer.class, existingSale.getCustomerId().getId()));
        returnDTO.setSaleId(existingSale);
        returnDTO.setAmount(existingSale.getAmount());
        returnDTO.setReturnDate(LocalDate.now());

        existingSale.setStatus(Sale.Status.RETURNED);
        returnService.createReturn(returnDTO);
    }

    private void handleSaleCompleted(Sale existingSale) {

        returnService.deleteReturnBySaleId(existingSale);
        existingSale.setStatus(Sale.Status.COMPLETED);
    }


    public void deleteSale(int id) {
        if(databaseRepository.findById(Sale.class, id) == null) {
            throw new EntityNotFoundException("Sale not found with ID: " + id);
        }else {
            Sale sale = databaseRepository.findById(Sale.class, id);
            Inventory inventoryItem = databaseRepository.findByForeignKey(Inventory.class, "article", sale.getArticleId().getId());
            inventoryItem.setAmount(inventoryItem.getAmount() + sale.getAmount());

            databaseRepository.delete(Sale.class, id);
        }

    }

}
