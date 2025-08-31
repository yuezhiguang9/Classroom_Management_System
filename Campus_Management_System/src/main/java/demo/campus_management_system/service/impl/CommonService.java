package demo.campus_management_system.service.impl;

import demo.campus_management_system.dao.dao_interface.CommonMapper;
import demo.campus_management_system.entity.Building;
import demo.campus_management_system.entity.College;
import demo.campus_management_system.entity.DTO.ClassroomTypeDTO;
import demo.campus_management_system.entity.DTO.ResultDTO;
import demo.campus_management_system.service.service_interface.CommonServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService implements CommonServiceInterface {

    @Autowired
    CommonMapper commonMapper;


    @Override
    public ResultDTO<List<College>> getColleges() {
        try {
            List<College> collegeList;

            collegeList = commonMapper.selectColleges();
            return ResultDTO.success(collegeList, "返回结果成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(400, "查询学院资源失败");
        }
    }

    @Override
    public ResultDTO<List<Building>> getBuildings() {
        try {
            List<Building> buildingList;
            buildingList = commonMapper.selectBuildings();
            return ResultDTO.success(buildingList, "查询楼栋资源结果成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(400, "查询楼栋资源失败");
        }
    }

    public ResultDTO<List<ClassroomTypeDTO>> getClassroomTypes() {
        try {
            List<ClassroomTypeDTO> classroomTypeDTOS;
            classroomTypeDTOS = commonMapper.selectRoomTypes();
            return ResultDTO.success(classroomTypeDTOS, "查询教室类型成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(400, "查询失败");
        }
    }

}
