package demo.campus_management_system.entity.VO;

import lombok.Data;

@Data
public class CalculateClassroomMetricsVo {

    /**
     * 本时段平均使用率（数值）
     */
    private String average_usage_rate;

    /**
     * 使用最频繁教室（教室编号）
     */
    private String most_used_classroom;

    /**
     * 最频繁教室使用次数（次数）
     */
    private String most_used_count;

    /**
     * 使用最少教室（教室编号）
     */
    private String least_used_classroom;

    /**
     * 最少教室使用次数（次数）
     */
    private String least_used_count;

    /**
     * 本周比较上周（上升/下降描述）
     */
    private String weekly_comparison;

}
