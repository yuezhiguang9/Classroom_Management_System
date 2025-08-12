package demo.campus_management_system.entity.DTO;

import demo.campus_management_system.entity.VO.batchUpdateStatusVO;
import lombok.Data;

//一个接受数据的类
@Data
public class batchUpdateStatusDTO {

    private batchUpdateStatusVO[] batchUpdateStatusVOS;

}
