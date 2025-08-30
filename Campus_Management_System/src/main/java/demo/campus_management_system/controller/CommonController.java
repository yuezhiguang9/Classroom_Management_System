package demo.campus_management_system.controller;

import demo.campus_management_system.entity.Building;
import demo.campus_management_system.entity.College;
import demo.campus_management_system.entity.DTO.ClassroomTypeDTO;
import demo.campus_management_system.entity.DTO.ResultDTO;
import demo.campus_management_system.service.impl.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    CommonService commonService;

    //获取学院信息
    @GetMapping("getColleges")
    public ResultDTO<List<College>> getColleges() {
        return commonService.getColleges();
    }

    //获取楼栋列表

    @GetMapping("getBuildings")
    public ResultDTO<List<Building>> getBuildings() {
        return commonService.getBuildings();
    }

    @GetMapping("getRoomTypes")
    public ResultDTO<List<ClassroomTypeDTO>> getRoomTypes() {
        return commonService.getClassroomTypes();
    }


}
