package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 更新审核状态DTO
 */
@Data
public class UpdateStatusDTO {
    
    /**
     * 申请记录唯一标识
     */
    private String apply_id;
    
    /**
     * 申请状态(待审核/已通过/已驳回)
     */
    private String apply_status;
    
    /**
     * 驳回原因(仅当status=已驳回时必填)
     */
    private String reject_reason;
}