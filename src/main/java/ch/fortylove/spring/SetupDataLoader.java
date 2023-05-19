package ch.fortylove.spring;

import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.repository.PrivilegeRepository;
import ch.fortylove.persistence.repository.RoleRepository;
import ch.fortylove.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(@Nonnull final UserRepository userRepository,
                           @Nonnull final RoleRepository roleRepository,
                           @Nonnull final PrivilegeRepository privilegeRepository,
                           @Nonnull final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@Nonnull final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> staffPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role staffRole = createRoleIfNotFound("ROLE_STAFF", staffPrivileges);
        final Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

        // create template users
        createUserIfNotFound("admin", "admin", "admin", "password", new ArrayList<>(Arrays.asList(adminRole)));
        createUserIfNotFound("staff", "staff", "staff", "password", new ArrayList<>(Arrays.asList(staffRole)));

        createUserIfNotFound("marco", "Marco", "Solombrino", "password", new ArrayList<>(Arrays.asList(userRole)));
        createUserIfNotFound("jonas", "Jonas", "Cahenzli", "password", new ArrayList<>(Arrays.asList(userRole)));
        createUserIfNotFound("daniel", "Daniel", "Tobler", "password", new ArrayList<>(Arrays.asList(userRole)));

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(@Nonnull final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(@Nonnull final String name,
                              @Nonnull final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    User createUserIfNotFound(@Nonnull final String email,
                              @Nonnull final String firstName,
                              @Nonnull final String lastName,
                              @Nonnull final String password,
                              @Nonnull final Collection<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }
}