package project.service;

import java.math.BigDecimal;
import java.util.List;

import project.domain.Item;

public interface ItemService {
	
	List<Item> findAll();
	
	Item save(Item i);
	
	List<Item> getAllByName(String name, String stat);
	
	Item getItemByKey(Long id, String uname);
	
	List<Item> getItemByPrice(BigDecimal price, String stat);
	
	List<Item> getItemByCurrency(String currency, String stat);
	
	List<Item> getItemBySeller(String name, String stat);
	
	List<Item> getItemsByGame(Long id, String stat);
	
	void removeItemByKey(Long id, String uname);
	
	void updateItemCurrency(Long id, String uname, String currency);
	
	void updateItemPrice(Long id, String uname, BigDecimal price);
	
	void updateItemImage(Long id, String uname, String image);
	
	void updateItemName(Long id, String uname, String name);
	
	void updateItemCurrencyAndPrice(Long id, String uname, String currency, BigDecimal price);
	
	void updateItemInfo(Long id, String uname, String currency, BigDecimal price, String image, String name);
	
	List<Item> getAllByBuyer(String name, String stat);
	
	List<Item> getAllBySeller(String name, String stat);
	
	List<Item> getAllByNameBought(String name, String buyer, String stat);
	
	List<Item> getAllByPriceBought(BigDecimal price, String buyer, String stat);
	
	List<Item> getAllByCurrencyBought(String currency, String buyer, String stat);
	
	List<Item> getAllBySellerBought(String name, String buyer, String stat);
	
	List<Item> getAllByNameSold(String name, String seller, String stat);
	
	List<Item> getAllByPriceSold(BigDecimal price, String seller, String stat);
	
	List<Item> getAllByCurrencySold(String currency, String seller, String stat);
	
	List<Item> getAllByBuyerSold(String name, String seller, String stat);
	
	List<Item> getAllInStock(String stat);
	
	List<Item> getItemByBuyer(String name, String stat);


}
