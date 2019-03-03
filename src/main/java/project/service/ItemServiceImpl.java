package project.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Item;
import project.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private ItemRepository itemRepository;

	@Override
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Override
	public Item save(Item i) {
		Assert.notNull(i);
		return itemRepository.save(i);
	}

	@Override
	public List<Item> getAllByName(String name, String stat) {
		Assert.notNull(name);
		Assert.notNull(stat);
		return itemRepository.getAllByName(name, stat);
	}

	@Override
	public Item getItemByKey(Long id, String uname) {
		Assert.notNull(uname);
		Assert.notNull(id);
		return itemRepository.getItemByKey(id, uname);
	}

	@Override
	public void removeItemByKey(Long id, String uname) {
		Assert.notNull(uname);
		Assert.notNull(id);
		itemRepository.removeItemByKey(id, uname);
	}

	@Override
	public void updateItemCurrency(Long id, String uname, String currency) {
		Assert.notNull(uname);
		Assert.notNull(id);
		Assert.notNull(currency);
		itemRepository.updateItemCurrency(id, uname, currency);
	}

	@Override
	public void updateItemPrice(Long id, String uname, BigDecimal price) {
		Assert.notNull(uname);
		Assert.notNull(id);
		Assert.notNull(price);
		itemRepository.updateItemPrice(id, uname, price);
	}

	@Override
	public void updateItemImage(Long id, String uname, String image) {
		Assert.notNull(uname);
		Assert.notNull(id);
		Assert.notNull(image);
		itemRepository.updateItemImage(id, uname, image);
	}

	@Override
	public void updateItemName(Long id, String uname, String name) {
		Assert.notNull(uname);
		Assert.notNull(id);
		Assert.notNull(name);
		itemRepository.updateItemName(id, uname, name);
	}

	@Override
	public void updateItemCurrencyAndPrice(Long id, String uname, String currency, BigDecimal price) {
		Assert.notNull(uname);
		Assert.notNull(id);
		Assert.notNull(currency);
		Assert.notNull(price);
		itemRepository.updateItemCurrencyAndPrice(id, uname, currency, price);
	}

	@Override
	public void updateItemInfo(Long id, String uname, String currency, BigDecimal price, String image, String name) {
		Assert.notNull(uname);
		Assert.notNull(id);
		Assert.notNull(currency);
		Assert.notNull(price);
		Assert.notNull(image);
		Assert.notNull(name);
		itemRepository.updateItemInfo(id, uname, currency, price, image, name);
	}

	@Override
	public List<Item> getItemByPrice(BigDecimal price, String stat) {
		Assert.notNull(price);
		Assert.notNull(stat);
		return itemRepository.getItemByPrice(price, stat);
	}

	@Override
	public List<Item> getItemByCurrency(String currency, String stat) {
		Assert.notNull(currency);
		Assert.notNull(stat);
		return itemRepository.getItemByCurrency(currency, stat);
	}

	@Override
	public List<Item> getItemBySeller(String name, String stat) {
		Assert.notNull(name);
		Assert.notNull(stat);
		return itemRepository.getItemBySeller(name, stat);
	}

	@Override
	public List<Item> getItemsByGame(Long id, String stat) {
		Assert.notNull(id);
		Assert.notNull(stat);
		return itemRepository.getItemsByGame(id, stat);
	}

	@Override
	public List<Item> getAllByBuyer(String name, String stat) {
		Assert.notNull(name);
		Assert.notNull(stat);
		return itemRepository.getAllByBuyer(name, stat);
	}

	@Override
	public List<Item> getAllBySeller(String name, String stat) {
		Assert.notNull(name);
		Assert.notNull(stat);
		return itemRepository.getAllBySeller(name, stat);
	}

	@Override
	public List<Item> getAllByNameBought(String name, String buyer, String stat) {
		Assert.notNull(name);
		Assert.notNull(buyer);
		Assert.notNull(stat);
		return itemRepository.getAllByNameBought(name, buyer, stat);
	}

	@Override
	public List<Item> getAllByPriceBought(BigDecimal price, String buyer, String stat) {
		Assert.notNull(price);
		Assert.notNull(buyer);
		Assert.notNull(stat);
		return itemRepository.getAllByPriceBought(price, buyer, stat);
	}

	@Override
	public List<Item> getAllByCurrencyBought(String currency, String buyer, String stat) {
		Assert.notNull(currency);
		Assert.notNull(buyer);
		Assert.notNull(stat);
		return itemRepository.getAllByCurrencyBought(currency, buyer, stat);
	}

	@Override
	public List<Item> getAllBySellerBought(String name, String buyer, String stat) {
		Assert.notNull(name);
		Assert.notNull(buyer);
		Assert.notNull(stat);
		return itemRepository.getAllBySellerBought(name, buyer, stat);
	}

	@Override
	public List<Item> getAllByNameSold(String name, String seller, String stat) {
		Assert.notNull(name);
		Assert.notNull(seller);
		Assert.notNull(stat);
		return itemRepository.getAllByNameSold(name, seller, stat);
	}

	@Override
	public List<Item> getAllByPriceSold(BigDecimal price, String seller, String stat) {
		Assert.notNull(price);
		Assert.notNull(seller);
		Assert.notNull(stat);
		return itemRepository.getAllByPriceSold(price, seller, stat);
	}

	@Override
	public List<Item> getAllByCurrencySold(String currency, String seller, String stat) {
		Assert.notNull(currency);
		Assert.notNull(seller);
		Assert.notNull(stat);
		return itemRepository.getAllByCurrencySold(currency, seller, stat);
	}

	@Override
	public List<Item> getAllByBuyerSold(String name, String seller, String stat) {
		Assert.notNull(name);
		Assert.notNull(seller);
		Assert.notNull(stat);
		return itemRepository.getAllByBuyerSold(name, seller, stat);
	}

	@Override
	public List<Item> getAllInStock(String stat) {
		Assert.notNull(stat);
		return itemRepository.getAllInStock(stat);
	}

	@Override
	public List<Item> getItemByBuyer(String name, String stat) {
		Assert.notNull(name);
		Assert.notNull(stat);
		return itemRepository.getItemByBuyer(name, stat);
	}

}
