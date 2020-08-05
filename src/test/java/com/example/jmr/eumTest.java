package com.example.jmr;

import com.example.jmr.entity.EnumWarehouse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class eumTest {

    @Test
    public void eum(){
        String s = "男";
        EnumWarehouse.J_SEX j = EnumWarehouse.J_SEX.valueOf("男");
        System.out.print(j);
    }

}
