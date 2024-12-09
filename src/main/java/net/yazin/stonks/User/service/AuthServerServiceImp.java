package net.yazin.stonks.User.service;

import net.yazin.stonks.User.model.entity.User;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServerServiceImp implements AuthServerService{

    public static final String USER_ID_IN_DB = "userIdDB";
    @Value("${stonks.keycloak.address}")
    private String serverUrl;
    @Value("${stonks.keycloak.realm_name}")
    private String realm_name;
    @Value("${stonks.keycloak.admin_name}")
    private String admin_name; //master realm admin fails here
    @Value("${stonks.keycloak.admin_pass}")
    private String admin_pass;
    @Value("${stonks.keycloak.admin_client}")
    private String admin_client_id;
    @Value("${stonks.keycloak.backend_client}")
    private String backend_client_id;
    private Keycloak keycloak;


    @Override
    public String addUserToAuthServer(User user,String password) {
        
        keycloak=Keycloak.getInstance(serverUrl,realm_name,admin_name,admin_pass,admin_client_id);

        UserRepresentation u=new UserRepresentation();
        u.setEnabled(true);
        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());

        RealmResource realm=keycloak.realm(realm_name);
        UsersResource usersResource=realm.users();

        Response response=usersResource.create(u);

        if(response.getStatus()>299){
            throw new RuntimeException("Error : "+response.getStatus()+" "+response.getStatusInfo());
        }


        String userIdOnServer = CreatedResponseUtil.getCreatedId(response);

        UserResource userResource=usersResource.get(userIdOnServer);
        CredentialRepresentation cr = new CredentialRepresentation();


        cr.setTemporary(false);
        cr.setType(CredentialRepresentation.PASSWORD);
        cr.setValue(password);
        
        userResource.resetPassword(cr);

        adjustRoles(userIdOnServer,user.getUserRole().onServer);


        return userIdOnServer;
    }

    public void adjustRoles(String userID,String crole){
        System.out.println(crole);
        keycloak=Keycloak.getInstance(serverUrl,realm_name,admin_name,admin_pass,admin_client_id);

        RealmResource realm=keycloak.realm(realm_name);
        UsersResource usersResource=realm.users();
        UserResource userResource=usersResource.get(userID);

        userResource.roles().realmLevel().remove(userResource.roles().realmLevel().listAll());

        List<RoleRepresentation> reps=new ArrayList<>();

        RoleRepresentation r3=realm.roles().get(crole).toRepresentation();
        reps.add(r3);


        userResource.roles().realmLevel().add(reps);
        reps.clear();

        ClientRepresentation c=realm.clients().findByClientId(backend_client_id).get(0);

        userResource.roles().clientLevel(c.getId()).remove(userResource.roles().clientLevel(c.getId()).listAll());

        RoleRepresentation r4=realm.clients().get(c.getId()).roles().get("app_"+crole).toRepresentation();
        reps.add(r4);

        userResource.roles().clientLevel(c.getId()).add(reps);
    }

    @Override
    public void deactivateUser(String userIdOnAuthServer) {
        keycloak = Keycloak.getInstance(serverUrl, realm_name, admin_name, admin_pass, admin_client_id);

        RealmResource realm = keycloak.realm(realm_name);
        UsersResource usersResource = realm.users();
        UserResource userResource = usersResource.get(userIdOnAuthServer);
        UserRepresentation ur= userResource.toRepresentation();

        ur.setEnabled(false);

        userResource.update(ur);
        userResource.logout();
    }

    @Override
    public void reactivateUser(String userIdOnAuthServer) {
        keycloak = Keycloak.getInstance(serverUrl, realm_name, admin_name, admin_pass, admin_client_id);

        RealmResource realm = keycloak.realm(realm_name);
        UsersResource usersResource = realm.users();
        UserResource userResource = usersResource.get(userIdOnAuthServer);
        UserRepresentation ur= userResource.toRepresentation();

        ur.setEnabled(true);

        userResource.update(ur);
    }

    @Override
    public void logoutUser(String userIdOnAuthServer) {
        keycloak = Keycloak.getInstance(serverUrl, realm_name, admin_name, admin_pass, admin_client_id);

        RealmResource realm = keycloak.realm(realm_name);
        UsersResource usersResource = realm.users();
        UserResource userResource = usersResource.get(userIdOnAuthServer);
        UserRepresentation ur= userResource.toRepresentation();

        userResource.logout();
        userResource.update(ur);

    }

}