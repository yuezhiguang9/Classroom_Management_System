package demo.campus_management_system.dao.dao_interface;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.DTO.UpdateUsersDTO;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.ListLogsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

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


    //测试测试RequestBody
    Boolean selectSuperAdmin(String account, String password);

    Boolean editUsers(UpdateUsersDTO updateUsersDTO);

    Boolean editTeach(UpdateUsersDTO updateUsersDTO);

    Boolean editClassMgr(UpdateUsersDTO updateUsersDTO);


}
