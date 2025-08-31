package demo.campus_management_system.service.service_interface;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.campus_management_system.entity.DTO.*;
import demo.campus_management_system.entity.Super_admin;

import java.util.List;


public interface SuperAdmin extends IService<Super_admin> {
    // 删除用户方法
    ResultDTO<Boolean> deleteUsers(String token, DeleteUsersDTO deleteUsersDTO);
    // 添加用户方法
    ResultDTO<Boolean> addUsers(String token, AddUsersDTO addUsersDTO);
    // 数据统计与分析
    ResultDTO<List<AnalyzeDataDTO>> analyzeData(String token, String dateStart, String dateEnd);
}
