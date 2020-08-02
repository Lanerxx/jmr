package com.example.jmr;

import com.example.jmr.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class eumTest {

    @Test
    public void eum(){
        String s = "男";
        Job.J_SEX j = Job.J_SEX.valueOf("男");
        System.out.print(j);
    }

}
