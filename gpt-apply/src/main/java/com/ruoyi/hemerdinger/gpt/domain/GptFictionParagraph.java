package com.ruoyi.hemerdinger.gpt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 段落对象 gpt_fiction_paragraph
 *
 * @author lijingxiang
 * @date 2024-05-27
 */
@ApiModel(value = "ClassName", description = "段落对象")
public class GptFictionParagraph extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 内容 */
    @Excel(name = "内容")
    @ApiModelProperty(value = "内容", example = "1")
    private String content;

    /** 选项JSON包含选项对应的全局框架和章节框架,以及下一段落ID */
    @Excel(name = "选项JSON包含选项对应的全局框架和章节框架,以及下一段落ID")
    @ApiModelProperty(value = "选项JSON包含选项对应的全局框架和章节框架,以及下一段落ID", example = "1")
    private String optionsJson;

    /** 全局框架ID */
    @Excel(name = "全局框架ID")
    @ApiModelProperty(value = "全局框架ID", example = "1")
    private Long fictionFrameId;

    /** 卷框架ID */
    @Excel(name = "卷框架ID")
    @ApiModelProperty(value = "卷框架ID", example = "1")
    private Long volumeFrameId;

    /** 小说ID */
    @Excel(name = "小说ID")
    @ApiModelProperty(value = "小说ID", example = "1")
    private Long fictionId;

    /** 序号 */
    @Excel(name = "序号")
    @ApiModelProperty(value = "序号", example = "1")
    private Long serial;

    /** 主角状态ID */
    @Excel(name = "主角状态ID")
    @ApiModelProperty(value = "主角状态ID", example = "1")
    private Long roleStatusId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setOptionsJson(String optionsJson)
    {
        this.optionsJson = optionsJson;
    }

    public String getOptionsJson()
    {
        return optionsJson;
    }
    public void setFictionFrameId(Long fictionFrameId)
    {
        this.fictionFrameId = fictionFrameId;
    }

    public Long getFictionFrameId()
    {
        return fictionFrameId;
    }
    public void setVolumeFrameId(Long volumeFrameId)
    {
        this.volumeFrameId = volumeFrameId;
    }

    public Long getVolumeFrameId()
    {
        return volumeFrameId;
    }
    public void setFictionId(Long fictionId)
    {
        this.fictionId = fictionId;
    }

    public Long getFictionId()
    {
        return fictionId;
    }
    public void setSerial(Long serial)
    {
        this.serial = serial;
    }

    public Long getSerial()
    {
        return serial;
    }
    public void setRoleStatusId(Long roleStatusId)
    {
        this.roleStatusId = roleStatusId;
    }

    public Long getRoleStatusId()
    {
        return roleStatusId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("content", getContent())
            .append("optionsJson", getOptionsJson())
            .append("fictionFrameId", getFictionFrameId())
            .append("volumeFrameId", getVolumeFrameId())
            .append("fictionId", getFictionId())
            .append("serial", getSerial())
            .append("roleStatusId", getRoleStatusId())
            .toString();
    }
}
