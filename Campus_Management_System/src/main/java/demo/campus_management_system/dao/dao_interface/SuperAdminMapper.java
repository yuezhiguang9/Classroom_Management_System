package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.entity.VO.UserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SuperAdminMapper extends BaseMapper<Super_admin> {


    // ============ 用户管理相关 ============
    
    /**
     * 查询用户列表(分页)
     */
    Page<UserListVO> selectUserList(
            Page<UserListVO> page,
            @Param("userType") String userType,
            @Param("collegeId") String collegeId,
            @Param("userName") String userName,
            @Param("userId") String userId
    );
    
    /**
     * 统计总用户数
     */
    Integer countTotalUsers();
    
    /**
     * 统计活跃用户数(近30天有登录记录)
     */
    Integer countActiveUsers();
    

}
