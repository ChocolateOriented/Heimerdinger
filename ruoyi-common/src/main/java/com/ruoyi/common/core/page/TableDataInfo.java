package com.ruoyi.common.core.page;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author ruoyi
 */
public class TableDataInfo<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总记录数", example = "200")
    private long total;

    @ApiModelProperty(value = "列表数据")
    private T rows;

    @ApiModelProperty(value = "消息状态码", example = "200")
    private int code;

    @ApiModelProperty(value = "消息内容", example = "成功")
    private String msg;

    /**
     * 表格数据对象
     */
    public TableDataInfo()
    {
    }

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(T list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public T getRows()
    {
        return rows;
    }

    public void setRows(T rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
