package demo.campus_management_system.entity.VO;

import lombok.Data;

@Data
public class ClassroomAuditRecordVO {
    private String user_name;    // 申请人姓名
    private String sec_name;     // 审核者教秘姓名
    private String classroom;    // 教室（格式：building_name+room_num）
    private String date;         // 使用日期（res_week+res_day_of_week+res_period）
    private String purpose;      // 用途
}