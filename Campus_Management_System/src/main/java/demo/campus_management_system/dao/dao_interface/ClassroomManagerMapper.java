package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.Classroom_manager;
import demo.campus_management_system.entity.VO.ClassroomManageVO;
import demo.campus_management_system.entity.VO.ApplyInfoManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教室管理员数据访问层
 */
@Mapper
public interface ClassroomManagerMapper extends BaseMapper<Classroom_manager> {
    
    /**
     * 查询管辖教室列表(分页)
     */
    Page<ClassroomManageVO> selectManagedClassrooms(
            Page<ClassroomManageVO> page,
            @Param("mgrAccount") String mgrAccount,
            @Param("roomNum") String roomNum,
            @Param("capacity") Integer capacity,
            @Param("roomStatus") String roomStatus,
            @Param("roomType") String roomType
    );
    
    /**
     * 统计今日可用教室数
     */
    Integer countTodayAvailableRooms(@Param("mgrAccount") String mgrAccount);
    
    /**
     * 批量更新教室状态
     */
    int batchUpdateRoomStatus(
            @Param("roomNums") List<String> roomNums,
            @Param("roomStatus") String roomStatus,
            @Param("mgrAccount") String mgrAccount
    );
    
    /**
     * 单个更新教室状态
     */
    int updateSingleRoomStatus(
            @Param("roomNum") String roomNum,
            @Param("roomStatus") String roomStatus,
            @Param("mgrAccount") String mgrAccount
    );
    
    /**
     * 查询教室审核情况(分页)
     */
    Page<ApplyInfoManageVO> selectApplyInfoByManager(
            Page<ApplyInfoManageVO> page,
            @Param("mgrAccount") String mgrAccount,
            @Param("applyStatus") String applyStatus,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
    
    /**
     * 统计管辖教室总数
     */
    Integer countManagedRooms(@Param("mgrAccount") String mgrAccount);
    
    /**
     * 根据管理员账号获取管理的楼栋ID
     */
    String getBuildingIdByManager(@Param("mgrAccount") String mgrAccount);
}