package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 用户列表查询DTO
 */
@Data
public class UserListQueryDTO {
    
    /**
     * 用户角色(all/user/teach_sec/class_mgr)
     */
    private String user_type;
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    private Integer size = 10;
    
    /**
     * 学院编号
     */
    private String college_id;
    
    /**
     * 用户姓名
     */
    private String user_name;
    
    /**
     * 用户编号
     */
    private String user_id;
}