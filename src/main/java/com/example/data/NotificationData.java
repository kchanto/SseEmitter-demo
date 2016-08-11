package com.example.data;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.model.Notifications;
import com.example.model.NotificationsResponse;

@Component
public class NotificationData {
	
	@Value("${messages.last}")
	private String lastMessage;

	public NotificationsResponse create(){
		NotificationsResponse notificationsResponse = new NotificationsResponse();
		
		ArrayList<Notifications> notifications = new ArrayList<Notifications>();
		Notifications notification1 = new Notifications();
		notification1.setCode("123");
		notification1.setDescription("Eligible for SCORE! Upgrade");
		notification1.setPriority("1");
		notification1.setUrl("url1");

		Notifications notification2 = new Notifications();
		notification2.setCode("456");
		notification2.setDescription("Eligible for JUMP! Upgrade");
		notification2.setPriority("2");
		notification2.setUrl("url2");

		Notifications notification3 = new Notifications();
		notification3.setCode("456");
		notification3.setDescription(lastMessage);
		notification3.setPriority("3");
		notification3.setUrl("url3");

		notifications.add(notification1);
		notifications.add(notification2);
		notifications.add(notification3);
		notificationsResponse.setId(55554L);
		notificationsResponse.setNotifications(notifications);
		
		return notificationsResponse;
	}
	
}
