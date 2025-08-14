package demo.campus_management_system.entity.DTO;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.VO.myReservationsVO;
import lombok.Data;

@Data
public class myReservationsDTO {
    //本周我的预约数
    private Integer week_total;
    //待审核预约数
    private Integer my_pending;

    private Page<myReservationsVO> myReservationsVoPage;

}
