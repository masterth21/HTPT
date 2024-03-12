package vn.fs.service.implement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.fs.entity.Notification;
import vn.fs.repository.NotificationDAO;
import vn.fs.service.NotificationBusiness;

@Service
public class NotificationBusinessImpl implements NotificationBusiness {

	@Autowired
	NotificationDAO notificationRepository;
	
	@Override
	public List<Notification> getAllNotifications(int offset , int limit) {
		// TODO Auto-generated method stub
//		return notificationRepository.findByOrderByIdDesc();
		Pageable pageable = PageRequest.of(offset, limit);
        
        // Sử dụng repository để lấy danh sách Category với phân trang
        Page<Notification> notiPage = notificationRepository.findAll(pageable);
        
        // Trả về danh sách Category từ Page
        return notiPage.getContent();
	}

	@Override
	public Notification createNotification(Notification notification) {
		if (notificationRepository.existsById(notification.getId())) {
	        return null; 
	    }
	    notification.setTime(new Date());
	    notification.setStatus(false);
	    return notificationRepository.save(notification);
	}

	@Override
	public Notification markAsRead(Long id) {
		if (!notificationRepository.existsById(id)) {
	        return null;
	    }
	    Notification notification = notificationRepository.getById(id);
	    notification.setStatus(true);
	    return notificationRepository.save(notification);
	}

	@Override
	public void markAllAsRead() {
		notificationRepository.readAll();
		
	}

	
	
}
