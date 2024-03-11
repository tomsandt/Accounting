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

    @Autowired
    private ReturnService returnService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReturnRepository returnRepository;

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

        return saleRepository.save(newSale);
    }

    @Transactional
    public Sale updateStatus(int id, SaleDTO saleDTO) {

        Optional<Sale> existingSaleOptional = saleRepository.findById(id);
        if (!existingSaleOptional.isPresent()) {
            throw new EntityNotFoundException("Sale not found with ID: " + id);
        }else {
            Sale existingSale = existingSaleOptional.get();
            if (saleDTO.getStatus() == Sale.Status.RETURNED) {
                handleSaleReturned(existingSale);
            }else if (saleDTO.getStatus() == Sale.Status.COMPLETED) {
                handleSaleCompleted(existingSale);
            }
            return saleRepository.save(existingSale);
        }
    }

    private void handleSaleReturned(Sale existingSale) {
        ReturnDTO returnDTO = new ReturnDTO();

        returnDTO.setCustomerId(customerRepository.findCustomerById(existingSale.getCustomerId().getId()));
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
        if(!saleRepository.existsById(id)) {
            throw new EntityNotFoundException("Sale not found with ID: " + id);
        }else {
            saleRepository.deleteById(id);
        }

    }

}
