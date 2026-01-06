package com.omnirank.controller;
import com.omnirank.model.PlatformHandle;
import com.omnirank.model.User;
import com.omnirank.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    @GetMapping("/login") public String loginPage() { return "login"; }
    @GetMapping("/register") public String registerPage() { return "register"; }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = userRepository.findByName(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam String name, @RequestParam String password,
                                 @RequestParam(required=false) String cfHandle,
                                 @RequestParam(required=false) String lcHandle,
                                 @RequestParam(required=false) String acHandle,
                                 @RequestParam(required=false) String ccHandle,
                                 @RequestParam(required=false) String csesHandle) {
        if (userRepository.findByName(name) != null) return "redirect:/register?error=exists";

        User user = new User();
        user.setName(name);
        user.setPassword(password);

        if (cfHandle != null && !cfHandle.isEmpty()) addHandle(user, "Codeforces", cfHandle);
        if (lcHandle != null && !lcHandle.isEmpty()) addHandle(user, "LeetCode", lcHandle);
        if (acHandle != null && !acHandle.isEmpty()) addHandle(user, "AtCoder", acHandle);
        if (ccHandle != null && !ccHandle.isEmpty()) addHandle(user, "CodeChef", ccHandle);
        if (csesHandle != null && !csesHandle.isEmpty()) addHandle(user, "CSES", csesHandle);

        userRepository.save(user);
        return "redirect:/login";
    }

    private void addHandle(User user, String platform, String handle) {
        PlatformHandle ph = new PlatformHandle();
        ph.setUser(user);
        ph.setPlatform(platform);
        ph.setHandle(handle);
        user.getHandles().add(ph);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}