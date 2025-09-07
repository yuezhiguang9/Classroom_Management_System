package demo.campus_management_system.entity.VO;

import lombok.Data;

/**
 * 教室使用率VO
 */
@Data
public class ClassroomUsageVO {

    /**
     * 教室名称（building_name + "-" + room_num）
     */
    private String classroom;

    /**
     * 教室类型
     */
    private String roomType;

    /**
     * 本时段使用次数
     */
    private Integer usageCount;

    /**
     * 本时段使用率
     */
    private String usageRate;

    /**
     * 较上周变化率
     */
    private String changeRate;
}