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
    private String avg_usage_rate;
    
    /**
     * 使用最频繁的教室名称
     */
    private String most_used;
    
    /**
     * 最高使用次数
     */
    private Integer most_usage_count;
    
    /**
     * 使用最少的教室名称
     */
    private String least_used;
    
    /**
     * 最少使用次数
     */
    private Integer least_usage_count;
    
    /**
     * 本时段所有教室总使用次数
     */
    private Integer total_usage;
    
    /**
     * 教室名称（building_name+room_num）
     */
    private String classroom;
    
    /**
     * 教室类型
     */
    private String room_type;
    
    /**
     * 本时段单教室使用次数
     */
    private Integer usage_count;
    
    /**
     * 本时段使用率
     */
    private String usage_rate;
    
    /**
     * 较上周变化率
     */
    private String change_rate;
}