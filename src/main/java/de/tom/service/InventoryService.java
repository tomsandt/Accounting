package de.tom.service;

import de.tom.domain.Inventory;
import de.tom.domain.InventoryDTO;
import de.tom.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryItemById(int id) {
        return Optional.ofNullable(inventoryRepository.findByArticleId_Id(id));
    }

    public Inventory createInventoryItem(InventoryDTO inventoryDTO) {

        Inventory newInventoryItem = new Inventory();

        newInventoryItem.setArticleId(inventoryDTO.getArticleId());
        newInventoryItem.setAmount(inventoryDTO.getAmount());
        newInventoryItem.setPurchaseDate(inventoryDTO.getPurchaseDate());

        return inventoryRepository.save(newInventoryItem);
    }

    public void deleteInventoryItem(int id) {
        if(!inventoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Article not found with the ID: " + id);
        }else {
            inventoryRepository.deleteById(id);
        }
    }

}
