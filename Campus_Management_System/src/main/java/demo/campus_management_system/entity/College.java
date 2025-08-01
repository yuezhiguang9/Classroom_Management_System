package demo.campus_management_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.lang.reflect.Type;

@TableName("college")
public class College {

    //学院编号
    @TableId(value = "college_id", type = IdType.ASSIGN_ID)
    private String college_id;

    //学院名称
    private String college_name;

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }
}
