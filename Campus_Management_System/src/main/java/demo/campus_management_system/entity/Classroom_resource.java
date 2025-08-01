package demo.campus_management_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("classroom_resource")
public class Classroom_resource {

    //
    @TableId(value = "res_id", type = IdType.ASSIGN_ID)
    private String res_id;

    //日期
    private String res_date;

    //周次
    private String res_week;

    //星期
    private String res_day_of_week;

    //节次
    private String res_period;

    //教室状态
    private String res_room_status;

    //教室编号
    private String room_num;

    //信息编号
    private String apply_info_id;


    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getRes_date() {
        return res_date;
    }

    public void setRes_date(String res_date) {
        this.res_date = res_date;
    }

    public String getRes_week() {
        return res_week;
    }

    public void setRes_week(String res_week) {
        this.res_week = res_week;
    }

    public String getRes_day_of_week() {
        return res_day_of_week;
    }

    public void setRes_day_of_week(String res_day_of_week) {
        this.res_day_of_week = res_day_of_week;
    }

    public String getRes_period() {
        return res_period;
    }

    public void setRes_period(String res_period) {
        this.res_period = res_period;
    }

    public String getRes_room_status() {
        return res_room_status;
    }

    public void setRes_room_status(String res_room_status) {
        this.res_room_status = res_room_status;
    }

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getApply_info_id() {
        return apply_info_id;
    }

    public void setApply_info_id(String apply_info_id) {
        this.apply_info_id = apply_info_id;
    }
}
