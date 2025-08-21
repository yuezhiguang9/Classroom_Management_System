package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.campus_management_system.entity.Building;
import demo.campus_management_system.entity.College;

import java.util.List;

public interface CommonMapper {
    List<College> getColleges();

    List<Building> getBuildings();
}
