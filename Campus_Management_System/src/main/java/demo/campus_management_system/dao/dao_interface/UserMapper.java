package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.entity.VO.ClassroomVO;
import demo.campus_management_system.entity.VO.ReservationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户相关数据访问层
 */
@Mapper
public interface UserMapper extends BaseMapper<Users> {
    
    /**
     * 查询可用教室(分页)
     */
    Page<ClassroomVO> selectAvailableClassrooms(
            IPage<ClassroomVO> page,
            @Param("buildingId") String buildingId,
            @Param("capacity") Integer capacity, 
            @Param("roomType") String roomType,
            @Param("roomStatus") String roomStatus,
            @Param("date") String date,
            @Param("period") String period
    );
    
    /**
     * 插入预约申请
     */
    int insertClassroomApply(
            @Param("applyId") String applyId,
            @Param("userAccount") String userAccount,
            @Param("userName") String userName,
            @Param("collegeId") String collegeId,
            @Param("phone") String phone,
            @Param("purpose") String purpose,
            @Param("personCount") Integer personCount,
            @Param("date") String date,
            @Param("week") Integer week,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("period") String period,
            @Param("roomNum") String roomNum,
            @Param("secAccount") String secAccount
    );
    
    /**
     * 插入教室资源记录
     */
    int insertClassroomResource(
            @Param("resId") String resId,
            @Param("date") String date,
            @Param("week") String week,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("period") String period,
            @Param("roomNum") String roomNum,
            @Param("applyId") String applyId
    );
    
    /**
     * 查询用户预约记录
     */
    Page<ReservationVO> selectUserReservations(
            IPage<ReservationVO> page,
            @Param("userAccount") String userAccount
    );
    
    /**
     * 统计用户本周预约数
     */
    Integer countWeekReservations(@Param("userAccount") String userAccount);
    
    /**
     * 统计用户待审核预约数
     */
    Integer countPendingReservations(@Param("userAccount") String userAccount);
    
    /**
     * 取消预约(更新user_cancel字段)
     */
    int cancelReservation(@Param("applyId") String applyId);
    
    /**
     * 检查预约是否可取消
     */
    boolean canCancelReservation(@Param("applyId") String applyId);
    
    /**
     * 根据教室获取对应的教秘账号
     */
    String getSecAccountByRoomNum(@Param("roomNum") String roomNum);
    
    /**
     * 检查用户账号是否存在于users表中
     */
    boolean checkUserExists(@Param("userAccount") String userAccount);
}