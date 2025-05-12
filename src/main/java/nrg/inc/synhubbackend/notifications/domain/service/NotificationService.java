package nrg.inc.synhubbackend.notifications.domain.service;


import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import nrg.inc.synhubbackend.notifications.domain.model.User;

public interface NotificationService {
    void sendNotification(Notification notification, User user);
    boolean validateType(Notification notification);
}