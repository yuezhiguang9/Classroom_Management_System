package demo.campus_management_system.dao.dao_interface;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.DTO.AddUsersDTO;
import demo.campus_management_system.entity.DTO.AnalyzeDataDTO;
import demo.campus_management_system.entity.DTO.UpdateUsersDTO;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.ListLogsVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SuperAdminMapper extends BaseMapper<Super_admin> {
    //全校今日待审核数
    Integer selectTodayPending();

    //全校本周通过数
    Integer selectWeekApproved(LocalDateTime StartTime, LocalDateTime EndTime);

    //全校本周驳回数
    Integer selectWeekRejected(LocalDateTime StartTime, LocalDateTime EndTime);

    //需要分页的信息
    Page<ListLogsVO> selectRecordsByPage(
            Page<ListLogsVO> page,
            @Param("apply_status") String apply_status,
            @Param("college_id") String college_id,
            @Param("building_id") String building_id,
            @Param("user_name") String user_name,
            @Param("date_start") String date_start,
            @Param("date_end") String date_end
    );


    //编辑用户：测试测试RequestBody
    Boolean selectSuperAdmin(String account, String password);

    Boolean editUsers(UpdateUsersDTO updateUsersDTO);

    Boolean editTeach(UpdateUsersDTO updateUsersDTO);

    Boolean editClassMgr(UpdateUsersDTO updateUsersDTO);


    //删除用户
    // 删除普通用户(users表) - 添加SQL注解
    @Delete("DELETE FROM users WHERE user_account = #{account}")
    int deleteUser(@Param("account") String account);

    // 删除教学秘书(teach_secretary表) - 添加SQL注解
    @Delete("DELETE FROM teach_secretary WHERE sec_account = #{account}")
    int deleteTeachSec(@Param("account") String account);

    // 删除教室管理员(classroom_manager表) - 添加SQL注解
    @Delete("DELETE FROM classroom_manager WHERE mgr_account = #{account}")
    int deleteClassMgr(@Param("account") String account);


    //添加用户
    // 新增普通用户（users表）
    @Insert("INSERT INTO users (user_account, user_password, user_name, user_phone, college_id) " +
            "VALUES (#{dto.account}, #{dto.password}, #{dto.name}, #{dto.phone}, #{dto.college_id})")
    int addUser(@Param("dto") AddUsersDTO dto);

    // 新增教学秘书（teach_secretary表）
    @Insert("INSERT INTO teach_secretary (sec_account, sec_password, sec_name, office_phone, college_id) " +
            "VALUES (#{dto.account}, #{dto.password}, #{dto.name}, #{dto.phone}, #{dto.college_id})")
    int addTeachSec(@Param("dto") AddUsersDTO dto);

    // 新增教室管理员（classroom_manager表）
    @Insert("INSERT INTO classroom_manager (mgr_account, mgr_password, mgr_phone, building_id) " +
            "VALUES (#{dto.account}, #{dto.password}, #{dto.phone}, #{dto.building_id})")
    int addClassMgr(@Param("dto") AddUsersDTO dto);


    //数据统计与分析

    // 1. 统计总用户数
    Integer countTotalUsers();
    // 2. 统计活跃用户数（近30天登录）
    Integer countActiveUsers();
    // 3. 统计总预约数
    Integer countTotalApplies(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);
    // 4. 计算预约成功率
    String calculateApprovalRate(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);
    // 5. 计算教室使用率
    String calculateClassroomUsageRate(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd,
                                       @Param("buildingId") String buildingId, @Param("roomType") String roomType);
    // 6. 统计教室类型分布
    IPage<AnalyzeDataDTO> countRoomTypeDistribution(Page<AnalyzeDataDTO> page,
                                                    @Param("collegeId") String collegeId, @Param("buildingId") String buildingId);
    // 7. 分析高峰时段（按节次统计使用次数）
    IPage<AnalyzeDataDTO> analyzePeakPeriods(Page<AnalyzeDataDTO> page,
                                             @Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd,
                                             @Param("buildingId") String buildingId, @Param("roomType") String roomType);
    // 8. 查询预约详情列表（用于success类型报表）
    IPage<AnalyzeDataDTO> queryApplyDetails(Page<AnalyzeDataDTO> page,
                                            @Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd,
                                            @Param("collegeId") String collegeId, @Param("status") String status);


}
