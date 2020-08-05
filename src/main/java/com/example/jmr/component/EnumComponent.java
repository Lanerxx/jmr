package com.example.jmr.component;

import com.example.jmr.entity.EnumWarehouse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnumComponent {
    private final static int EUMN_S_SEX_LENGTH = EnumWarehouse.S_SEX.values().length;
    private final static int EUMN_J_SEX_LENGTH = EnumWarehouse.J_SEX.values().length;
    private final static int EUMN_LEVEL_LENGTH = EnumWarehouse.C_LEVEL.values().length;
    private final static int EUMN_HISTORY_LENGTH = EnumWarehouse.E_HISTORY.values().length;
    private final static int EUMN_LANGUAGE_LENGTH = EnumWarehouse.F_LANGUAGE.values().length;
    private final static int EUMN_RANG_LENGTH = EnumWarehouse.S_RANGE.values().length;
    private final static int EUMN_CITY_LENGTH = EnumWarehouse.E_CITY.values().length;

    //验证性别是否符合要求
    //性别与要求一致或性别要求为无
    public boolean verifySex(EnumWarehouse.S_SEX studentSex, EnumWarehouse.J_SEX jobSex){
        boolean qualified = false;
        if (jobSex == EnumWarehouse.J_SEX.无要求 ||
                (jobSex ==  EnumWarehouse.J_SEX.男 && studentSex == EnumWarehouse.S_SEX.男) ||
                (jobSex ==  EnumWarehouse.J_SEX.女 && studentSex == EnumWarehouse.S_SEX.女))
            qualified = true;
        return qualified;
    }

    //验证性学校等级是否符合要求
    public int getSchoolLevelIndex(EnumWarehouse.C_LEVEL level){
        EnumWarehouse.C_LEVEL[] levels = EnumWarehouse.C_LEVEL.values();
        int index = EUMN_LEVEL_LENGTH;
        for(int i = 0;i < EUMN_LEVEL_LENGTH ; i++) {
            if (levels[i] == level) {
                index = i;
                break;
            }
        }
        return index;
    }
    public boolean verifySchoolLevel(EnumWarehouse.C_LEVEL studentLevel, EnumWarehouse.C_LEVEL jobLevel){
        boolean qualified = false;
        if (getSchoolLevelIndex(studentLevel) <= getSchoolLevelIndex(jobLevel) ) qualified = true;
        return qualified;
    }

    //验证性学历等级是否符合要求
    public int getHistoryIndex(EnumWarehouse.E_HISTORY history){
        EnumWarehouse.E_HISTORY[] histories = EnumWarehouse.E_HISTORY.values();
        int index = EUMN_HISTORY_LENGTH;
        for(int i = 0;i < EUMN_HISTORY_LENGTH ; i++) {
            if (histories[i] == history){
                index = i;
                break;
            }
        }
        return index;
    }
    public boolean verifyHistory(EnumWarehouse.E_HISTORY studentHistory, EnumWarehouse.E_HISTORY jobHistory){
        boolean qualified = false;
        if (getHistoryIndex(studentHistory) <= getHistoryIndex(jobHistory) ) qualified = true;
        return qualified;
    }

    //验证性外语等级是否符合要求
    public boolean verifyLanguage(int studentLanguage, int jobLanguage){
        boolean q = false;
        int i;

        String studentLanguageString = Integer.toBinaryString(studentLanguage);
        int studentLanguageN = studentLanguageString.length();

        String jobLanguageString = Integer.toBinaryString(jobLanguage);
        int jobLanguageN = jobLanguageString.length();

        //若job要求为"无条件"，则jobLanguage = 16，转为二进制后，长度为5，则符合条件
        //若job外语要求转为二进制后的长度 > 学生外语水平转为二进制后的长度，则不符合条件
        if (jobLanguageN == 5){
            q = true;
        }
        else if (jobLanguageN <= studentLanguageN){
            q =true;
            int diff = studentLanguageN - jobLanguageN;
            for ( i = jobLanguageN - 1; i >= 0 ; i--){
                if (jobLanguageString.substring(i, i + 1 ).equals("1") && !studentLanguageString.substring(i+diff, i+1+diff ).equals("1")){
                    q = false;
                    break;
                }
            }
        }
        return q;
    }

    //验证性期望薪资是否符合要求
    public int getRangeIndex(EnumWarehouse.S_RANGE range){
        EnumWarehouse.S_RANGE[] ranges = EnumWarehouse.S_RANGE.values();
        int index = EUMN_RANG_LENGTH;
        for(int i = 0;i < EUMN_RANG_LENGTH ; i++) {
            if (ranges[i] == range){
                index = i;
                break;
            }
        }
        return index;
    }
    public boolean verifyRange(EnumWarehouse.S_RANGE studentRange, EnumWarehouse.S_RANGE jobRange){
        boolean qualified = false;
        if (getRangeIndex(studentRange) <= getRangeIndex(jobRange) ) {
            qualified = true;
        }
        return qualified;
    }

    //验证性就业意向地是否符合要求
    public boolean verifyCity(EnumWarehouse.E_CITY studentCity, EnumWarehouse.E_CITY jobCity){
        boolean qualified = false;
        if (studentCity == jobCity ) qualified = true;
        return qualified;
    }

}
