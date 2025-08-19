package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.entity.Teach_Secretary;
import demo.campus_management_system.entity.Classroom_manager;
import demo.campus_management_system.entity.Super_admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 认证相关数据访问层
 */
@Mapper
public interface AuthMapper extends BaseMapper<Users> {
    
    /**
     * 根据账号查询普通用户
     */
    Users selectUserByAccount(@Param("account") String account);
    
    /**
     * 根据账号查询教学秘书
     */
    Teach_Secretary selectTeachSecByAccount(@Param("account") String account);
    
    /**
     * 根据账号查询教室管理员
     */
    Classroom_manager selectClassroomManagerByAccount(@Param("account") String account);
    
    /**
     * 根据账号查询超级管理员
     */
    Super_admin selectSuperAdminByAccount(@Param("account") String account);
    
    /**
     * 插入登录日志
     */
    int insertLoginLog(@Param("userAccount") String userAccount, 
                      @Param("loginId") String loginId);
}