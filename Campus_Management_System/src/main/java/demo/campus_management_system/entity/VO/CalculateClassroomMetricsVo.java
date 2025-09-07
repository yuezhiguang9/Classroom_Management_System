package demo.campus_management_system.entity.VO;

import lombok.Data;

@Data
public class CalculateClassroomMetricsVo {

    /**
     * 本时段平均使用率（数值）
     */
    private String averageUsageRate;

    /**
     * 使用最频繁教室（教室编号）
     */
    private String mostUsedClassroom;

    /**
     * 最频繁教室使用次数（次数）
     */
    private String mostUsedCount;

    /**
     * 使用最少教室（教室编号）
     */
    private String leastUsedClassroom;

    /**
     * 最少教室使用次数（次数）
     */
    private String leastUsedCount;

    /**
     * 本周比较上周（上升/下降描述）
     */
    private String weeklyComparison;

}
