package project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.domain.Active;
import project.domain.Game;
import project.domain.GameUser;
import project.domain.Item;
import project.domain.dto.ItemDTO;
import project.domain.dto.ItemKeyDTO;
import project.service.GameService;
import project.service.GameUserService;
import project.service.ItemService;

@RequestMapping("/items")
@Controller
public class ItemsController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private GameUserService gameUserService;
	
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value = "/getItems",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> getAllItems(@Context HttpServletRequest request) {
		
			List<Item> items = itemService.getAllInStock("IN STOCK");
			
		
			
			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getItemsByGame",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> getItemsByGame(@Context HttpServletRequest request, @RequestBody String data) {
		
			List<Item> items = itemService.getItemsByGame(new Long(data), "IN STOCK");
			
			Collections.sort(items, new Comparator() {
	            @Override
	            public int compare(Object itemOne, Object itemTwo) {

	                return ((Item)itemOne).getItemname().toUpperCase()
	                        .compareTo(((Item)itemTwo).getItemname().toUpperCase());
	            }
	        }); 
			
		
			
			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterName",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterItemName(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Item> items = itemService.getAllByName(newData, "IN STOCK");
			
			Collections.sort(items, new Comparator() {
	            @Override
	            public int compare(Object itemOne, Object itemTwo) {

	                return ((Item)itemOne).getItemname().toUpperCase()
	                        .compareTo(((Item)itemTwo).getItemname().toUpperCase());
	            }
	        }); 


				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("IN STOCK");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterPrice",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterPrice(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			int price = Integer.parseInt(data);
			BigDecimal cena = new BigDecimal(price);
			List<Item> items = itemService.getItemByPrice(cena,"IN STOCK");


				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("IN STOCK");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterCurrency",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterUser(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Item> items = itemService.getItemByCurrency(newData,"IN STOCK");


				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("IN STOCK");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterSeller",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterSeller(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Item> items = itemService.getItemBySeller(newData,"IN STOCK");


				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("IN STOCK");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/addItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> addItem(@Context HttpServletRequest request, @RequestBody ItemDTO data) {
		if (data != null) {
			
			if(data.getItemname() == "" || data.getItemname()==null){
				return new ResponseEntity<String>("Please, fill in the item name field.", HttpStatus.OK);
			} else if(data.getItemprice() == "" || data.getItemprice()==null){
				return new ResponseEntity<String>("Please, fill in the item price field.", HttpStatus.OK);
			} else if(data.getItemq() == "" || data.getItemq()==null){
				return new ResponseEntity<String>("Please, fill in the item quantity field.", HttpStatus.OK);
			} else if(data.getItemcurr() == "" || data.getItemcurr()==null){
				return new ResponseEntity<String>("Please, select a valid currency.", HttpStatus.OK);
			} else if(data.getItemim() == "" || data.getItemim()==null){
				return new ResponseEntity<String>("Please, select the item image.", HttpStatus.OK);
			}
			
			Item item = new Item();
			item.setItemcurr(data.getItemcurr());
			item.setItemprice(new BigDecimal(data.getItemprice()));
			item.setItemname(data.getItemname());
			
			item.setItemstat("IN STOCK");
			
			item.setGameGameid(new Long(data.getGameId()));
			Game game = gameService.getGameById(new Long(data.getGameId()));
			item.setGame(game);
			item.setItemq(new Long(data.getItemq()));
			Active current = (Active) request.getSession().getAttribute("user");
			
			
			GameUser user = gameUserService.getGameUserByName(current.getActuser().getUname());
			item.setGameUser(user);
			item.setGameUserUname(user.getUname());
			user.addItem(item);
			final String dir = System.getProperty("user.dir");
			File theDir = new File(dir + "\\src\\main\\resources\\static\\data\\items\\"+user.getUname());
			File theDir2 = new File(dir + "\\target\\classes\\static\\data\\items\\"+user.getUname());
			// if the directory does not exist, create it
			if (!theDir.exists()) {
			    System.out.println("creating directories: " + theDir.getName());
			    boolean result = false;

			    try{
			        theDir.mkdir();
			        theDir2.mkdir();
			        result = true;
			    } 
			    catch(SecurityException se){
			        //handle it
			    }        
			    if(result) {    
			        System.out.println("DIRs created");  
			    }
			}

			itemService.save(item);
			if(data.getItemim()!=null && data.getItemim()!=""){
				String base64Image = data.getItemim().split(",")[1];
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
				
				Long sequence = item.getItemid();
				String imagePath = "../../data/items/"+user.getUname()+"/" + sequence + ".jpg";
				item.setItemim(imagePath);
				String savePath = dir + "\\src\\main\\resources\\static\\data\\items\\"+user.getUname()+"\\"+sequence+".jpg";
				String savePath2 = dir + "\\target\\classes\\static\\data\\items\\"+user.getUname()+"\\"+sequence+".jpg";
				OutputStream out = null;
				OutputStream out2 = null;
	
				try {
				    out = new BufferedOutputStream(new FileOutputStream(savePath));
				    out2 = new BufferedOutputStream(new FileOutputStream(savePath2));
				    out.write(imageBytes);
				    out2.write(imageBytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				finally {
					try {
						if (out != null) out.close();
						if (out2 != null) out2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}


			return new ResponseEntity<String>("Item successfully added", HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/getItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Item> getItem(@Context HttpServletRequest request, @RequestBody ItemKeyDTO data) {
		
		if (data != null) {
			Long id = new Long(data.getId());
			String uname = data.getUname();
			Item item = itemService.getItemByKey(id, uname);
			System.out.println("POvukao"+item.getItemname());
			return new ResponseEntity<Item>(item, HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/update",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> update(@Context HttpServletRequest request, @RequestBody ItemDTO data) {
		if (data != null) {
			
			if(data.getItemname() == "" || data.getItemname()==null){
				return new ResponseEntity<String>("Please, fill in the item name field.", HttpStatus.OK);
			} else if(data.getItemprice() == "" || data.getItemprice()==null){
				return new ResponseEntity<String>("Please, fill in the item price field.", HttpStatus.OK);
			} else if(data.getItemq() == "" || data.getItemq()==null){
				return new ResponseEntity<String>("Please, fill in the item quantity field.", HttpStatus.OK);
			} else if(data.getItemcurr() == "" || data.getItemcurr()==null){
				return new ResponseEntity<String>("Please, select a valid currency.", HttpStatus.OK);
			} else if(data.getItemim() == "" || data.getItemim()==null){
				return new ResponseEntity<String>("Please, select the item image.", HttpStatus.OK);
			}
			
			Item item = itemService.getItemByKey(new Long(data.getId()), data.getUname());
			
			item.setItemcurr(data.getItemcurr());
			
			item.setItemname(data.getItemname());
			
			item.setItemprice(new BigDecimal(data.getItemprice()));
			
			item.setItemstat("IN STOCK");
			
			item.setItemq(new Long(data.getItemq()));
			
			final String dir = System.getProperty("user.dir");
			if(item.getItemim()!=null){
				if(!data.getItemim().toString().equals(item.getItemim())){
					String base64Image = data.getItemim().split(",")[1];
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					Long sequence = item.getItemid();
					String imagePath = "../../data/items/"+item.getGameUserUname()+"/" + sequence + ".jpg";
					item.setItemim(imagePath);
					String savePath = dir + "\\src\\main\\resources\\static\\data\\items\\"+item.getGameUserUname()+"\\"+sequence+".jpg";
					String savePath2 = dir + "\\target\\classes\\static\\data\\items\\"+item.getGameUserUname()+"\\"+sequence+".jpg";
					OutputStream out = null;
					OutputStream out2 = null;
		
					try {
					    out = new BufferedOutputStream(new FileOutputStream(savePath));
					    out2 = new BufferedOutputStream(new FileOutputStream(savePath2));
					    out.write(imageBytes);
					    out2.write(imageBytes);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					finally {
						try {
							if (out != null) out.close();
							if (out2 != null) out2.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		} else {
			if(data.getItemim()!=null && data.getItemim()!=""){
				String base64Image = data.getItemim().split(",")[1];
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
				Long sequence = item.getItemid();
				String imagePath = "../../data/items/"+data.getUname()+"/" + sequence + ".jpg";
				item.setItemim(imagePath);
				String savePath = dir + "\\src\\main\\resources\\static\\data\\items\\"+data.getUname()+"\\"+sequence+".jpg";
				String savePath2 = dir + "\\target\\classes\\static\\data\\items\\"+data.getUname()+"\\"+sequence+".jpg";
				OutputStream out = null;
				OutputStream out2 = null;
	
				try {
				    out = new BufferedOutputStream(new FileOutputStream(savePath));
				    out2 = new BufferedOutputStream(new FileOutputStream(savePath2));
				    out.write(imageBytes);
				    out2.write(imageBytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				finally {
					try {
						if (out != null) out.close();
						if (out2 != null) out2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
			
			itemService.updateItemInfo(item.getItemid(), item.getGameUserUname(), item.getItemcurr(), item.getItemprice(), item.getItemim(), item.getItemname());
			
			return new ResponseEntity<String>("Item successfully updated", HttpStatus.OK);

		}
		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/removeItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Item> removeItem(@Context HttpServletRequest request, @RequestBody ItemKeyDTO data) {

		if (data != null) {
			Long id = new Long(data.getId());
			
			Item item = itemService.getItemByKey(id, data.getUname());
			
			
			itemService.removeItemByKey(id, data.getUname());
			
			final String dir = System.getProperty("user.dir");
			Long sequence = item.getItemid();
			String savePath = dir + "\\src\\main\\resources\\static\\data\\items\\"+data.getUname()+"\\"+sequence+".jpg";
			String savePath2 = dir + "\\target\\classes\\static\\data\\items\\"+data.getUname()+"\\"+sequence+".jpg";
			File file = new File(savePath);
			File file2 = new File(savePath2);
			file.delete();
			file2.delete();
			
		
				return new ResponseEntity<Item>(item, HttpStatus.OK);

		}

		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/buyItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Item> buyItem(@Context HttpServletRequest request, @RequestBody ItemKeyDTO data) {

		if (data != null) {
			Long id = new Long(data.getId());
			Item item = itemService.getItemByKey(id, data.getUname());
			item.setItemstat("SOLD");
			Active current = (Active) request.getSession().getAttribute("user");
			
			
			GameUser user = gameUserService.getGameUserByName(current.getActuser().getUname());
			item.setGameUser2(user);
			item.setItembuy(user.getUname());
			user.getItems2().add(item);
			itemService.save(item);
			gameUserService.save(user);
		
				return new ResponseEntity<Item>(item, HttpStatus.OK);

		}

		return null;
		
	}
	
	
	@Transactional
	@RequestMapping(value = "/finalizeDeal",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Item> finalizeDeal(@Context HttpServletRequest request, @RequestBody ItemKeyDTO data) {

		if (data != null) {
			Long id = new Long(data.getId());
			Item item = itemService.getItemByKey(id, data.getUname());
			item.setItemstat("FINALIZED");
			
			itemService.save(item);
		
				return new ResponseEntity<Item>(item, HttpStatus.OK);

		}

		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/cancelDeal",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Item> cancelDeal(@Context HttpServletRequest request, @RequestBody ItemKeyDTO data) {

		if (data != null) {
			Long id = new Long(data.getId());
			Item item = itemService.getItemByKey(id, data.getUname());
			item.setItemstat("IN STOCK");
			GameUser user = gameUserService.getGameUserByName(item.getItembuy());
			item.setGameUser2(null);
			item.setItembuy(null);
			user.getItems2().remove(item);
			itemService.save(item);
			gameUserService.save(user);
		
				return new ResponseEntity<Item>(item, HttpStatus.OK);

		}

		return null;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterNameBought",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterNameBought(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			List<Item> items = itemService.getAllByNameBought(newData, current.getActuser().getUname(), "SOLD");


			Collections.sort(items, new Comparator() {
	            @Override
	            public int compare(Object itemOne, Object itemTwo) {

	                return ((Item)itemOne).getItemname().toUpperCase()
	                        .compareTo(((Item)itemTwo).getItemname().toUpperCase());
	            }
	        }); 
			
				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}

		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterPriceBought",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterPriceBought(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			int price = Integer.parseInt(data);
			BigDecimal cena = new BigDecimal(price);
			Active current = (Active) request.getSession().getAttribute("user");
			List<Item> items = itemService.getAllByPriceBought(cena, current.getActuser().getUname(), "SOLD");



				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterCurrencyBought",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterCurrencyBought(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			Active current = (Active) request.getSession().getAttribute("user");
			List<Item> items = itemService.getAllByCurrencyBought(newData, current.getActuser().getUname(), "SOLD");



				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterSellerBought",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterSellerBought(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			Active current = (Active) request.getSession().getAttribute("user");
			List<Item> items = itemService.getAllBySellerBought(newData, current.getActuser().getUname(), "SOLD");



				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		return null;
		
	}
	
	@RequestMapping(value = "/getAllByBuyer",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> getAllByBuyer(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			List<Item> items = itemService.getAllByBuyer(current.getActuser().getUname(),"SOLD");
			
		
			
			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterNameSold",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterNameSold(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			
			Active current = (Active) request.getSession().getAttribute("user");
			
			List<Item> items = itemService.getAllByNameSold(newData, current.getActuser().getUname(), "SOLD");

			Collections.sort(items, new Comparator() {
	            @Override
	            public int compare(Object itemOne, Object itemTwo) {

	                return ((Item)itemOne).getItemname().toUpperCase()
	                        .compareTo(((Item)itemTwo).getItemname().toUpperCase());
	            }
	        }); 

				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}

		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterPriceSold",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterPriceSold(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			int price = Integer.parseInt(data);
			BigDecimal cena = new BigDecimal(price);
			Active current = (Active) request.getSession().getAttribute("user");
			List<Item> items = itemService.getAllByPriceSold(cena, current.getActuser().getUname(), "SOLD");



				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterCurrencySold",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterCurrencySold(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			Active current = (Active) request.getSession().getAttribute("user");
			List<Item> items = itemService.getAllByCurrencySold(newData, current.getActuser().getUname(), "SOLD");



				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		return null;
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterBuyerSold",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterBuyerSold(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			Active current = (Active) request.getSession().getAttribute("user");
			List<Item> items = itemService.getAllByBuyerSold(newData, current.getActuser().getUname(), "SOLD");



				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		return null;
		
	}
	
	@RequestMapping(value = "/getAllBySeller",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> getAllBySeller(@Context HttpServletRequest request) {
		
			Active current = (Active) request.getSession().getAttribute("user");
			List<Item> items = itemService.getAllBySeller(current.getActuser().getUname(),"SOLD");
			
		
			
			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getAllFinalized",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> getAllFinalized(@Context HttpServletRequest request) {
		
			List<Item> items = itemService.getAllInStock("FINALIZED");
			
		
			
			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@RequestMapping(value = "/filterNameFinalized",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterNameFinalized(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Item> items = itemService.getAllByName(newData, "FINALIZED");

			Collections.sort(items, new Comparator() {
	            @Override
	            public int compare(Object itemOne, Object itemTwo) {

	                return ((Item)itemOne).getItemname().toUpperCase()
	                        .compareTo(((Item)itemTwo).getItemname().toUpperCase());
	            }
	        }); 

				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("FINALIZED");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterPriceFinalized",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterPriceFinalized(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			int price = Integer.parseInt(data);
			BigDecimal cena = new BigDecimal(price);
			List<Item> items = itemService.getItemByPrice(cena,"FINALIZED");


				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("FINALIZED");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterCurrencyFinalized",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterCurrencyFinalized(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Item> items = itemService.getItemByCurrency(newData,"FINALIZED");


				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("FINALIZED");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterSellerFinalized",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterSellerFinalized(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Item> items = itemService.getItemBySeller(newData,"FINALIZED");


				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("FINALIZED");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}
	
	@Transactional
	@RequestMapping(value = "/filterBuyerFinalized",
			method = RequestMethod.POST,
			consumes = MediaType.TEXT_PLAIN,
			produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Item>> filterBuyerFinalized(@Context HttpServletRequest request, @RequestBody String data) {

		if (data != null) {
			String newData ="%";
			newData = newData+ data;
			newData = newData+"%";
			List<Item> items = itemService.getItemByBuyer(newData,"FINALIZED");


				return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

		}
		List<Item> items = itemService.getAllInStock("FINALIZED");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		
	}

}
