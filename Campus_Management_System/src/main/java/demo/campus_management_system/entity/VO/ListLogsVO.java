package demo.campus_management_system.entity.VO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ListLogsVO {
    //申请人姓名
    private String userName;


    //联系电话
    private String phone;

    //预约时间
    private Date bookTime;

    //教室（格式：building_name+room_num)
    private String buildingName;

    private String roomNum;


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
    private Integer personCount;

    //申请状态
    private String apply_status;


    private String applyStatus;


}
