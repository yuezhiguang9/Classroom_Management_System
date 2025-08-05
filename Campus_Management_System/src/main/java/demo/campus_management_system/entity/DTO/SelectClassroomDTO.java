package demo.campus_management_system.entity.DTO;

import java.util.Date;

public class SelectClassroomDTO {
    //楼名
    private String building_name;
    //教室名称
    private String room_num;
    //容量
    private Integer capacity;
    //教室类型
    private String room_type;
    //周次
    private String res_week;
    //星期
    private String res_day_of_week;
    //节次
    private String period;
    //教室状态
    private String room_status;
    //日期
    private String res_date;

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRoom_status() {
        return room_status;
    }

    public void setRoom_status(String room_status) {
        this.room_status = room_status;
    }

    public String getRes_date() {
        return res_date;
    }

    public void setRes_date(String res_date) {
        this.res_date = res_date;
    }
}
