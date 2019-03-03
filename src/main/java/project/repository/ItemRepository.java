package project.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Game;
import project.domain.Item;
import project.domain.ItemPK;

public interface ItemRepository extends Repository<Item, ItemPK>{
	
	public List<Item> findAll();
	
	public Item save(Item i);
	
	@Query("select i from Item i where UPPER(i.itemname) LIKE UPPER( ?1 ) AND i.itemstat = ?2")
	public List<Item> getAllByName(String name, String stat);
	
	@Query("select i from Item i where i.itemstat = ?1")
	public List<Item> getAllInStock(String stat);
	
	@Query("select i from Item i where i.itembuy = ?1 AND i.itemstat = ?2")
	public List<Item> getAllByBuyer(String name, String stat);
	
	@Query("select i from Item i where i.gameUserUname = ?1 AND i.itemstat = ?2")
	public List<Item> getAllBySeller(String name, String stat);
	
	@Query("select i from Item i where UPPER(i.itemname) LIKE UPPER( ?1 ) AND i.itembuy = ?2 AND i.itemstat = ?3")
	public List<Item> getAllByNameBought(String name, String buyer, String stat);
	
	@Query("select i from Item i where i.itemprice = ?1 AND i.itembuy = ?2 AND i.itemstat = ?3")
	public List<Item> getAllByPriceBought(BigDecimal price, String buyer, String stat);
	
	@Query("select i from Item i where UPPER(i.itemcurr) LIKE UPPER( ?1 ) AND i.itembuy = ?2 AND i.itemstat = ?3")
	public List<Item> getAllByCurrencyBought(String currency, String buyer, String stat);
	
	@Query("select i from Item i where UPPER(i.gameUser.uname) LIKE UPPER( ?1 ) AND i.itembuy = ?2 AND i.itemstat = ?3")
	public List<Item> getAllBySellerBought(String name, String buyer, String stat);
	
	@Query("select i from Item i where UPPER(i.itemname) LIKE UPPER( ?1 ) AND i.gameUserUname = ?2 AND i.itemstat = ?3")
	public List<Item> getAllByNameSold(String name, String seller, String stat);
	
	@Query("select i from Item i where i.itemprice = ?1 AND i.gameUserUname = ?2 AND i.itemstat = ?3")
	public List<Item> getAllByPriceSold(BigDecimal price, String seller, String stat);
	
	@Query("select i from Item i where UPPER(i.itemcurr) LIKE UPPER( ?1 ) AND i.gameUserUname = ?2 AND i.itemstat = ?3")
	public List<Item> getAllByCurrencySold(String currency, String seller, String stat);
	
	@Query("select i from Item i where UPPER(i.itembuy) LIKE UPPER( ?1 ) AND i.gameUserUname = ?2 AND i.itemstat = ?3")
	public List<Item> getAllByBuyerSold(String name, String seller, String stat);
	
	@Query("select i from Item i where i.itemid = ?1 AND i.gameUserUname = ?2")
	public Item getItemByKey(Long id, String uname);
	
	@Query("select i from Item i where i.itemprice = ?1 AND i.itemstat = ?2")
	public List<Item> getItemByPrice(BigDecimal price, String stat);
	
	@Query("select i from Item i where UPPER(i.itemcurr) LIKE UPPER( ?1 ) AND i.itemstat = ?2")
	public List<Item> getItemByCurrency(String currency, String stat);
	
	@Query("select i from Item i where UPPER(i.gameUser.uname) LIKE UPPER( ?1 ) AND i.itemstat = ?2")
	public List<Item> getItemBySeller(String name, String stat);
	
	@Query("select i from Item i where UPPER(i.itembuy) LIKE UPPER( ?1 ) AND i.itemstat = ?2")
	public List<Item> getItemByBuyer(String name, String stat);
	
	@Query("select i from Item i where i.gameGameid = ?1 AND i.itemstat = ?2")
	public List<Item> getItemsByGame(Long id, String stat);
	
	@Modifying
	@Query("delete from Item i where i.itemid = ?1 AND i.gameUserUname = ?2")
	public void removeItemByKey(Long id, String uname);
	
	@Modifying
	@Query("update Item i set i.itemcurr = ?3 where i.itemid = ?1 AND i.gameUserUname = ?2 ")
	public void updateItemCurrency(Long id, String uname, String currency);
	
	@Modifying
	@Query("update Item i set i.itemprice = ?3 where i.itemid = ?1 AND i.gameUserUname = ?2 ")
	public void updateItemPrice(Long id, String uname, BigDecimal price);
	
	@Modifying
	@Query("update Item i set i.itemim = ?3 where i.itemid = ?1 AND i.gameUserUname = ?2 ")
	public void updateItemImage(Long id, String uname, String image);
	
	@Modifying
	@Query("update Item i set i.itemname = ?3 where i.itemid = ?1 AND i.gameUserUname = ?2 ")
	public void updateItemName(Long id, String uname, String name);
	
	@Modifying
	@Query("update Item i set i.itemcurr = ?3 , i.itemprice = ?4 where i.itemid = ?1 AND i.gameUserUname = ?2 ")
	public void updateItemCurrencyAndPrice(Long id, String uname, String currency, BigDecimal price);
	
	@Modifying
	@Query("update Item i set i.itemcurr = ?3 , i.itemprice = ?4, i.itemim = ?5, i.itemname = ?6 where i.itemid = ?1 AND i.gameUserUname = ?2 ")
	public void updateItemInfo(Long id, String uname, String currency, BigDecimal price, String image, String name);

}
