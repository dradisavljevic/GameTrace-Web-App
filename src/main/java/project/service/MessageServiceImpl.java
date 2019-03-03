package project.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Achievement;
import project.domain.GtUser;
import project.domain.Message;
import project.domain.MessagePackage;
import project.domain.dto.MessageDTO;
import project.repository.GtUserRepository;
import project.repository.ItemRepository;
import project.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private GtUserRepository gtUserRepository;
	
	@Autowired
    private JpaContext jpaContext;

	@Override
	public List<Message> findAll() {
		return messageRepository.findAll();
	}

	@Override
	public Message save(Message m) {
		Assert.notNull(m);
		return messageRepository.save(m);
	}

	@Override
	public List<Message> getAllByContent(String content) {
		Assert.notNull(content);
		return messageRepository.getAllByContent(content);
	}

	@Override
	public Message getMessageByKey(String sender, Date date, Timestamp time) {
		Assert.notNull(sender);
		Assert.notNull(date);
		Assert.notNull(time);
		return messageRepository.getMessageByKey(sender, date, time);
	}

	@Override
	public Message getMessageBySender(String sender) {
		Assert.notNull(sender);
		return messageRepository.getMessageBySender(sender);
	}

	@Override
	public Message getMessageByDate(Date date) {
		Assert.notNull(date);
		return messageRepository.getMessageByDate(date);
	}

	@Override
	public void removeMessageByKey(String sender, Date date, Timestamp time) {
		Assert.notNull(sender);
		Assert.notNull(date);
		Assert.notNull(time);
		messageRepository.removeMessageByKey(sender, date, time);
	}

	@Override
	public void updateMessageContent(String sender, Date date, Timestamp time, String content) {
		Assert.notNull(sender);
		Assert.notNull(date);
		Assert.notNull(time);
		Assert.notNull(content);
		messageRepository.updateMessageContent(sender, date, time, content);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageDTO> getMessages(String uname1, String uname2) {
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(Message.class);;
		
		List<MessagePackage> messages = (List<MessagePackage>) entityM
			    .createNamedQuery("get_messages_between")
			    .setParameter(1, uname1)
			    .setParameter(2, uname2)
			    .getResultList();
		GtUser sender = gtUserRepository.getGtUserByName(uname1);
		GtUser receiver = gtUserRepository.getGtUserByName(uname2);
		List<MessageDTO> dtos = new ArrayList<MessageDTO>();
		for (MessagePackage msg : messages){
			MessageDTO dto = new MessageDTO();
			dto.setMsgcont(msg.getMsgcont());
			dto.setMsgdate(msg.getMsgdate());
			dto.setMsgrec(msg.getMsgrec());
			String time = " on ";
			String rad[] = msg.getMsgtime().toLocalDateTime().toString().split("T");
			String radDatum[] = rad[0].split("-");
			time += radDatum[2];
			time+="/"+radDatum[1];
			time+="/"+radDatum[0];
			time+=" at ";
			time+=rad[1];
			dto.setMsgtime(time);
			dto.setMsgsent(msg.getMsgsent());
			if(receiver.getUname().equals(msg.getMsgrec())){
				dto.setReceiver(receiver);
				dto.setSender(sender);
			} else {
				dto.setReceiver(sender);
				dto.setSender(receiver);
			}
			dtos.add(dto);
		}
	
		return dtos;
	}

}
