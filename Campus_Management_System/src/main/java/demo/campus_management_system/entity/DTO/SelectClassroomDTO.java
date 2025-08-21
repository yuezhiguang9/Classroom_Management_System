package demo.campus_management_system.entity.DTO;

import lombok.Data;

import java.util.List;


@Data
public class SelectClassroomDTO {

    //楼名
    private String building_name;
    //教室名称
    private String room_num;
    //容量
    private Integer capacity;
    //教室类型
    private String room_type;
    //周次
    private String res_week;
    //星期
    private String res_day_of_week;
    //节次
    private List<String> period;
    //教室状态
    private String room_status;
    //日期
    private String res_date;


}
