package demo.campus_management_system.service.service_interface;

import demo.campus_management_system.entity.DTO.LoginRequestDTO;
import demo.campus_management_system.entity.DTO.LoginResponseDTO;
import demo.campus_management_system.util.ResultDTO;

/**
 * 认证服务接口
 */
public interface AuthService {
    
    /**
     * 用户登录
     * @param loginRequest 登录请求数据
     * @return 登录结果
     */
    ResultDTO<LoginResponseDTO> login(LoginRequestDTO loginRequest);
    
    /**
     * 验证Token有效性
     * @param token JWT令牌
     * @return 是否有效
     */
    boolean validateToken(String token);
    
    /**
     * 从Token获取用户信息
     * @param token JWT令牌
     * @return 用户账号
     */
    String getUserAccountFromToken(String token);
}