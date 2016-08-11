package com.example.model;

import java.util.ArrayList;

public class NotificationsResponse {
	private long id;
	private ArrayList<Notifications> notifications;

	public NotificationsResponse() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArrayList<Notifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<Notifications> notifications) {
		this.notifications = notifications;
	}
}