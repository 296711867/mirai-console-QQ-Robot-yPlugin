package yplugin.vo;

import lombok.Data;

/**
 * 饥荒查房响应封装类
 * @author pan
 */
@Data
public class DSTserver {

    public String[] List;

    public void setList(String[] List)
    {
        this.List = List;
    }

    public String[] getList()
    {
        return List;
    }




}
