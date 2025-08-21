package demo.campus_management_system.entity.DTO;

import lombok.Data;

import java.util.List;

/**
 * 教室预约申请DTO
 */
@Data
public class ClassroomApplyDTO {
    //学号
    private String user_account;
    //使用日期
    private String use_date;
    //教室号
    private String room_num;
    //节次
    private List<String> period;
    //原因
    private String purpose;
    //参与人数
    private Integer person_count;
}