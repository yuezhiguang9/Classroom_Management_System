package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.*;
import demo.campus_management_system.service.impl.SuperAdminImpl;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class SuperAdmin {
    @Autowired
    private SuperAdminImpl superAdminImpl;

    /**
     * 用户列表
     */
    @GetMapping("/listUsers")
    public ResultDTO<UserListDTO> listUsers(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String user_type,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String college_id,
            @RequestParam(required = false) String user_name,
            @RequestParam(required = false) String user_id) {

        // JWT认证 - 去掉Bearer前缀
        String actualToken = JwtUtil.extractToken(token);
        if (JwtUtil.getUserAccountToken(actualToken).equals("error")) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }

        UserListQueryDTO queryDTO = new UserListQueryDTO();
        queryDTO.setUser_type(user_type);
        queryDTO.setPage(page);
        queryDTO.setSize(size);
        queryDTO.setCollege_id(college_id);
        queryDTO.setUser_name(user_name);
        queryDTO.setUser_id(user_id);

        return superAdminImpl.listUsers(queryDTO);
    }
}
