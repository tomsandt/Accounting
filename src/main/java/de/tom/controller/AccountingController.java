package de.tom.controller;

import de.tom.domain.*;
import de.tom.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountingController {


    @Autowired
    ArticleService articleService;

    @Autowired
    CostService costService;

    @Autowired
    CustomerService customerService;

    @Autowired
    DealerService dealerService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ReturnService returnService;

    @Autowired
    SaleService saleService;

    //------------------- ARTICLE -------------------

    @PostMapping("/article/create")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleDTO articleDTO) {
        Article newArticle = articleService.createArticle(articleDTO);
        return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable int id) {
        Optional<Article> article = articleService.getArticleById(id);
        if(article.isPresent()) {
            return ResponseEntity.ok(article.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable int id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //------------------- COST -------------------

    @PostMapping("/cost/create")
    public ResponseEntity<Cost> createCost(@RequestBody CostDTO costDTO) {
        Cost newCost = costService.createCost(costDTO);
        return new ResponseEntity<>(newCost, HttpStatus.CREATED);
    }

    @GetMapping("/costs")
    public ResponseEntity<List<Cost>> getAllCosts() {
        List<Cost> costs = costService.getAllCosts();
        return ResponseEntity.ok(costs);
    }

    @GetMapping("/cost/{id}")
    public ResponseEntity<Cost> getCost(@PathVariable int id) {
        Cost cost = costService.getCostById(id);
        if(cost != null) {
            return ResponseEntity.ok(cost);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/cost/{id}")
    public ResponseEntity<Cost> deleteCost(@PathVariable int id) {
        try {
            costService.deleteCost(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //------------------- CUSTOMER -------------------

    @PostMapping("/customer/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer newCustomer = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);
        if(customer != null) {
            return ResponseEntity.ok(customer);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //------------------- DEALER -------------------

    @PostMapping("/dealer/create")
    public ResponseEntity<Dealer> createDealer(@RequestBody DealerDTO dealerDTO) {
        Dealer newDealer = dealerService.createDealer(dealerDTO);
        return new ResponseEntity<>(newDealer, HttpStatus.CREATED);
    }

    @GetMapping("/dealers")
    public ResponseEntity<List<Dealer>> getAllDealers() {
        List<Dealer> dealers = dealerService.getAllDealers();
        return ResponseEntity.ok(dealers);
    }

    @GetMapping("/dealer/{id}")
    public ResponseEntity<Dealer> getDealer(@PathVariable int id) {
        Dealer dealer = dealerService.getDealerById(id);
        if(dealer != null) {
            return ResponseEntity.ok(dealer);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/dealer/{id}")
    public ResponseEntity<Dealer> deleteDealer(@PathVariable int id) {
        try {
            dealerService.deleteDealer(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //------------------- INVENTORY -------------------

    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getInventory() {
        List<Inventory> inventory = inventoryService.getInventory();
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<Inventory> getInventoryItem(@PathVariable int id) {
        Optional<Inventory> inventoryItem = inventoryService.getInventoryItemById(id);
        if(inventoryItem.isPresent()) {
            return ResponseEntity.ok(inventoryItem.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //------------------- RETURN -------------------

    @PostMapping("/return/create")
    public ResponseEntity<Return> createReturn(@RequestBody ReturnDTO returnDTO) {
        Return newReturn = returnService.createReturn(returnDTO);
        return new ResponseEntity<>(newReturn, HttpStatus.CREATED);
    }

    @GetMapping("/returns")
    public ResponseEntity<List<Return>> getAllReturns() {
        List<Return> returns = returnService.getAllReturns();
        return ResponseEntity.ok(returns);
    }

    @GetMapping("/return/{id}")
    public ResponseEntity<Return> getReturn(@PathVariable int id) {
        Optional<Return> returnItem = returnService.getReturnById(id);
        if(returnItem.isPresent()) {
            return ResponseEntity.ok(returnItem.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/return/{id}")
    public ResponseEntity<Return> deleteReturn(@PathVariable int id) {
        try {
            returnService.deleteReturn(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //------------------- SALE -------------------

    @PostMapping("/sale/create")
    public ResponseEntity<Sale> createSale(@RequestBody SaleDTO saleDTO) {
        Sale newSale = saleService.createSale(saleDTO);
        return new ResponseEntity<>(newSale, HttpStatus.CREATED);
    }

    @GetMapping("/sales")
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/sale/{id}")
    public ResponseEntity<Sale> getSale(@PathVariable int id) {
        Sale sale = saleService.getSaleById(id);
        if(sale != null) {
            return ResponseEntity.ok(sale);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/sale/{id}")
    public ResponseEntity<Sale> updateStatus(@PathVariable int id, @RequestBody SaleDTO saleDTO) {
        try {
            Sale updatedSaleStatus = saleService.updateStatus(id, saleDTO);
            return ResponseEntity.ok(updatedSaleStatus);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/sale/{id}")
    public ResponseEntity<Sale> deleteSale(@PathVariable int id) {
        try {
            saleService.deleteSale(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
