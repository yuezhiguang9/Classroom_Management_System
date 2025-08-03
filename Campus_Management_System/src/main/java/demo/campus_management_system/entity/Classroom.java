package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("classroom")
public class Classroom {

    //教室编号
    @TableId(value = "room_num", type = IdType.ASSIGN_ID)
    private String roomNm;

    //教室类型
    private String roomType;

    //教室容量
    private Integer roomCapacity;

    //所在楼栋id
    private String buildingId;

    //所属学院id
    private String collegeId;

    public String getRoomNm() {
        return roomNm;
    }

    public void setRoomNm(String roomNm) {
        this.roomNm = roomNm;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}
