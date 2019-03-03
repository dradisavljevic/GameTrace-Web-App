package project.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.Message;
import project.domain.MessagePackage;
import project.domain.dto.MessageDTO;

public interface MessageService {
	
	List<Message> findAll();
	
	Message save(Message m);
	
	List<Message> getAllByContent(String content);
	
	Message getMessageByKey(String sender, Date date, Timestamp time);
	
	Message getMessageBySender(String sender);
	
	Message getMessageByDate(Date date);
	
	void removeMessageByKey(String sender, Date date, Timestamp time);
	
	void updateMessageContent(String sender, Date date, Timestamp time, String content);
	
	List<MessageDTO> getMessages(String uname1, String uname2);

}
