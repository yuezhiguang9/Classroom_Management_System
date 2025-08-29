package demo.campus_management_system.entity.VO;

import lombok.Data;

/**
 * 教室使用率VO
 */
@Data
public class ClassroomUsageVO {
    
    /**
     * 本时段平均使用率
     */
    private String avgUsageRate;
    
    /**
     * 使用最频繁的教室名称
     */
    private String mostUsed;
    
    /**
     * 最高使用次数
     */
    private Integer mostUsageCount;
    
    /**
     * 使用最少的教室名称
     */
    private String leastUsed;
    
    /**
     * 最少使用次数
     */
    private Integer leastUsageCount;
    
    /**
     * 本时段所有教室总使用次数
     */
    private Integer totalUsage;
    
    /**
     * 教室名称（building_name+room_num）
     */
    private String classroom;
    
    /**
     * 教室类型
     */
    private String roomType;
    
    /**
     * 本时段单教室使用次数
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