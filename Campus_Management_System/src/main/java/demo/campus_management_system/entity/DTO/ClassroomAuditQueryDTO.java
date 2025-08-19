package demo.campus_management_system.entity.DTO;

import lombok.Data;

@Data
public class ClassroomAuditQueryDTO {
    private String token; // 用于解析账号密码
    private String mgr_account;  // 教室管理员账号（非必填）
    private String apply_status; // 审核状态筛选（已批准/已驳）
    private String date_start;   // 开始日期 yyyy-MM-dd
    private String date_end;     // 结束日期 yyyy-MM-dd
    private Integer page = 1;    // 当前页码，默认1
    private Integer size = 10;   // 每页条数，默认10
}