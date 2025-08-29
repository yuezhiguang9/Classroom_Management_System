package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.*;
import demo.campus_management_system.service.impl.SuperAdminImpl;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class SuperAdmin {
    @Autowired
    private SuperAdminImpl superAdminImpl;

    
}
