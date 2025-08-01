package demo.campus_management_system.controller;

import demo.campus_management_system.dao.dao_interface.UsersMapper;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.service.srevice_interface.UserService;
import demo.campus_management_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class test {

    @Autowired
    private UserService userService;

    @PostMapping("test")
    public String getToken(String user_account, String user_password) {

        return JwtUtil.createToken(user_account, user_password);
    }

    @GetMapping("getId")
    public String getID(String token) {
        return JwtUtil.getUserAccountToken(token);
    }

    @GetMapping("getUsers")
    public List<Users> getUsers() {
        List<Users> result = userService.list();
        return result;
    }


}
