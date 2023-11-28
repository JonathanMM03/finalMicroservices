package ShopAll.com.config;


import ShopAll.com.Entity.Role;
import ShopAll.com.Entity.User;
import ShopAll.com.Service.RoleService;
import ShopAll.com.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        // Crea usuarios de prueba
        if (userService.userNotExists()){
            Role role1 = new Role("ROLE_USER");
            roleService.save(role1);
            Role role2 = new Role("ROLE_ADMIN");
            roleService.save(role2);

            User user1 = new User("usuario1", "password1");
            user1.setRoles(Collections.singleton(role1));
            userService.save(user1);

            User admin = new User("admin", "admin");
            admin.setRoles(Collections.singleton(role2));
            userService.save(admin);
        }
    }
}

