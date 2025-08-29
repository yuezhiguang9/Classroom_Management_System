package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.TeachSecretaryMapper;
import demo.campus_management_system.entity.Teach_Secretary;
import demo.campus_management_system.entity.DTO.ClassroomUsageQueryDTO;
import demo.campus_management_system.entity.DTO.UpdateStatusDTO;
import demo.campus_management_system.entity.VO.ClassroomUsageVO;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.service.service_interface.TeachSecretaryService;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教学秘书服务实现类
 */
@Service
public class TeachSecretaryServiceImpl extends ServiceImpl<TeachSecretaryMapper, Teach_Secretary> implements TeachSecretaryService {

    @Autowired
    private TeachSecretaryMapper teachSecretaryMapper;

    @Override
    public ResultDTO<List<ListLogsVO>> listLogs(String token, String applyStatus, String buildingId,
                                                String userName, String dateStart, String dateEnd,
                                                Integer page, Integer size) {
        try {
            // 验证token并获取教秘账号
            String actualToken = JwtUtil.extractToken(token);
            String secAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(secAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }

            // 创建分页对象
            Page<ListLogsVO> pageObj = new Page<>(page, size);

            // 查询教秘负责的申请记录
            Page<ListLogsVO> result = teachSecretaryMapper.selectSecLogs(
                    pageObj, secAccount, applyStatus, buildingId, userName, dateStart, dateEnd
            );

            // 获取统计数据
            Integer todayPending = teachSecretaryMapper.countTodayPendingBySec(secAccount);
            Integer weekApproved = teachSecretaryMapper.countWeekApprovedBySec(secAccount);
            Integer weekRejected = teachSecretaryMapper.countWeekRejectedBySec(secAccount);

            List<ListLogsVO> records = result.getRecords();

            // 在第一条记录中设置统计信息（简化处理）
            if (!records.isEmpty()) {
                // 可以扩展VO或使用其他方式返回统计数据
            }

            return ResultDTO.success(records, "查询成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

    @Override
    public ResultDTO<ListLogsVO> viewLogs(String token, String applyId) {
        try {
            // 验证token
            String actualToken = JwtUtil.extractToken(token);
            String secAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(secAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }

            // 查询申请详情
            ListLogsVO logDetail = teachSecretaryMapper.selectSecLogDetail(applyId);

            if (logDetail == null) {
                return ResultDTO.fail(404, "申请记录不存在或已被用户取消");
            }

            return ResultDTO.success(logDetail, "查询成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

    @Override
    public ResultDTO<Boolean> updateStatus(String token, UpdateStatusDTO updateDTO) {
        try {
            // 验证token
            String actualToken = JwtUtil.extractToken(token);
            String secAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(secAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }

            // 参数校验
            if ("已拒绝".equals(updateDTO.getApply_status())) {
                if (updateDTO.getReject_reason() == null || updateDTO.getReject_reason().trim().isEmpty()) {
                    return ResultDTO.fail(400, "驳回时必须填写驳回原因");
                }
            }

            // 更新申请状态
            int result = teachSecretaryMapper.updateApplyStatus(
                    updateDTO.getApply_id(),
                    updateDTO.getApply_status(),
                    updateDTO.getReject_reason()
            );

            if (result > 0) {
                return ResultDTO.success(true, "审核状态更新成功");
            } else {
                return ResultDTO.fail(400, "更新失败，请检查申请编号是否正确");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

    @Override
    public ResultDTO<List<ClassroomUsageVO>> classroomUsage(String token, ClassroomUsageQueryDTO queryDTO) {
        try {
            // 验证token
            String actualToken = JwtUtil.extractToken(token);
            String secAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(secAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }

            // 获取统计数据
            ClassroomUsageVO statistics = teachSecretaryMapper.selectUsageStatistics(
                queryDTO.getDate_start(),
                queryDTO.getDate_end(),
                queryDTO.getBuilding_id(),
                queryDTO.getRoom_type()
            );

            // 创建分页对象
            Page<ClassroomUsageVO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());

            // 查询详细使用率列表
            Page<ClassroomUsageVO> result = teachSecretaryMapper.selectUsageDetails(
                    page,
                    queryDTO.getTime_range(),
                    queryDTO.getDate_start(),
                    queryDTO.getDate_end(),
                    queryDTO.getBuilding_id(),
                    queryDTO.getRoom_type()
            );

            List<ClassroomUsageVO> records = result.getRecords();

            // 将统计数据添加到第一条记录中（简化处理）
            if (statistics != null && !records.isEmpty()) {
                ClassroomUsageVO firstRecord = records.get(0);
                firstRecord.setAvgUsageRate(statistics.getAvgUsageRate());
                firstRecord.setMostUsed(statistics.getMostUsed());
                firstRecord.setMostUsageCount(statistics.getMostUsageCount());
                firstRecord.setLeastUsed(statistics.getLeastUsed());
                firstRecord.setLeastUsageCount(statistics.getLeastUsageCount());
                firstRecord.setTotalUsage(statistics.getTotalUsage());
            }

            return ResultDTO.success(records, "查询成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
}