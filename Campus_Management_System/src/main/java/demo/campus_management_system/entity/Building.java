package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("building")
public class Building {

    //楼栋编号
    @TableId(value = "building_id", type = IdType.ASSIGN_ID)
    private String building_id;

    //楼栋名称
    private String building_name;

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }
}
