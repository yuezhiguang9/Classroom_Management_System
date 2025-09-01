package demo.campus_management_system.service.service_interface;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import demo.campus_management_system.entity.Teach_Secretary;
import demo.campus_management_system.entity.DTO.ClassroomUsageQueryDTO;
import demo.campus_management_system.entity.DTO.UpdateStatusDTO;
import demo.campus_management_system.entity.VO.CalculateClassroomMetricsVo;
import demo.campus_management_system.entity.VO.ClassroomUsageStatsVO;
import demo.campus_management_system.entity.VO.ClassroomUsageVO;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.entity.DTO.ResultDTO;

import java.util.List;

/**
 * 教学秘书服务接口
 */
public interface TeachSecretaryService extends IService<Teach_Secretary> {

    /**
     * 教秘审核工作台
     */
    ResultDTO<Page<ListLogsVO>> listLogs(
            String token,
            String applyStatus,
            String buildingId,
            String userName,
            String dateStart,
            String dateEnd,
            Integer page,
            Integer size
    );

    /**
     * 审核详情查看
     */
    ResultDTO<ListLogsVO> viewLogs(String token, String applyId);

    /**
     * 审核状态更新
     */
    ResultDTO<Boolean> updateStatus(String token, UpdateStatusDTO updateDTO);

    /**
     * 查看教室使用率页面
     */
    ResultDTO<Page<ClassroomUsageVO>> classroomUsage(String token, ClassroomUsageQueryDTO queryDTO);

    /**
     * 获取教室使用率统计数据
     */
    ResultDTO<ClassroomUsageStatsVO> getClassroomUsageStats(String token);

    /**
     * 教室使用统计
     */
    ResultDTO<CalculateClassroomMetricsVo> calculateClassroomMetrics(String token);
}