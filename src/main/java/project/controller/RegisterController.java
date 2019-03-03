package project.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.misc.BASE64Decoder;

import project.domain.GameUser;
import project.domain.GtUser;
import project.domain.Token;
import project.domain.dto.GameUserDTO;
import project.service.GameUserService;
import project.service.GtUserService;
import project.service.TokenService;

@RequestMapping("/register")
@Controller
public class RegisterController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private GtUserService gtUserService;
	
	@Autowired
	private GameUserService gameUserService;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	
	@RequestMapping(value = "/token",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON,
			produces = MediaType.TEXT_PLAIN)
	public ResponseEntity<String> registerCustomer(@Context HttpServletRequest request, @RequestBody GameUserDTO dto) {
		
		String hash = UUID.randomUUID().toString();
		
		if (gtUserService.getGtUserByEmail(dto.getUemail()) != null) {
			return new ResponseEntity<String>("Provided e-mail is already in use.", HttpStatus.OK);
		}
		
		if (gtUserService.getGtUserByName(dto.getUname()) != null) {
			return new ResponseEntity<String>("Provided username is already in use.", HttpStatus.OK);
		}
		
		if(dto.getUavat() == "" || dto.getUavat()==null)
		{
			dto.setUavat("data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAAgAAAAIABAMAAAAGVsnJAAAAIVBMVEUAAAB+fX1+fX1+fX1+fX1+fX1+fX1+fX1+fX1+fX1+fX1I2PRsAAAACnRSTlMAF/ClME+Kb9vEsIrXWQAACWpJREFUeNrs3T1rVEEUBuBzs1+JlbGImkpREW6lVrqVhBBCKhESIZWCIqTSgEZSKSrCVordVrrxY/P+SouEJG7uzH7k3rBz3vf5CYe9Z87MOTNrIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiMo755fWdty931pfnjU/25EGOI73vby4akWzjPk75+IIlBtlGF4X2OUKw0kXQ/nPzrnEPUTcemWsrOYboef4RZO8wgi9uM0Gri5HsvzKXWh2MqO8yApdzjKz32txZyDGG3jNzZiEHmCPQyjGm3lNzpNHB2PqOSqKsjQns+akHtjGR2+bEKib02VyoYWJ3zYF6BxPrP7T0HSYA2jRQAwDij+DgAyD+CLYBgPgjqOHM7ljKujizfUvYVZTgmyUr66AE/XT3BKsoxSdLVD1HKXqpLoWPUZJblqQGSpPm2cgSSnPTEnSQAYizwBWU6IMl57gGIK0F5lCqr5aaLk4g3BHU8B++TeEuSvbXklJH6dJaCQ/XQN6VsI3S7VlCWqhASmMjSzhCuSE4UQVyVoPHRQBpKbCJSvy2VHRQib4looUjnOvAJVTkuqWhjRMIa6EGBrAdjs6iMu8tBVuozB9LQIYKpVAMNlGhFOZnBxdBuoVwMAWwJYEsR4V6058EmhjAlgROpQC2JLCLAkyn4zkq1bMp10IBpi3xHIoQdUnXULFfNt22UISoFOqgGMvBYB1BHE3SGkJIugMzqNw1m2abCCFpj7QRQnI0jHNgU6yBCIaz8SbCKI4E5hBCshtYxDn4adNrE0Ec6+AWwii2Qx2EMWyHMkT57481EENQCDQRQ1AI1BBDsCGeRZz7MYkLOBc/bFqtIc79wfAmYghKwV1E+e8PthHl/0yoizj3V+hyRLnvEGeIYNgM0Aegjjj33TH6ADQQ5X8/rACgGE0AWhjC+6AUfQCaiPJ/JqYAoJgCwBIA+iSoAKAYTQDoK0EFAMVoAkB/HkAfAEMUwahkB1H+Z2ToGyP0rbEtxBDMydG3x+kHJBYxhPdh4RlE+b81NIc49/Py9IOS9KOy9MPS9OPysVqYoRIeNijofkwwVgpSFILDxsXdD4vr4qSuzlqOIPdzoro+rwcU9ISGHlEJLgMsi0BoGaBZBPSUlh5To39Or4FTqHKgntQsyIJUOTDQHWLoCgVrQaY6MHQ0znEkrsfV9by+/mAh+L4+0ev6+pOVgSTAlwKKrg24vyjwj70zeXUiCMJ4jU4UPAUjbifFfU4qLpiTG6i3EHHBkwvicnI/eFJRwdxcEMlJJwpaf6XPjDGTWXq6J/Owa7763QR5PNvpqq++qu6umpds/4SkyRMA8gKKEiFcEtQHF/XJTX10VZ/dnecByBygT2/r4+v6/H76BF37z8pVTAwiTAeWSgFMETAPg7ghcNYlBeqJFqlBVBU4YyOeF7ZIGHFjxMJyYMJpbozdJJEwwv4AiE5jfwBEYYT9ARCd50Z4TVIJRqgaYMY2boD3JJg+YhWQZj2YE5ZnyEuyh2QTjpaMgGJT4IweL8UhEs8jXoJ9JJLgRvoPY67Nr7QE2CxHDzyKTzaSCeIHC8JazOdwNRO7L3BNPmXyyRsSwYXcWP/9BmbCOsmKCKCXt/HDca0AcJJSPJeSFNZHBeMsnVENBTAoGLuJvdeF/4TPJLss7gEwTV+KMLpf0srZ7LgC8Q1Ks1bKsOjVTA6f03NWgIVawvNU0DOUMZuj2v//NBSijjuRaaxvy8g6/j00DR7G3p6cC/plQjahM7bMfwMiMojpia+aeFhVy4eH2YJdJ7M/V4hHsM5itvVixBXER3M/V8jMbDA2V3MJnYqPYNfA6uf6uAmGdvV8cHFkiH5Hu/nSUohRttbQ1DAugfmfT+eFDI6HIwdPK7j8gXMcuN11cNR++SaJhwZNX8Smyyei1F/6ePtUSWklxC1eZ6xqiwnOXrry7NaxO08vnS2LaeFYSr+gb/I1aofs4L6UjtE2s7VbcwWCR1J6hlWDAHtrrUBwU0zPZMjc/AoEN8V0zdYxN78CwU05p8j6XM3kJDkR9uV0zteyDZMBOdDpy5mgtm19xUfImjMRF+BpUbSNbXlr+esGdyWNz7gMQv16SBZsGYsaoDrPLhyvjIXhY1kjdKGr329egvBxJGyI8rR7y+t4l0oIHo+kjdHWmob9eexJwRoE526N5M3RnuZ6xB+fLvzi4ZUTkcRJ6qXGofe/+7hiBqxYAie+vJI6Sr2VPeAluePVMLTgYWovPoD/+AkEY/YC54rA07OR8k5V9tkTJuSG79cFSblg6Bp7ww9ywts7EmTdrrCRPWInWdE+EeQmhtqZA50zof8XZ4q4bLPDnjEgCzwwAjLIPWHvVQh0u2zQz1typN2z85y9w0INemKFZRB5zYQnTojjQ4xtLITdimKfzoT/RagU8KoOcquIPL87W8ge8HQHGPYAxg4w7QGAHFC1B9pcCFbuAZQdULoHUHZA6R6A2QHmPSDqgXWf6wHPzEAna9D3d5REvMTkoRdk4Qu1syPo4Au12Q218UYRCiHTYTIQGVgqBnGSYHkibOdQgO2oAFASNCdCb9/PSZDxGo/HlWBZRYgWAnJBAC0EZIMAXAjIBQG0EJANAnghIBME8ELAagaB7SyCb5QCqxBY7XLAazdsTkwLAHkBxZ4AiCFeao7j2IGFxiCeDFpFKRSwGLo0p5VnhP7PGaI1LIYdNKfV47E2D5S2fjasiF+UgBoD01EQUAcuaEFEHbioBcHssLwtBlcLZytimL64oUsOMBxmGhcD8wOzviCkEE6JYUQzIGUJ4CaBJA0AJ4F0GsBqCmXbQ6CVwGI10Mr7EuxvVADrimX6Y7hZcJYHAS3xjDWO1hbMNAiBs+A0DyJnwb95ELUW/FsPohqCCS+wZQDzN2wZMBUCuMXwv4IYsS22Ou0xFgitAKyDpkoIWQcxPyBoHcR8EFsHNauENrBAvtIKiJ3hGd+xhWAiBQHnoxYnpWANsT9MsJXwVAvjOoKJK4g5ITenS6DTITMG2KUA8wMCnBNPc10XQBdAY4BmAYD7w8qIu1oLqB8AnQaua2OkQbaxON7TlJY9Lfj/HiFcLywTxg+oYXqiViA+RI3TufeKhbD/84AURVEURVEURVEURVEURVEURVEURVEURVEURVEURVEURVEURVEURfndHhyQAAAAAAj6/7ofoQIAAAAAAAAAAPwEGcG4SMHdcSkAAAAASUVORK5CYII=");
		}
		
		Token tmpToken = tokenService.getTokenByEmail(dto.getUemail());
		Token token = new Token(hash, dto.getUemail(), dto.getPword(), dto.getUname(), dto.getUavat(), dto.getUbio(), dto.getUdob(), dto.getRname(), dto.getUcountry());
		if (tmpToken != null) {
			token.setTokid(tmpToken.getTokid());
		}
		token.setTokexp(token.calculateExpiryDate());
		tokenService.addToken(token);
		System.out.println(hash);
		
		String appLocation = getURL(request);
		String url = appLocation + "/register/confirm?token=" + hash;
		
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getUemail());
        message.setSubject("Registration confirmation");
        message.setText("Please follow the link below to confirm your registration. \n\n" + url);
        javaMailSender.send(message);
        
		return new ResponseEntity<String>("A confirmation link has been sent to provided e-mail address.", HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/confirm",
			method = RequestMethod.GET)
	public String confirmRegistration(@Context HttpServletRequest request, @RequestParam("token") String token) {
		
		Token tok = tokenService.getTokenByHash(token);
		
		if (tok == null) {
			return "redirect:/register.html";
		}
		
		Calendar cal = Calendar.getInstance();
		if ((tok.getTokexp().getTime() - cal.getTime().getTime()) <= 0) {
			return "redirect:/register.html";
		}
		
		GtUser gt = new GtUser();
		gt.setUemail(tok.getTokem());
		gt.setUname(tok.getTokun());
		gt.setPword(tok.getTokpw());
		BigDecimal type = new BigDecimal(1);
		gt.setUserut(type);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String tekst = year + "-" + month + "-" + day;
		Date pokusaj = Date.valueOf(tekst);
		gt.setUdate(pokusaj);
		
		
		gtUserService.save(gt);
		
		
		if(tok.getTokavat()!=null && tok.getTokavat()!=""){
			final String dir = System.getProperty("user.dir");
			String base64Image = tok.getTokavat().split(",")[1];
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
			String sequence = gt.getUname();
			String imagePath = "../../data/users/" + sequence + ".jpg";
			gt.setUavat(imagePath);
			gtUserService.save(gt);
			String savePath = dir + "\\src\\main\\resources\\static\\data\\users\\"+sequence+".jpg";
			String savePath2 = dir + "\\target\\classes\\static\\data\\users\\"+sequence+".jpg";
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

		} else {
			gt.setUavat(null);
		}
		
		
		GameUser game = new GameUser();
		game.setUname(tok.getTokun());
		if(tok.getTokrn() != "")
			game.setRname(tok.getTokrn());
		game.setUcountry(tok.getTokcntry());
		if(tok.getTokbio()!="")
			game.setUbio(tok.getTokbio());
		Date datum = Date.valueOf(tok.getTokdob());
		game.setUdob(datum);
		gameUserService.save(game);

		
		return "redirect:/login.html";
		
		
	}
	
	
	public static String getURL(HttpServletRequest req) {

	    String scheme = req.getScheme();
	    String serverName = req.getServerName();
	    int serverPort = req.getServerPort();        
	    String contextPath = req.getContextPath();    

	    StringBuilder url = new StringBuilder();
	    url.append(scheme).append("://").append(serverName);

	    if (serverPort != 80 && serverPort != 443) {
	        url.append(":").append(serverPort);
	    }

	    url.append(contextPath);

	    return url.toString();
	}
	
	

	
}
