package demo.campus_management_system.entity.VO;

import lombok.Data;

/**
 * 教室管理VO
 */
@Data
public class ClassroomManageVO {
    
    /**
     * 今日可用教室数
     */
    private Integer today_available_count;
    
    /**
     * 教室编号
     */
    private String room_num;
    
    /**
     * 教室容量
     */
    private Integer capacity;
    
    /**
     * 教室类型
     */
    private String room_type;
    
    /**
     * 教室状态(可用/维修中/已占用)
     */
    private String room_status;
}