package cat.tecnocampus.securityjwt.api;

import cat.tecnocampus.securityjwt.domain.ERole;
import cat.tecnocampus.securityjwt.domain.Role;
import cat.tecnocampus.securityjwt.domain.UserLab;
import cat.tecnocampus.securityjwt.persistence.RoleRepository;
import cat.tecnocampus.securityjwt.persistence.UserLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

@RestController
public class APIController {

    @Autowired
    private UserLabRepository userLabRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/helloAdmin")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @GetMapping("/helloUser")
    public String helloUser() {
        return "Hello User";
    }

    @GetMapping("/helloUserAdmin")
    public String helloUserAdmin() {
        return "Hello User or Admin";
    }

    @GetMapping("/helloMe")
    public String helloMe(Principal principal) {
        return "Hello " + principal.getName();
    }

    // getMapping /moderator returning the url
    @GetMapping("/moderator")
    public String moderator() {
        return "moderator";
    }

    @GetMapping("/moderator/aaa")
    public String moderatorAaa() {
        return "moderator/aaa";
    }

    @GetMapping("/moderator/bbb")
    public String moderatorBbb() {
        return "moderator/bbb";
    }

    @GetMapping("/moderator/ccc")
    public String moderatorCcc() {
        return "moderator/ccc";
    }

    @GetMapping("/moderator/aaa/admin")
    public String moderatorAaaAdmin() {
        return "moderator/aaa/admin";
    }

    @GetMapping("/moderator/bbb/admin")
    public String moderatorBbbAdmin() {
        return "moderator/bbb/admin";
    }

    @GetMapping("/moderator/ccc/admin")
    public String moderatorCccAdmin() {
        return "moderator/ccc/admin";
    }

    // TODO 2 add a PostMapping to create a new user with a single role. The role must be ADMIN or USER or MODERATOR
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserLab user) {
        if (user.getRoles().isEmpty() || user.getRoles().size() > 1) {
            return ResponseEntity.badRequest().body("User must have exactly one role");
        }

        Role role = user.getRoles().iterator().next();
        if (!role.getName().equals(ERole.ADMIN) && !role.getName().equals(ERole.USER) && !role.getName().equals(ERole.MODERATOR)) {
            return ResponseEntity.badRequest().body("Invalid role");
        }

        Role persistedRole = roleRepository.findByName(role.getName())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.getRoles().clear();
        user.getRoles().add(persistedRole);

        userLabRepository.save(user);

        return ResponseEntity.ok("User created successfully");
    }
}
