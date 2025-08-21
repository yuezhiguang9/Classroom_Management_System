package demo.campus_management_system.service.service_interface;

import demo.campus_management_system.entity.DTO.ClassroomAuditQueryDTO;
import demo.campus_management_system.entity.DTO.ClassroomAuditResultDTO;
import demo.campus_management_system.entity.DTO.ResultDTO;

public interface ClassroomManagerAuditService {
        ResultDTO<ClassroomAuditResultDTO> getClassroomAuditInfo(ClassroomAuditQueryDTO queryDTO);
}