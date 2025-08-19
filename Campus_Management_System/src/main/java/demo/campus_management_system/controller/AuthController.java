package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.LoginRequestDTO;
import demo.campus_management_system.entity.DTO.LoginResponseDTO;
import demo.campus_management_system.service.service_interface.AuthService;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求数据
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResultDTO<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        // 参数校验
        if (loginRequest.getUser_type() == null || loginRequest.getUser_type().trim().isEmpty()) {
            return ResultDTO.fail(400, "用户类型不能为空");
        }
        if (loginRequest.getAccount() == null || loginRequest.getAccount().trim().isEmpty()) {
            return ResultDTO.fail(400, "账号不能为空");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            return ResultDTO.fail(400, "密码不能为空");
        }

        // 验证用户类型是否有效
        String userType = loginRequest.getUser_type();
        if (!"user".equals(userType) && !"teach_sec".equals(userType)
                && !"class_mgr".equals(userType) && !"super_admin".equals(userType)) {
            return ResultDTO.fail(400, "无效的用户类型");
        }

        return authService.login(loginRequest);
    }
}