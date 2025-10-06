package NayaBazzar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import NayaBazzar.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {



}
