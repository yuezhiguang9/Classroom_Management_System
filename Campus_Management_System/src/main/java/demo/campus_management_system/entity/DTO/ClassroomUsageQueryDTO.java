package demo.campus_management_system.entity.DTO;

import lombok.Data;

/**
 * 教室使用率查询DTO
 */
@Data
public class ClassroomUsageQueryDTO {
    
    /**
     * 时间范围筛选（week/month/semester）
     */
    private String time_range;
    
    /**
     * 自定义开始日期（yyyy-MM-dd）
     */
    private String date_start;
    
    /**
     * 自定义结束日期（yyyy-MM-dd）
     */
    private String date_end;
    
    /**
     * 前端显示所申请楼栋名称（下拉框）
     */
    private String building_id;
    
    /**
     * 教室类型筛选
     */
    private String room_type;
    
    /**
     * 表格数据页码（默认1）
     */
    private Integer page = 1;
    
    /**
     * 表格数据每页条数（默认10）
     */
    private Integer size = 10;
}