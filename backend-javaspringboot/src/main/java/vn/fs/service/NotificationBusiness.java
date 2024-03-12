package vn.fs.service;
import java.util.List;

import vn.fs.entity.Notification;

public interface NotificationBusiness {
	
	List<Notification> getAllNotifications(int offset, int limit);
	
	Notification createNotification(Notification notification);
	
	Notification markAsRead(Long id);
	
	void markAllAsRead();
	
	
	
}
