package demo.campus_management_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("demo.campus_management_system.dao")
public class CampusManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusManagementSystemApplication.class, args);
    }

}
