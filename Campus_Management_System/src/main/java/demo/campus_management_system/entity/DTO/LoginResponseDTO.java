package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponseDTO {
    
    /**
     * JWT认证令牌
     */
    private String token;
    
    /**
     * 用户账号
     */
    private String account;
    
    /**
     * 用户姓名
     */
    private String name;
    
    /**
     * 用户角色
     */
    private String user_type;
    
    /**
     * 所属学院
     */
    private String college;
}