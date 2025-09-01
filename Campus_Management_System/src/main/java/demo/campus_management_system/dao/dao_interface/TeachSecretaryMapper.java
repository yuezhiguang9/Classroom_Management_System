package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.Teach_Secretary;
import demo.campus_management_system.entity.VO.ClassroomUsageStatsVO;
import demo.campus_management_system.entity.VO.ClassroomUsageVO;
import demo.campus_management_system.entity.VO.ListLogsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 教学秘书数据访问层
 */
@Mapper
public interface TeachSecretaryMapper extends BaseMapper<Teach_Secretary> {
    
    /**
     * 教秘查看审核工作台(分页)
     */
    Page<ListLogsVO> selectSecLogs(
            Page<ListLogsVO> page,
            @Param("secAccount") String secAccount,
            @Param("applyStatus") String applyStatus,
            @Param("buildingId") String buildingId,
            @Param("userName") String userName,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
    
    /**
     * 教秘统计数据
     */
    Integer countTodayPendingBySec(@Param("secAccount") String secAccount);
    
    Integer countWeekApprovedBySec(@Param("secAccount") String secAccount);
    
    Integer countWeekRejectedBySec(@Param("secAccount") String secAccount);
    
    /**
     * 根据申请ID查询详情(过滤用户取消的预约)
     */
    ListLogsVO selectSecLogDetail(@Param("applyId") String applyId);
    
    /**
     * 更新申请审核状态
     */
    int updateApplyStatus(
            @Param("applyId") String applyId,
            @Param("applyStatus") String applyStatus,
            @Param("rejectReason") String rejectReason
    );
    
    /**
     * 查询教室使用率统计数据
     */
    ClassroomUsageVO selectUsageStatistics(
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd,
            @Param("buildingId") String buildingId,
            @Param("roomType") String roomType
    );
    
    /**
     * 查询教室使用率详细列表(分页)
     */
    Page<ClassroomUsageVO> selectUsageDetails(
            Page<ClassroomUsageVO> page,
            @Param("timeRange") String timeRange,
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd,
            @Param("buildingId") String buildingId,
            @Param("roomType") String roomType
    );

    ClassroomUsageStatsVO getClassroomUsageStats();
}