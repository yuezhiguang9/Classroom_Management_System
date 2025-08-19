package demo.campus_management_system.service.impl;

import demo.campus_management_system.dao.dao_interface.AuthMapper;
import demo.campus_management_system.entity.*;
import demo.campus_management_system.entity.DTO.LoginRequestDTO;
import demo.campus_management_system.entity.DTO.LoginResponseDTO;
import demo.campus_management_system.service.service_interface.AuthService;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 认证服务实现类
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public ResultDTO<LoginResponseDTO> login(LoginRequestDTO loginRequest) {
        try {
            String userType = loginRequest.getUser_type();
            String account = loginRequest.getAccount();
            String password = loginRequest.getPassword();

            // 根据用户类型查询对应表
            LoginResponseDTO response = new LoginResponseDTO();

            switch (userType) {
                case "user":
                    Users user = authMapper.selectUserByAccount(account);
                    if (user == null || !user.getUserPassword().equals(password)) {
                        return ResultDTO.fail(401, "账号或密码错误");
                    }
                    response.setAccount(user.getUserAccount());
                    response.setName(user.getUserName());
                    response.setUser_type("user");
                    response.setCollege(user.getCollegeId()); // 需要关联查询学院名称
                    break;

                case "teach_sec":
                    Teach_Secretary teachSec = authMapper.selectTeachSecByAccount(account);
                    if (teachSec == null || !teachSec.getSecPassword().equals(password)) {
                        return ResultDTO.fail(401, "账号或密码错误");
                    }
                    response.setAccount(teachSec.getSecAccount());
                    response.setName(teachSec.getSecName());
                    response.setUser_type("teach_sec");
                    response.setCollege(teachSec.getCollegeId()); // 需要关联查询学院名称
                    break;

                case "class_mgr":
                    Classroom_manager manager = authMapper.selectClassroomManagerByAccount(account);
                    if (manager == null || !manager.getMgrPassword().equals(password)) {
                        return ResultDTO.fail(401, "账号或密码错误");
                    }
                    response.setAccount(manager.getMgrAccount());
                    response.setName("教室管理员"); // 教室管理员表没有姓名字段
                    response.setUser_type("class_mgr");
                    response.setCollege(""); // 教室管理员不关联学院
                    break;

                case "super_admin":
                    Super_admin admin = authMapper.selectSuperAdminByAccount(account);
                    if (admin == null || !admin.getPassword().equals(password)) {
                        return ResultDTO.fail(401, "账号或密码错误");
                    }
                    response.setAccount(admin.getAccount());
                    response.setName("超级管理员");
                    response.setUser_type("super_admin");
                    response.setCollege(""); // 超级管理员不关联学院
                    break;

                default:
                    return ResultDTO.fail(400, "无效的用户类型");
            }

            // 生成JWT Token
            String token = JwtUtil.createToken(account, password);
            response.setToken(token);


            // 记录登录日志(仅普通用户)
            if (true) {
                String logUuid = UUID.randomUUID().toString().replace("-", "");
                String loginId = "log" + logUuid.substring(0, 10); // 简化登录ID
                System.out.println(account);
                System.out.println(loginId);
                authMapper.insertLoginLog(account, loginId);
            }

            return ResultDTO.success(response, "登录成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
}