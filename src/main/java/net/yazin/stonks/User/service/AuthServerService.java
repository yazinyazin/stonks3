package net.yazin.stonks.User.service;

import net.yazin.stonks.User.model.entity.User;

public interface AuthServerService {
    String addUserToAuthServer(User user, String password);

    void deactivateUser(String userIdOnAuthServer);

    void reactivateUser(String userIdOnAuthServer);

    void logoutUser(String userIdOnAuthServer);
}
