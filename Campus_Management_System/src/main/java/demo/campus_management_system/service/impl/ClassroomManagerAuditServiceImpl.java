package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.dao.dao_interface.ClassroomManagerAuditMapper;
import demo.campus_management_system.entity.DTO.ClassroomAuditQueryDTO;
import demo.campus_management_system.entity.DTO.ClassroomAuditResultDTO;
import demo.campus_management_system.entity.DTO.ResultDTO;
import demo.campus_management_system.entity.VO.ClassroomAuditRecordVO;
import demo.campus_management_system.service.service_interface.ClassroomManagerAuditService;
import demo.campus_management_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomManagerAuditServiceImpl implements ClassroomManagerAuditService {

    @Autowired
    private ClassroomManagerAuditMapper classroomManagerAuditNewMapper;

    @Override
    public ResultDTO<ClassroomAuditResultDTO> getClassroomAuditInfo(ClassroomAuditQueryDTO queryDTO) {
        try {
            // 1. 从token解析账号密码并验证身份
            String account = JwtUtil.getUserAccountToken(queryDTO.getToken());
            String password = JwtUtil.getUserPasswordToken(queryDTO.getToken());

            // 验证是否为有效的教室管理员
            Boolean isManager = classroomManagerAuditNewMapper.verifyClassroomManager(account, password);
            if (isManager == null || !isManager) {
                return ResultDTO.fail(401, "身份验证失败，无权访问");
            }

            // 2. 设置查询的管理员账号（优先使用token解析的账号）
            queryDTO.setMgr_account(account);

            // 3. 获取管辖教室总数
            Integer managedRooms = classroomManagerAuditNewMapper.countManagedRooms(account);

            // 4. 统计符合条件的申请总数
            Long totalApplyinfo = classroomManagerAuditNewMapper.countQualifiedApplies(queryDTO);

            // 5. 创建分页对象
            Page<ClassroomAuditRecordVO> pageObj = new Page<>(queryDTO.getPage(), queryDTO.getSize());

            // 6. 查询审核记录
            Page<ClassroomAuditRecordVO> auditRecords =
                    classroomManagerAuditNewMapper.selectAuditRecords(pageObj, queryDTO);

            // 7. 构建返回结果
            ClassroomAuditResultDTO resultDTO = new ClassroomAuditResultDTO();
            resultDTO.setManaged_rooms(managedRooms);
            resultDTO.setTotal_applyinfo(totalApplyinfo);
            resultDTO.setPage(queryDTO.getPage());
            resultDTO.setSize(queryDTO.getSize());
            resultDTO.setRecords(auditRecords.getRecords());

            return ResultDTO.success(resultDTO, "查询成功");

        } catch (Exception e) {
            return ResultDTO.fail(500, "服务器错误：" + e.getMessage());
        }
    }
}