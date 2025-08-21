package demo.campus_management_system.entity.DTO;

import demo.campus_management_system.entity.VO.ClassroomAuditRecordVO;
import lombok.Data;

import java.util.List;

@Data
public class ClassroomAuditResultDTO {
    private Integer managed_rooms;      // 管辖教室总数
    private Long total_applyinfo;       // 符合条件的通知总数
    private Integer page;               // 当前页码
    private Integer size;               // 每页条数
    private List<ClassroomAuditRecordVO> records; // 审核记录列表
}