package com.mediconnect.dto;

import com.mediconnect.model.HealthWorker;
import com.mediconnect.model.User;

public class AuthResponse {
    private String token;
    private User user;
    private HealthWorker workerProfile;

    public AuthResponse() {}

    public AuthResponse(String token, User user, HealthWorker workerProfile) {
        this.token = token;
        this.user = user;
        this.workerProfile = workerProfile;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public HealthWorker getWorkerProfile() { return workerProfile; }
    public void setWorkerProfile(HealthWorker workerProfile) { this.workerProfile = workerProfile; }
}
