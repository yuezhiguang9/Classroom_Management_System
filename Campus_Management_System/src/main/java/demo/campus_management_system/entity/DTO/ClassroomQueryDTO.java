package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 教室查询请求DTO
 */
@Data
public class ClassroomQueryDTO {
    
    /**
     * 教学楼筛选
     */
    private String building_id;
    
    /**
     * 最小容量筛选
     */
    private Integer capacity;
    
    /**
     * 教室类型筛选
     */
    private String room_type;
    
    /**
     * 教室状态筛选(可用/维修中/已占用)
     */
    private String room_status;
    
    /**
     * 使用日期(格式：yyyy-MM-dd)
     */
    private String date;
    
    /**
     * 使用节次
     */
    private String period;
    
    /**
     * 当前页码
     */
    private Integer page = 1;
    
    /**
     * 每页条数
     */
    private Integer size = 10;
}