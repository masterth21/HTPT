package vn.fs.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.entity.Notification;
import vn.fs.repository.NotificationDAO;
import vn.fs.service.NotificationBusiness;

@RestController
@CrossOrigin("*")
@RequestMapping("api/notification")
public class NotificationService {

	@Autowired
	NotificationDAO notificationRepository;

	@Autowired
	NotificationBusiness notificationService;
	
	@GetMapping
	public ResponseEntity<List<Notification>> getAll(@RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
//		return ResponseEntity.ok(notificationRepository.findByOrderByIdDesc());
		List<Notification> notifications = notificationService.getAllNotifications(offset, limit);
	    return ResponseEntity.ok(notifications);
	}

	@PostMapping
	public ResponseEntity<Notification> post(@RequestBody Notification notification) {
//		if (notificationRepository.existsById(notification.getId())) {
//			return ResponseEntity.badRequest().build();
//		}
//		notification.setTime(new Date());
//		notification.setStatus(false);
//		return ResponseEntity.ok(notificationRepository.save(notification));
		Notification createdNotification = notificationService.createNotification(notification);
	    if (createdNotification != null) {
	        return ResponseEntity.ok(createdNotification);
	    }
	    return ResponseEntity.badRequest().build();
	}

	@GetMapping("/readed/{id}")
	public ResponseEntity<Notification> put(@PathVariable("id") Long id) {
//		if (!notificationRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		Notification no = notificationRepository.getById(id);
//		no.setStatus(true);
//		return ResponseEntity.ok(notificationRepository.save(no));
		
		Notification updatedNotification = notificationService.markAsRead(id);
	    if (updatedNotification != null) {
	        return ResponseEntity.ok(updatedNotification);
	    }
	    return ResponseEntity.notFound().build();
	}

	@GetMapping("/read-all")
	public ResponseEntity<Void> readAll() {
		notificationRepository.readAll();
		return ResponseEntity.ok().build();
//		notificationService.markAllAsRead();
//	    return ResponseEntity.ok().build();
	}

}
