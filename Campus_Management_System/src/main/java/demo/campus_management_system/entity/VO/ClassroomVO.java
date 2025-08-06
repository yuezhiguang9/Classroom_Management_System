package demo.campus_management_system.entity.VO;

import lombok.Data;

/**
 * 教室信息VO
 */
@Data
public class ClassroomVO {
    
    /**
     * 教室(格式：building_name+room_num)
     */
    private String classroom;
    
    /**
     * 容量
     */
    private Integer capacity;
    
    /**
     * 教室类型
     */
    private String room_type;
    
    /**
     * 操作按钮("预约此教室"/"不可预约")
     */
    private String operation;
}