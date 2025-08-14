package demo.campus_management_system.entity.VO;

import lombok.Data;

/**
 * 用户预约记录VO
 */
@Data
public class ReservationVO {
    
    /**
     * 申请编号
     */
    private String apply_id;
    
    /**
     * 教室(格式：building_name+room_num)
     */
    private String classroom;
    
    /**
     * 使用日期
     */
    private String date;
    
    /**
     * 周次
     */
    private Integer week;
    
    /**
     * 节次
     */
    private String period;
    
    /**
     * 用途
     */
    private String purpose;
    
    /**
     * 预约时间(提交申请的时间)
     */
    private String book_time;
    
    /**
     * 审核状态(待审核/已通过/已驳回)
     */
    private String apply_status;
    
    /**
     * 是否可取消
     */
    private Boolean can_cancel;
}