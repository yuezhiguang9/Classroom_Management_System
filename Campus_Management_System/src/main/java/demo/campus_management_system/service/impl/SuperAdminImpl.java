package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.SuperAdminMapper;
import demo.campus_management_system.entity.DTO.ListLogsDTO;
import demo.campus_management_system.entity.DTO.UserListQueryDTO;
import demo.campus_management_system.entity.DTO.UserOperationDTO;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.entity.VO.UserListVO;
import demo.campus_management_system.service.service_interface.SuperAdmin;
import demo.campus_management_system.util.DataUtils;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class SuperAdminImpl extends ServiceImpl<SuperAdminMapper, Super_admin> implements SuperAdmin {

    @Autowired
    SuperAdminMapper superAdminMapper;

    /**
     * 用户列表查询
     */
    public ResultDTO<List<UserListVO>> listUsers(UserListQueryDTO queryDTO) {
        try {
            // 创建分页对象
            Page<UserListVO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
            
            // 查询用户列表
            Page<UserListVO> result = superAdminMapper.selectUserList(
                page,
                queryDTO.getUser_type(),
                queryDTO.getCollege_id(),
                queryDTO.getUser_name(),
                queryDTO.getUser_id()
            );
            
            // 获取总用户数和活跃用户数
            Integer totalUsers = superAdminMapper.countTotalUsers();
            Integer activeUsers = superAdminMapper.countActiveUsers();
            
            // 构建返回数据
            List<UserListVO> records = result.getRecords();
            
            return ResultDTO.success(records, "查询成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

}
