package com.ruoyi.hemerdinger.gpt.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.gpt.mapper.GptFictionParagraphMapper;
import com.ruoyi.hemerdinger.gpt.domain.GptFictionParagraph;
import com.ruoyi.hemerdinger.gpt.service.IGptFictionParagraphService;

/**
 * 段落Service业务层处理
 * 
 * @author lijingxiang
 * @date 2024-05-27
 */
@Service
public class GptFictionParagraphServiceImpl implements IGptFictionParagraphService 
{
    @Autowired
    private GptFictionParagraphMapper gptFictionParagraphMapper;

    /**
     * 查询段落
     * 
     * @param id 段落主键
     * @return 段落
     */
    @Override
    public GptFictionParagraph selectGptFictionParagraphById(Long id)
    {
        return gptFictionParagraphMapper.selectGptFictionParagraphById(id);
    }

    /**
     * 查询段落列表
     * 
     * @param gptFictionParagraph 段落
     * @return 段落
     */
    @Override
    public List<GptFictionParagraph> selectGptFictionParagraphList(GptFictionParagraph gptFictionParagraph)
    {
        return gptFictionParagraphMapper.selectGptFictionParagraphList(gptFictionParagraph);
    }

    /**
     * 新增段落
     * 
     * @param gptFictionParagraph 段落
     * @return 结果
     */
    @Override
    public int insertGptFictionParagraph(GptFictionParagraph gptFictionParagraph)
    {
        gptFictionParagraph.setCreateTime(DateUtils.getNowDate());
        return gptFictionParagraphMapper.insertGptFictionParagraph(gptFictionParagraph);
    }

    /**
     * 修改段落
     * 
     * @param gptFictionParagraph 段落
     * @return 结果
     */
    @Override
    public int updateGptFictionParagraph(GptFictionParagraph gptFictionParagraph)
    {
        gptFictionParagraph.setUpdateTime(DateUtils.getNowDate());
        return gptFictionParagraphMapper.updateGptFictionParagraph(gptFictionParagraph);
    }

    /**
     * 批量删除段落
     * 
     * @param ids 需要删除的段落主键
     * @return 结果
     */
    @Override
    public int deleteGptFictionParagraphByIds(Long[] ids)
    {
        return gptFictionParagraphMapper.deleteGptFictionParagraphByIds(ids);
    }

    /**
     * 删除段落信息
     * 
     * @param id 段落主键
     * @return 结果
     */
    @Override
    public int deleteGptFictionParagraphById(Long id)
    {
        return gptFictionParagraphMapper.deleteGptFictionParagraphById(id);
    }
}
