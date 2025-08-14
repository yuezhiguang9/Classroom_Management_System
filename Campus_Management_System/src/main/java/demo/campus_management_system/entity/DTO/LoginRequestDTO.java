package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 登录请求DTO
 */
@Data
public class LoginRequestDTO {
    
    /**
     * 用户类型(user/teach_sec/class_mgr/super_admin)
     */
    private String user_type;
    
    /**
     * 登录账号
     */
    private String account;
    
    /**
     * 密码
     */
    private String password;


}