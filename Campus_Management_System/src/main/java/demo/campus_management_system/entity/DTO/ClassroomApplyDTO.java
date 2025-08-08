package demo.campus_management_system.entity.DTO;

import java.util.List;


//用来传入预约信息的类
public class ClassroomApplyDTO {
    //用户id
    private String user_account;
    //姓名
    private String user_name;
    //教室资源id
    //因为有可能一次预约多个教室、或者多节，所以做成一个List类型的数据
    private String[] res_id;
    //申请理由
    private String purpose;
    //参与人数
    private Integer person_count;

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String[] getRes_id() {
        return res_id;
    }

    public void setRes_id(String[] res_id) {
        this.res_id = res_id;
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
}
