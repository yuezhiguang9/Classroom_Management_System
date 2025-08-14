package demo.campus_management_system.entity.VO;

import lombok.Data;

/**
 * 用户列表VO
 */
@Data
public class UserListVO {
    
    /**
     * 姓名(用户名列)
     */
    private String name;
    
    /**
     * 角色
     */
    private String userType;
    
    /**
     * 所属学院
     */
    private String college;
    
    /**
     * 学号/工号
     */
    private String userAccount;
    
    /**
     * 最后登录时间
     */
    private String lastLogin;
}