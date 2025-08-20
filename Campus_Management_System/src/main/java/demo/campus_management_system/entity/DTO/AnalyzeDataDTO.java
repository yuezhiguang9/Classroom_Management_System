package demo.campus_management_system.entity.DTO;

import demo.campus_management_system.entity.VO.BuildingUsageVO;
import demo.campus_management_system.entity.VO.RoomTypeUsageVO;
import demo.campus_management_system.entity.VO.RoomUsageVO;
import lombok.Data;

import java.util.List;

@Data
public class AnalyzeDataDTO {
    // 顶层统计字段
    private Integer total_users;
    private Integer total_teach_secs;
    private Integer total_classroom_mgrs;
    private Integer active_users;
    private Integer total_applies;
    private Integer mom_appt_comparison;//当月于上月预约总数的差
    private List<RoomUsageVO> active_classroom;// 教室列表
    private List<RoomTypeUsageVO> active_classroom_type;// 教室类型列表
    private List<BuildingUsageVO> total_of_building; // 直接存储对象列表，无需JSON字符串

}