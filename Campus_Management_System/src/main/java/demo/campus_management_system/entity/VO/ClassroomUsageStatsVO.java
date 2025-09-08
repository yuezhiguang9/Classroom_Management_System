package demo.campus_management_system.entity.VO;

import lombok.Data;

@Data
public class ClassroomUsageStatsVO {


    /**
     * 本周批准的申请数量
     */
    private String thisWeekApproved;

    /**
     * 本周拒绝的申请数量
     */
    private String thisWeekRejected;

    /**
     * 今日待审核的申请数量
     */
    private String todayPending;

    /**
     * 批准申请与昨日对比
     */
    private String approvedVsYesterday;

    /**
     * 拒绝申请与上周对比
     */
    private String rejectedVsLastWeek;

    /**
     * 待审核申请与上周对比
     */
    private String pendingVsLastWeek;

}
