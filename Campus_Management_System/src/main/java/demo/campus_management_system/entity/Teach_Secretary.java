package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("teach_secretary")
public class Teach_Secretary {

    //账号
    @TableId(value = "sec_account", type = IdType.ASSIGN_ID)
    private String secAccount;

    //密码
    private String secPassword;

    //姓名
    private String secName;

    //办公室电话
    private String officePhone;


    //所属学院
    private String collegeId;

    public String getSecAccount() {
        return secAccount;
    }

    public void setSecAccount(String secAccount) {
        this.secAccount = secAccount;
    }

    public String getSecPassword() {
        return secPassword;
    }

    public void setSecPassword(String secPassword) {
        this.secPassword = secPassword;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}
