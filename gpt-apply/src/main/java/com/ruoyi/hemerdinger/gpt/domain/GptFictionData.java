package com.ruoyi.hemerdinger.gpt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小说数据对象 gpt_fiction_data
 *
 * @author lijingxiang
 * @date 2024-05-27
 */
@ApiModel(value = "ClassName", description = "小说数据对象")
public class GptFictionData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 内容JSON */
    @Excel(name = "内容JSON")
    @ApiModelProperty(value = "内容JSON", example = "1")
    private String content;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 上传id */
    @Excel(name = "上传id")
    @ApiModelProperty(value = "上传id", example = "1")
    private String fileId;

    /** 小说id */
    @Excel(name = "小说id")
    @ApiModelProperty(value = "小说id", example = "1")
    private Long fictionId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getFileId()
    {
        return fileId;
    }
    public void setFictionId(Long fictionId)
    {
        this.fictionId = fictionId;
    }

    public Long getFictionId()
    {
        return fictionId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("content", getContent())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("fileId", getFileId())
            .append("fictionId", getFictionId())
            .toString();
    }
}
