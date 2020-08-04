package com.example.jmr;

import com.example.jmr.entity.EumnWarehouse;
import com.example.jmr.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class eumTest {

    @Test
    public void eum(){
        String s = "男";
        EumnWarehouse.J_SEX j = EumnWarehouse.J_SEX.valueOf("男");
        System.out.print(j);
    }

}
