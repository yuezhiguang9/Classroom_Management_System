package demo.campus_management_system.controller;

import demo.campus_management_system.dao.dao_interface.SuperAdminMapper;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.service.service_interface.UserService;
import demo.campus_management_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class test {

    @Autowired
    private UserService userService;

    @Autowired
    private SuperAdminMapper superAdminMapper;


    @PostMapping("test")
    public String getToken(String user_account, String user_password) {

        return JwtUtil.createToken(user_account, user_password);
    }

    @GetMapping("getId")
    public String getID(String token) {
        return JwtUtil.getUserAccountToken(token);
    }

    @GetMapping("getPassword")
    public String getPassword(@RequestHeader(value = "Authorization") String token) {
        return JwtUtil.getUserPasswordToken(token);
    }

    @GetMapping("getUsers")
    public List<Users> getUsers() {
        List<Users> result = userService.list();
        return result;
    }


    //测试RequestBody
//    @PostMapping("admin/updateUsers")
//    public ResultDTO<Boolean> updateUsers(@RequestHeader(value = "Authorization") String token, @RequestBody UpdateUsersDTO updateUsersDTO) {
//        try {
//
//            //打印测试
//            System.out.println(JwtUtil.getUserAccountToken(token) + "----" + JwtUtil.getUserPasswordToken(token));
//
//            System.out.println("usersDTO=" + "\n" + updateUsersDTO.getTotal());
//
//            String account = JwtUtil.getUserAccountToken(token);
//            String password = JwtUtil.getUserPasswordToken(token);
//
//            System.out.println("account=" + account + "---" + "password=" + password);
//
//            //先判断token正确性
//            if (account == null || "error".equals(account)) {
//                return ResultDTO.fail(401, "未登录或Token失效");
//            }
//
//
//            //超级管理员才能编辑用户，所以先判断是不是超级管理员
//            //为了快速测试直接跳过service层
//
//            Boolean isSuperAdmin = superAdminMapper.selectSuperAdmin(account, password);
//            if (isSuperAdmin == null || !isSuperAdmin) {
//                return ResultDTO.fail(401, "没有权限");
//            } else {
//                switch (updateUsersDTO.getUser_type()) {
//                    case "user":
//                        if (superAdminMapper.editUsers(updateUsersDTO)) {
//                            return ResultDTO.success(true);
//                        } else {
//                            return ResultDTO.fail(404, "修改失败");
//                        }
//                    case "teach_sec":
//                        if (superAdminMapper.editTeach(updateUsersDTO)) {
//                            return ResultDTO.success(true);
//                        } else {
//                            return ResultDTO.fail(404, "修改失败");
//                        }
//                    case "class_mgr":
//                        if (superAdminMapper.editClassMgr(updateUsersDTO)) {
//                            return ResultDTO.success(true);
//                        } else {
//                            return ResultDTO.fail(404, "修改失败");
//                        }
//                    default:
//                        return ResultDTO.fail(404, "没有选择用户类型");
//                }
//
//
//            }
//        } catch (Exception e) {
//            System.out.println("e=" + e);
//            return ResultDTO.fail(500, e.toString());
//        }
//    }
}
