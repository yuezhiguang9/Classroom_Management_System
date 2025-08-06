package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.entity.VO.UserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface SuperAdminMapper extends BaseMapper<Super_admin> {
    
    // ============ 日志相关 ============
    
    /**
     * 全校今日待审核数
     */
    Integer selectTodayPending();

    /**
     * 全校本周通过数
     */
    Integer selectWeekApproved(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 全校本周驳回数
     */
    Integer selectWeekRejected(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 需要分页的信息
     */
    Page<ListLogsVO> selectRecordsByPage(
            Page<ListLogsVO> page,
            @Param("apply_status") String apply_status,
            @Param("college_id") String college_id,
            @Param("building_id") String building_id,
            @Param("user_name") String user_name,
            @Param("date_start") String date_start,
            @Param("date_end") String date_end
    );
    
    /**
     * 根据申请ID查询日志详情
     */
    ListLogsVO selectLogDetailByApplyId(@Param("applyId") String applyId);

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
    
    /**
     * 更新普通用户信息
     */
    int updateUserInfo(
            @Param("account") String account,
            @Param("name") String name,
            @Param("password") String password,
            @Param("phone") String phone,
            @Param("collegeId") String collegeId
    );
    
    /**
     * 更新教学秘书信息
     */
    int updateTeachSecInfo(
            @Param("account") String account,
            @Param("name") String name,
            @Param("password") String password,
            @Param("phone") String phone,
            @Param("collegeId") String collegeId
    );
    
    /**
     * 更新教室管理员信息
     */
    int updateClassroomManagerInfo(
            @Param("account") String account,
            @Param("password") String password,
            @Param("phone") String phone,
            @Param("buildingId") String buildingId
    );
    
    /**
     * 更新超级管理员信息
     */
    int updateSuperAdminInfo(
            @Param("account") String account,
            @Param("password") String password
    );
    
    /**
     * 删除普通用户
     */
    int deleteUser(@Param("account") String account);
    
    /**
     * 删除教学秘书
     */
    int deleteTeachSec(@Param("account") String account);
    
    /**
     * 删除教室管理员
     */
    int deleteClassroomManager(@Param("account") String account);
    
    /**
     * 删除超级管理员
     */
    int deleteSuperAdmin(@Param("account") String account);
    
    /**
     * 删除用户相关的申请记录
     */
    int deleteUserApplies(@Param("account") String account);
    
    /**
     * 删除用户相关的资源记录
     */
    int deleteUserResources(@Param("account") String account);
    
    /**
     * 插入普通用户
     */
    int insertUser(
            @Param("account") String account,
            @Param("name") String name,
            @Param("password") String password,
            @Param("phone") String phone,
            @Param("collegeId") String collegeId
    );
    
    /**
     * 插入教学秘书
     */
    int insertTeachSec(
            @Param("account") String account,
            @Param("name") String name,
            @Param("password") String password,
            @Param("phone") String phone,
            @Param("collegeId") String collegeId
    );
    
    /**
     * 插入教室管理员
     */
    int insertClassroomManager(
            @Param("account") String account,
            @Param("password") String password,
            @Param("phone") String phone,
            @Param("buildingId") String buildingId
    );
    
    /**
     * 插入超级管理员
     */
    int insertSuperAdmin(
            @Param("account") String account,
            @Param("password") String password
    );

    // ============ 数据统计相关 ============
    
    /**
     * 统计总申请数
     */
    Integer countTotalApplies(
            @Param("timeRange") String timeRange,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
    
    /**
     * 计算教室使用率
     */
    String calculateUsageRate(
            @Param("timeRange") String timeRange,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
    
    /**
     * 计算审批通过率
     */
    String calculateApprovalRate(
            @Param("timeRange") String timeRange,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
    
    /**
     * 获取使用率详情
     */
    List<Map<String, Object>> getUsageDetails(
            @Param("timeRange") String timeRange,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd,
            @Param("collegeId") String collegeId,
            @Param("buildingId") String buildingId,
            @Param("roomType") String roomType
    );
    
    /**
     * 获取高峰时段分析
     */
    List<Map<String, Object>> getPeakTimeAnalysis(
            @Param("timeRange") String timeRange,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
    
    /**
     * 获取用户活跃度统计
     */
    List<Map<String, Object>> getUserActivityStats(
            @Param("timeRange") String timeRange,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
    
    /**
     * 获取审批详情
     */
    List<Map<String, Object>> getApprovalDetails(
            @Param("timeRange") String timeRange,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
}
