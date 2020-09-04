package com.example.jmr.entity;

public class EnumWarehouse {
    public enum S_SEX {
        男, 女
    }
    public enum J_SEX{
        无要求, 男, 女
    }

    public enum C_LEVEL{
        _985, _211, 一批本科, 二批本科, 专科, 高职
    }
    public enum E_HISTORY{
        博士, 硕士, 本科, 专科
    }
    public enum S_RANGE{
        四k以下, 四至六k, 六至八k, 八至十k, 十K以上
    }
    public enum E_CITY{
        烟台, 其他
    }
    public enum IF_WORK{
        未就业, 已就业
    }
    public enum F_LANGUAGE{
        NONE, JN3, JN2, CET6, CET4
    }
}
