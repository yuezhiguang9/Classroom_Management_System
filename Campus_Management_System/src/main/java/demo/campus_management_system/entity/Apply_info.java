package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("apply_id")
public class Apply_info {

    //申请编号
    @TableId(value = "apply_id", type = IdType.ASSIGN_ID)
    private String applyId;

    //预约人数
    private Integer applyPersonCount;

    //预约目的
    private String applyPurpose;

    //预约时间
    private Date applyBookTime;

    //审核状态
    private String applyStatus;

    //驳回原因
    private String applyRejectReason;

    //申请人id
    private String userAccount;

    //审核人id
    private String secAccount;

    //是否取消标志
    private Integer userCancel;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyPersonCount() {
        return applyPersonCount;
    }

    public void setApplyPersonCount(Integer applyPersonCount) {
        this.applyPersonCount = applyPersonCount;
    }

    public String getApplyPurpose() {
        return applyPurpose;
    }

    public void setApplyPurpose(String applyPurpose) {
        this.applyPurpose = applyPurpose;
    }

    public Date getApplyBookTime() {
        return applyBookTime;
    }

    public void setApplyBookTime(Date applyBookTime) {
        this.applyBookTime = applyBookTime;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyRejectReason() {
        return applyRejectReason;
    }

    public void setApplyRejectReason(String applyRejectReason) {
        this.applyRejectReason = applyRejectReason;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getSecAccount() {
        return secAccount;
    }

    public void setSecAccount(String secAccount) {
        this.secAccount = secAccount;
    }

    public Integer getUserCancel() {
        return userCancel;
    }

    public void setUserCancel(Integer userCancel) {
        this.userCancel = userCancel;
    }
}
