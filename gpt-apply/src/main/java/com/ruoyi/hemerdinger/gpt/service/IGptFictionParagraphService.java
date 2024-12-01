package com.ruoyi.hemerdinger.gpt.service;

import java.util.List;
import com.ruoyi.hemerdinger.gpt.domain.GptFictionParagraph;

/**
 * 段落Service接口
 * 
 * @author lijingxiang
 * @date 2024-05-27
 */
public interface IGptFictionParagraphService 
{
    /**
     * 查询段落
     * 
     * @param id 段落主键
     * @return 段落
     */
    public GptFictionParagraph selectGptFictionParagraphById(Long id);

    /**
     * 查询段落列表
     * 
     * @param gptFictionParagraph 段落
     * @return 段落集合
     */
    public List<GptFictionParagraph> selectGptFictionParagraphList(GptFictionParagraph gptFictionParagraph);

    /**
     * 新增段落
     * 
     * @param gptFictionParagraph 段落
     * @return 结果
     */
    public int insertGptFictionParagraph(GptFictionParagraph gptFictionParagraph);

    /**
     * 修改段落
     * 
     * @param gptFictionParagraph 段落
     * @return 结果
     */
    public int updateGptFictionParagraph(GptFictionParagraph gptFictionParagraph);

    /**
     * 批量删除段落
     * 
     * @param ids 需要删除的段落主键集合
     * @return 结果
     */
    public int deleteGptFictionParagraphByIds(Long[] ids);

    /**
     * 删除段落信息
     * 
     * @param id 段落主键
     * @return 结果
     */
    public int deleteGptFictionParagraphById(Long id);
}
