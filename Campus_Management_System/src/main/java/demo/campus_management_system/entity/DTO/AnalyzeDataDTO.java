package demo.campus_management_system.entity.DTO;

import java.util.List;

public class AnalyzeDataDTO {
    // 用户相关
    private Integer total_users; // 总用户数（users表）
    private Integer total_teach_secs;
    private Integer total_classroom_mgrs;
    private Integer active_users; // 活跃用户数（login表近30天）

    // 预约相关
    private Integer total_applies; // 总预约数（apply_info表）


    // 经常使用的教室信息
    private List<AnalyzeDataDTO> active_classroom;

    // 经常使用的教室类型
    private String active_classroom_type;

    //每月每栋预约数
    private String total_of_building;


    public Integer getTotal_users() {
        return total_users;
    }

    public void setTotal_users(Integer total_users) {
        this.total_users = total_users;
    }


    public Integer getTotal_teach_secs() {
        return total_teach_secs;
    }

    public void setTotal_teach_secs(Integer total_teach_secs) {
        this.total_teach_secs = total_teach_secs;
    }

    public Integer getTotal_classroom_mgrs() {
        return total_classroom_mgrs;
    }

    public void setTotal_classroom_mgrs(Integer total_classroom_mgrs) {
        this.total_classroom_mgrs = total_classroom_mgrs;
    }

    public Integer getActive_users() {
        return active_users;
    }

    public void setActive_users(Integer active_users) {
        this.active_users = active_users;
    }

    public Integer getTotal_applies() {
        return total_applies;
    }

    public void setTotal_applies(Integer total_applies) {
        this.total_applies = total_applies;
    }

    public List<AnalyzeDataDTO> getActive_classroom() {
        return active_classroom;
    }

    public void setActive_classroom(List<AnalyzeDataDTO> active_classroom) {
        this.active_classroom = active_classroom;
    }

    public String getActive_classroom_type() {
        return active_classroom_type;
    }

    public void setActive_classroom_type(String active_classroom_type) {
        this.active_classroom_type = active_classroom_type;
    }

    public String getTotal_of_building() {
        return total_of_building;
    }

    public void setTotal_of_building(String total_of_building) {
        this.total_of_building = total_of_building;
    }
}