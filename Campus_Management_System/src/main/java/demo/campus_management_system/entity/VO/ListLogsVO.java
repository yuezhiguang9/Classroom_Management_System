package demo.campus_management_system.entity.VO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ListLogsVO {
    //申请编号
    private String applyid;
    //申请人姓名
    private String user_name;
    private String userName;
    //所属院系
    private String collegeName;
    //驳回原因
    private String rejectReason;
    //驳回原因
    private String processingTime;


    //联系电话
    private String phone;

    //预约时间
    private Date book_time;

    //教室（格式：building_name+room_num)
    private String building_name;

    private String room_num;


    //使用时间（格式：date+week+day_of_week+period)
    //日期
    private String date;
    //周次
    private String week;
    //星期
    private String day_of_week;
    //节次

    private String dayOfWeek;

    private String period;

    //用途
    private String purpose;

    //人数
    private Integer person_count;

    //申请状态
    private String apply_status;


    private String applyStatus;

    private String apply_reject_reason;


}
