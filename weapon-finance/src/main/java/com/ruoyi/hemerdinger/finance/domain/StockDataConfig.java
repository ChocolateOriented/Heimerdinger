package com.ruoyi.hemerdinger.finance.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 股票数据映射对象 stock_data_config
 *
 * @author lijignxiang
 * @date 2022-04-24
 */
@ApiModel(value = "ClassName", description = "股票数据映射对象")
public class StockDataConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 索引 */
    @Excel(name = "索引")
    @ApiModelProperty(value = "索引", example = "1")
    private Long index;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称", example = "1")
    private String name;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setIndex(Long index)
    {
        this.index = index;
    }

    public Long getIndex()
    {
        return index;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("index", getIndex())
            .append("name", getName())
            .toString();
    }
}
