package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 用户操作DTO(添加/编辑用户)
 */
@Data
public class UserOperationDTO {
    
    /**
     * 账号，唯一标识用户
     */
    private String account;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 密码，不修改时留空
     */
    private String password;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 所属学院，普通用户、教学秘书角色时必填
     */
    private String college_id;
    
    /**
     * 所管楼栋，仅用于教室管理员
     */
    private String building_id;
    
    /**
     * 角色，区分用户、教秘、教室管理员
     */
    private String user_type;
}