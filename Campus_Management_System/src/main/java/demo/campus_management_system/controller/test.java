package demo.campus_management_system.controller;

import demo.campus_management_system.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @PostMapping("test")
    public String getToken(String user_account, String user_password) {
        
        return JwtUtil.createToken(user_account, user_password);
    }

    @GetMapping("getId")
    public String getID(String token) {
        return JwtUtil.getUserAccountToken(token);
    }

}
