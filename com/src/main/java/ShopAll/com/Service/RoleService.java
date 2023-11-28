package ShopAll.com.Service;

import ShopAll.com.Entity.Role;
import ShopAll.com.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
