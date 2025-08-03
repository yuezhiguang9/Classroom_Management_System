package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("classroom_manager")
public class Classroom_manager {

    @TableId(value = "mgr_account", type = IdType.ASSIGN_ID)
    private String mgrAccount;

    //密码
    private String mgrPassword;

    //联系电话
    private String mgrPhone;

    //所在楼栋
    private String buildingId;

    public String getMgrAccount() {
        return mgrAccount;
    }

    public void setMgrAccount(String mgrAccount) {
        this.mgrAccount = mgrAccount;
    }

    public String getMgrPassword() {
        return mgrPassword;
    }

    public void setMgrPassword(String mgrPassword) {
        this.mgrPassword = mgrPassword;
    }

    public String getMgrPhone() {
        return mgrPhone;
    }

    public void setMgrPhone(String mgrPhone) {
        this.mgrPhone = mgrPhone;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
}
