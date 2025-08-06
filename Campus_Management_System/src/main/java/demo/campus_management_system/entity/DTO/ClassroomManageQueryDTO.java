package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 教室管理查询DTO
 */
@Data
public class ClassroomManageQueryDTO {
    
    /**
     * 楼层筛选，通过教室号的第一个数字
     */
    private String room_num;
    
    /**
     * 最小容量筛选
     */
    private Integer capacity;
    
    /**
     * 教室状态筛选(可用/已占用/维护中)
     */
    private String room_status;
    
    /**
     * 教室类型筛选
     */
    private String room_type;
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页条数
     */
    private Integer size = 10;
}