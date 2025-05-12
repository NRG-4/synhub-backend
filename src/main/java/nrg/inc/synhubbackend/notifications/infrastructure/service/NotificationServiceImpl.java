package nrg.inc.synhubbackend.notifications.infrastructure.service;


import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import nrg.inc.synhubbackend.notifications.domain.model.User;
import nrg.inc.synhubbackend.notifications.domain.model.UserPreferences;
import nrg.inc.synhubbackend.notifications.domain.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotification(Notification notification, User user) {
        // Validar que el tipo de notificación esté permitido
        if (!validateType(notification)) {
            throw new IllegalArgumentException("Tipo de notificación no permitido");
        }

        // Verificar preferencias del usuario
        UserPreferences preferences = user.getPreferences();
        if (preferences.isNotificationTypeEnabled(notification.getType())) {
            notification.send();
        }
    }

    @Override
    public boolean validateType(Notification notification) {
        // Todos los tipos son válidos en esta implementación básica
        // Se podría agregar lógica de negocio más compleja aquí
        return notification.getType() != null;
    }
}