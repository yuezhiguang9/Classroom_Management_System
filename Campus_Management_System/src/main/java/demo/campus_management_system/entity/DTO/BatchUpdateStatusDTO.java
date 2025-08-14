package demo.campus_management_system.entity.DTO;

import lombok.Data;
import java.util.List;

/**
 * 批量更新教室状态DTO
 */
@Data
public class BatchUpdateStatusDTO {
    
    /**
     * 需更新状态的教室编号列表
     */
    private List<String> room_nums;
    
    /**
     * 教室状态(可用/维修中/已占用)
     */
    private String room_status;
}