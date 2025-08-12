package demo.campus_management_system.entity.VO;

import lombok.Data;

@Data
public class myReservationsVO {
    //申请编号
    private String apply_id;

    //教室
    private String building_name;
    private String room_num;

    //使用日期
    private String date;

    //周次
    private String week;

    //星期
    private String res_day_of_week;

    //节次
    private String period;

    //用途（理由）
    private String purpose;

    //拒绝理由
    private String reject_reason;

    //预约时间
    private String book_time;

    //状态
    private String apply_status;

    //是否可以取消
    private Integer can_cancel;

}
