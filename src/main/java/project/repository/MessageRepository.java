package project.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


import project.domain.Message;
import project.domain.MessagePK;

public interface MessageRepository extends Repository<Message, MessagePK> {
	
	public List<Message> findAll();
	
	public Message save(Message m);
	
	@Query("select m from Message m where UPPER(m.msgcont) LIKE UPPER( ?1 )")
	public List<Message> getAllByContent(String content);
	
	@Query("select m from Message m where m.id.msgsent = ?1 AND m.id.msgdate = ?2 AND m.id.msgtime = ?3")
	public Message getMessageByKey(String sender, Date date, Timestamp time);
	
	@Query("select m from Message m where m.id.msgsent = ?1")
	public Message getMessageBySender(String sender);
	
	@Query("select m from Message m where m.id.msgdate = ?1")
	public Message getMessageByDate(Date date);
	
	@Modifying
	@Query("delete from Message m where m.id.msgsent = ?1 AND m.id.msgdate = ?2 AND m.id.msgtime = ?3")
	public void removeMessageByKey(String sender, Date date, Timestamp time);
	
	@Modifying
	@Query("update Message m set m.msgcont = ?4 where m.id.msgsent = ?1 AND m.id.msgdate = ?2 AND m.id.msgtime = ?3 ")
	public void updateMessageContent(String sender, Date date, Timestamp time, String content);
}
