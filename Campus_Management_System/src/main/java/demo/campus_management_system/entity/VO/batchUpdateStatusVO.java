package demo.campus_management_system.entity.VO;


import cn.hutool.core.date.DateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class batchUpdateStatusVO {
    //教室id
    private String room_num;
    //改教室即将更改的状态
    private String room_status;
    //当更改为不可用时的持续时间，比如更改b001-101这个教室到2025-8-10 23：59:59前都不用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime duration;
}
