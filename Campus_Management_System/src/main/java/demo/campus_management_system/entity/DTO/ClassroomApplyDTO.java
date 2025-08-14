package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 教室预约申请DTO
 */
@Data
public class ClassroomApplyDTO {
    
    /**
     * 学号(从登录态获取)
     */
    private String user_account;
    
    /**
     * 申请人姓名
     */
    private String user_name;
    
    /**
     * 所属学院
     */
    private String college_id;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 用途
     */
    private String purpose;
    
    /**
     * 参与人数
     */
    private Integer person_count;
    
    /**
     * 教室容量
     */
    private Integer capacity;
    
    /**
     * 使用日期(格式：yyyy-MM-dd)
     */
    private String date;
    
    /**
     * 周数
     */
    private Integer week;
    
    /**
     * 星期
     */
    private String day_of_week;
    
    /**
     * 选择节次
     */
    private String period;
    
    /**
     * 所选教室编号
     */
    private String room_num;

    private String[] res_id;
}