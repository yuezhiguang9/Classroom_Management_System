package demo.campus_management_system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@TableName("apply_info")
@Data
@AllArgsConstructor
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


}
