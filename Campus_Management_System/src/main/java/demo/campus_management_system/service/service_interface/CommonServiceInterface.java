package demo.campus_management_system.service.service_interface;


import demo.campus_management_system.entity.Building;
import demo.campus_management_system.entity.College;
import demo.campus_management_system.entity.DTO.ResultDTO;

import java.util.List;

public interface CommonServiceInterface {
    //获取学院
    public ResultDTO<List<College>> getColleges();

    //获取楼栋信息
    public ResultDTO<List<Building>> getBuildings();

}
