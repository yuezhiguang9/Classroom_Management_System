package demo.campus_management_system.entity.VO;

import lombok.Data;

/**
 * 申请信息管理VO
 */
@Data
public class ApplyInfoManageVO {
    
    /**
     * 管辖教室总数
     */
    private Integer managed_rooms;
    
    /**
     * 申请编号
     */
    private String apply_id;
    
    /**
     * 审核者教秘姓名
     */
    private String sec_name;
    
    /**
     * 教室(格式：building_name+room_num)
     */
    private String classroom;
    
    /**
     * 使用日期
     */
    private String date;
    
    /**
     * 用途
     */
    private String purpose;
}