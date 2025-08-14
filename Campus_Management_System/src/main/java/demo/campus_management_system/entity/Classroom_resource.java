package demo.campus_management_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("classroom_resource")
public class Classroom_resource {

    //
    @TableId(value = "res_id", type = IdType.ASSIGN_ID)
    private String resId;

    //日期
    private Date resDate;

    //周次
    private String resWeek;

    //星期
    private String resDayOfWeek;

    //节次
    private String resPeriod;

    //教室状态
    private String resRoomStatus;

    //教室编号
    private String roomNum;

    //信息编号
    private String applyInfoId;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public Date getResDate() {
        return resDate;
    }

    public void setResDate(Date resDate) {
        this.resDate = resDate;
    }

    public String getResWeek() {
        return resWeek;
    }

    public void setResWeek(String resWeek) {
        this.resWeek = resWeek;
    }

    public String getResDayOfWeek() {
        return resDayOfWeek;
    }

    public void setResDayOfWeek(String resDayOfWeek) {
        this.resDayOfWeek = resDayOfWeek;
    }

    public String getResPeriod() {
        return resPeriod;
    }

    public void setResPeriod(String resPeriod) {
        this.resPeriod = resPeriod;
    }

    public String getResRoomStatus() {
        return resRoomStatus;
    }

    public void setResRoomStatus(String resRoomStatus) {
        this.resRoomStatus = resRoomStatus;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getApplyInfoId() {
        return applyInfoId;
    }

    public void setApplyInfoId(String applyInfoId) {
        this.applyInfoId = applyInfoId;
    }
}
