package project.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import project.domain.dto.SendMessageDTO;

@Component
public class ChatMessenger {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	public void sendRequestTo(SendMessageDTO dto) {
		String topic = "chats?betweenID=" + dto.getReceiver();
		this.template.convertAndSend("/topic/" + topic, dto);
	}
	
	public void sendUpdateTo(SendMessageDTO dto) {
		String topic = "chats?betweenID=" + dto.getReceiver();
		this.template.convertAndSend("/topic/" + topic, dto);
	}

}
