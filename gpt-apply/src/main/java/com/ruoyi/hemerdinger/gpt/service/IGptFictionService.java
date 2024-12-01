package com.ruoyi.hemerdinger.gpt.service;

import java.util.List;
import com.ruoyi.hemerdinger.gpt.domain.GptFiction;

/**
 * 小说Service接口
 * 
 * @author lijingxiang
 * @date 2024-05-27
 */
public interface IGptFictionService 
{
    /**
     * 查询小说
     * 
     * @param id 小说主键
     * @return 小说
     */
    public GptFiction selectGptFictionById(Long id);

    /**
     * 查询小说列表
     * 
     * @param gptFiction 小说
     * @return 小说集合
     */
    public List<GptFiction> selectGptFictionList(GptFiction gptFiction);

    /**
     * 新增小说
     * 
     * @param gptFiction 小说
     * @return 结果
     */
    public int insertGptFiction(GptFiction gptFiction);

    /**
     * 修改小说
     * 
     * @param gptFiction 小说
     * @return 结果
     */
    public int updateGptFiction(GptFiction gptFiction);

    /**
     * 批量删除小说
     * 
     * @param ids 需要删除的小说主键集合
     * @return 结果
     */
    public int deleteGptFictionByIds(Long[] ids);

    /**
     * 删除小说信息
     * 
     * @param id 小说主键
     * @return 结果
     */
    public int deleteGptFictionById(Long id);

    int createGptFiction(GptFiction gptFiction);
}
