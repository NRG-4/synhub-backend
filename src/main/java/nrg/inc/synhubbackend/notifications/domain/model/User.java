package nrg.inc.synhubbackend.notifications.domain.model;


public class User {
    private Long id;
    private String email;
    private UserPreferences preferences;

    public User() {}

    public User(Long id, String email, UserPreferences preferences) {
        this.id = id;
        this.email = email;
        this.preferences = preferences;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferences preferences) {
        this.preferences = preferences;
    }

    // Métodos de dominio
    public void updatePreferences(UserPreferences newPreferences) {
        this.preferences = newPreferences;
    }
}