package de.tom.service;

import de.tom.domain.Inventory;
import de.tom.domain.InventoryDTO;
import de.tom.repository.DatabaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private DatabaseRepository databaseRepository;

    public List<Inventory> getInventory() {
        return databaseRepository.findAll(Inventory.class);
    }

    public Optional<Inventory> getInventoryItemById(int id) {
        return Optional.ofNullable(databaseRepository.findByForeignKey(Inventory.class, "article", id));
    }

    public Inventory createInventoryItem(InventoryDTO inventoryDTO) {

        Inventory newInventoryItem = new Inventory();

        newInventoryItem.setArticle(inventoryDTO.getArticle());
        newInventoryItem.setAmount(inventoryDTO.getAmount());
        newInventoryItem.setPurchaseDate(inventoryDTO.getPurchaseDate());

        return databaseRepository.save(newInventoryItem);
    }

    public void deleteInventoryItem(int id) {
        if(databaseRepository.findById(Inventory.class, id) == null) {
            throw new EntityNotFoundException("Article not found with the ID: " + id);
        }else {
            databaseRepository.delete(Inventory.class, id);
        }
    }

}
