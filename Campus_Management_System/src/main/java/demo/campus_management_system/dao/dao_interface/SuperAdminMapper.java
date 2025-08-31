package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.DTO.AddUsersDTO;
import demo.campus_management_system.entity.DTO.AnalyzeDataDTO;
import demo.campus_management_system.entity.DTO.UpdateUsersDTO;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.BuildingUsageVO;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.entity.VO.UserListVO;
import demo.campus_management_system.entity.VO.RoomTypeUsageVO;
import demo.campus_management_system.entity.VO.RoomUsageVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SuperAdminMapper extends BaseMapper<Super_admin> {
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
            @Param("date_end") String date_end);

    // 编辑用户：测试测试RequestBody
    Boolean selectSuperAdmin(String account, String password);

    Boolean editUsers(UpdateUsersDTO updateUsersDTO);

    Boolean editTeach(UpdateUsersDTO updateUsersDTO);

    Boolean editClassMgr(UpdateUsersDTO updateUsersDTO);
    // ============ 用户管理相关 ============

    /**
     * 查询用户列表(分页)
     */
    Page<UserListVO> selectUserList(
            Page<UserListVO> page,
            @Param("userType") String userType,
            @Param("collegeId") String collegeId,
            @Param("userName") String userName,
            @Param("userId") String userId);


    // 删除用户
    // 删除普通用户(users表) - 添加SQL注解
    @Delete("DELETE FROM users WHERE user_account = #{account}")
    int deleteUser(@Param("account") String account);

    // 删除教学秘书(teach_secretary表) - 添加SQL注解
    @Delete("DELETE FROM teach_secretary WHERE sec_account = #{account}")
    int deleteTeachSec(@Param("account") String account);

    // 删除教室管理员(classroom_manager表) - 添加SQL注解
    @Delete("DELETE FROM classroom_manager WHERE mgr_account = #{account}")
    int deleteClassMgr(@Param("account") String account);

    // 添加用户
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

    // 数据统计与分析
    // 1. 统计总用户数、教秘数、教室管理员数
    Integer countTotalUsers();

    Integer countTotalTeachSecs();

    Integer countTotalClassroomMgrs();

    // 2. 统计活跃用户数（近30天登录）
    Integer countActiveUsers();

    // 3. 统计总预约数
    Integer countTotalApplies(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);


    //4. 统计本月于上月预约总数的差
    Integer calculateMomApptComparison();


    // 5. 统计经常使用的教室（前五）
    List<RoomUsageVO> countFrequentlyUsedRooms(
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd);

    // 6. 统计经常使用的教室类型（前五）
    List<RoomTypeUsageVO> countFrequentlyUsedRoomTypes(
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd);

    // 7. 统计当月每栋楼的预约数
    List<BuildingUsageVO> countMonthlyBuildingApplies();

}
