package com.app.zware.Controllers;

import com.app.zware.RequestEntities.LoginRequest;
import com.app.zware.Util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request
    ) {
        if ("quang@gmail.com".equals(request.getEmail()) && "123456".equals(request.getPassword())) {
            return JwtUtil.generateToken(request.getEmail());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from auth/hello" ;
    }
}
