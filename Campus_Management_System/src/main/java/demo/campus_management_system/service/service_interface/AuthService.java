package demo.campus_management_system.service.service_interface;

import demo.campus_management_system.entity.DTO.LoginRequestDTO;
import demo.campus_management_system.entity.DTO.LoginResponseDTO;
import demo.campus_management_system.entity.DTO.ResultDTO;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求数据
     * @return 登录结果
     */
    ResultDTO<LoginResponseDTO> login(LoginRequestDTO loginRequest);

}