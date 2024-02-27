package com.ruoyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@MapperScan("com.ruoyi.mapper")
public class ProductApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProductApp.class,args);
    }
}
