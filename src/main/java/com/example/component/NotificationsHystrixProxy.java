package com.example.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.data.NotificationData;
import com.example.model.Notifications;
import com.example.model.NotificationsResponse;

import rx.Observable;

@Component
public class NotificationsHystrixProxy {

	@Autowired
	private NotificationData notificationData;
	
	public Observable<NotificationsResponse> getNotifications() {
		
		return Observable.just(notificationData.create());
		
	}

	public Observable<NotificationsResponse> findById(Long id) {
		
		return getNotifications().filter(n -> n.getId() == id);
	
	}

	public Observable<Notifications> findByCode(Long id, String code) {

		return findById(id).flatMap(nr -> Observable.from(nr.getNotifications()))
				.filter(n -> n.getCode().toLowerCase().contains(code.toLowerCase()));

	}

	public NotificationsResponse defaultNotifications() {
	
		return new NotificationsResponse();
	
	}

	public NotificationsResponse defaultById(Long id) {
		
		return new NotificationsResponse();
	
	}

	public Notifications defaultByCode(Long id, String code) {
	
		return new Notifications();
	
	}
	
}
