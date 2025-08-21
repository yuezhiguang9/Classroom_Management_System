package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.DTO.ClassroomAuditQueryDTO;
import demo.campus_management_system.entity.VO.ClassroomAuditRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassroomManagerAuditMapper {

        // 验证教室管理员身份
        Boolean verifyClassroomManager(@Param("account") String account, @Param("password") String password);

        // 统计管辖教室总数
        Integer countManagedRooms(@Param("mgrAccount") String mgrAccount);

        // 统计符合条件的申请总数（用户未取消且已批准）
        Long countQualifiedApplies(@Param("query") ClassroomAuditQueryDTO queryDTO);

        // 分页查询审核记录
        Page<ClassroomAuditRecordVO> selectAuditRecords(Page<ClassroomAuditRecordVO> page,
                                                        @Param("query") ClassroomAuditQueryDTO queryDTO);
}