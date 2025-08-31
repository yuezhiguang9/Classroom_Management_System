package demo.campus_management_system.dao.dao_interface;

import demo.campus_management_system.entity.Building;
import demo.campus_management_system.entity.Classroom;
import demo.campus_management_system.entity.College;
import demo.campus_management_system.entity.DTO.ClassroomTypeDTO;

import java.util.List;

public interface CommonMapper {
    List<College> selectColleges();

    List<Building> selectBuildings();

    List<ClassroomTypeDTO> selectRoomTypes();
}
