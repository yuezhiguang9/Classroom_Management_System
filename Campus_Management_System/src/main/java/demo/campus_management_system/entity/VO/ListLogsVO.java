package demo.campus_management_system.entity.VO;

import java.util.Date;

public class ListLogsVO {
    //申请人姓名
    private String user_name;


    //联系电话
    private String phone;

    //预约时间
    private Date book_time;

    //教室（格式：building_name+room_num)
    private String building_name;

    private String room_num;


    //使用时间（格式：date+week+day_of_week+period)
    private String date;

    private String week;

    private String day_of_week;

    private String period;

    //用途
    private String purpose;

    //人数
    private Integer person_count;

    //申请状态
    private String apply_status;


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBook_time() {
        return book_time;
    }

    public void setBook_time(Date book_time) {
        this.book_time = book_time;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getPerson_count() {
        return person_count;
    }

    public void setPerson_count(Integer person_count) {
        this.person_count = person_count;
    }

    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }


}
