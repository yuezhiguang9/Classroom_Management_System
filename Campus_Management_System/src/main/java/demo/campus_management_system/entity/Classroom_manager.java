package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("classroom_manager")
public class Classroom_manager {

    @TableId(value = "mgr_account", type = IdType.ASSIGN_ID)
    private String mgr_account;

    //密码
    private String mgr_password;

    //联系电话
    private String mgr_phone;

    //所在楼栋
    private String building_id;

    public String getMgr_password() {
        return mgr_password;
    }

    public void setMgr_password(String mgr_password) {
        this.mgr_password = mgr_password;
    }

    public String getMgr_phone() {
        return mgr_phone;
    }

    public void setMgr_phone(String mgr_phone) {
        this.mgr_phone = mgr_phone;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getMgr_account() {
        return mgr_account;
    }

    public void setMgr_account(String mgr_account) {
        this.mgr_account = mgr_account;
    }
}
