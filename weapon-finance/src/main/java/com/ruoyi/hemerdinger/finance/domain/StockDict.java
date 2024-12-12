package com.ruoyi.hemerdinger.finance.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 股票字典对象 stock_dict
 *
 * @author lijingxiang
 * @date 2024-12-10
 */
@ApiModel(value = "ClassName", description = "股票字典对象")
public class StockDict extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称", example = "1")
    private String name;

    /** 代码 */
    @Excel(name = "代码")
    @ApiModelProperty(value = "代码", example = "1")
    private String code;

    /** 交易所 */
    @Excel(name = "交易所")
    @ApiModelProperty(value = "交易所", example = "1")
    private String bourse;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setBourse(String bourse)
    {
        this.bourse = bourse;
    }

    public String getBourse()
    {
        return bourse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("code", getCode())
            .append("bourse", getBourse())
            .toString();
    }
}
