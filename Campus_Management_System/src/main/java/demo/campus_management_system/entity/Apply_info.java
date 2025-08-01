package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("apply_id")
public class Apply_info {

    //申请编号
    @TableId(value = "apply_id", type = IdType.ASSIGN_ID)
    private String apply_id;

    //预约人数
    private String apply_person_count;

    //预约目的
    private String apply_purpose;

    //预约时间
    private String apply_book_time;

    //审核状态
    private String apply_status;

    //驳回原因
    private String apply_reject_reason;

    //申请人id
    private String user_account;

    //审核人id
    private String sec_account;

    //是否取消标志
    private Integer user_cancel;


    public String getApply_id() {
        return apply_id;
    }

    public void setApply_id(String apply_id) {
        this.apply_id = apply_id;
    }

    public String getApply_person_count() {
        return apply_person_count;
    }

    public void setApply_person_count(String apply_person_count) {
        this.apply_person_count = apply_person_count;
    }

    public String getApply_purpose() {
        return apply_purpose;
    }

    public void setApply_purpose(String apply_purpose) {
        this.apply_purpose = apply_purpose;
    }

    public String getApply_book_time() {
        return apply_book_time;
    }

    public void setApply_book_time(String apply_book_time) {
        this.apply_book_time = apply_book_time;
    }

    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }

    public String getApply_reject_reason() {
        return apply_reject_reason;
    }

    public void setApply_reject_reason(String apply_reject_reason) {
        this.apply_reject_reason = apply_reject_reason;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getSec_account() {
        return sec_account;
    }

    public void setSec_account(String sec_account) {
        this.sec_account = sec_account;
    }

    public Integer getUser_cancel() {
        return user_cancel;
    }

    public void setUser_cancel(Integer user_cancel) {
        this.user_cancel = user_cancel;
    }
}
