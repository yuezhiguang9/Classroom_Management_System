package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("teach_secretary")
public class Teach_Secretary {

    //账号
    @TableId(value = "sec_account", type = IdType.ASSIGN_ID)
    private String sec_account;

    //密码
    private String sec_password;

    //姓名
    private String sec_name;

    //办公室电话
    private String office_phone;


    //所属学院
    private String college_id;

    public String getSec_account() {
        return sec_account;
    }

    public void setSec_account(String sec_account) {
        this.sec_account = sec_account;
    }

    public String getSec_password() {
        return sec_password;
    }

    public void setSec_password(String sec_password) {
        this.sec_password = sec_password;
    }

    public String getSec_name() {
        return sec_name;
    }

    public void setSec_name(String sec_name) {
        this.sec_name = sec_name;
    }

    public String getOffice_phone() {
        return office_phone;
    }

    public void setOffice_phone(String office_phone) {
        this.office_phone = office_phone;
    }

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }
}
