package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("classroom")
public class Classroom {

    //教室编号
    @TableId(value = "room_num", type = IdType.ASSIGN_ID)
    private String room_nm;

    //教室类型
    private String room_type;

    //教室容量
    private Integer room_capacity;

    //所在楼栋id
    private String building_id;

    //所属学院id
    private String college_id;

    public String getRoom_nm() {
        return room_nm;
    }

    public void setRoom_nm(String room_nm) {
        this.room_nm = room_nm;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public Integer getRoom_capacity() {
        return room_capacity;
    }

    public void setRoom_capacity(Integer room_capacity) {
        this.room_capacity = room_capacity;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }
}
