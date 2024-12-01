package com.ruoyi.hemerdinger.gpt.service;

import java.util.List;
import com.ruoyi.hemerdinger.gpt.domain.GptFictionData;

/**
 * 小说数据Service接口
 * 
 * @author lijingxiang
 * @date 2024-05-27
 */
public interface IGptFictionDataService 
{
    /**
     * 查询小说数据
     * 
     * @param id 小说数据主键
     * @return 小说数据
     */
    public GptFictionData selectGptFictionDataById(Long id);

    /**
     * 查询小说数据列表
     * 
     * @param gptFictionData 小说数据
     * @return 小说数据集合
     */
    public List<GptFictionData> selectGptFictionDataList(GptFictionData gptFictionData);

    /**
     * 新增小说数据
     * 
     * @param gptFictionData 小说数据
     * @return 结果
     */
    public int insertGptFictionData(GptFictionData gptFictionData);

    /**
     * 修改小说数据
     * 
     * @param gptFictionData 小说数据
     * @return 结果
     */
    public int updateGptFictionData(GptFictionData gptFictionData);

    /**
     * 批量删除小说数据
     * 
     * @param ids 需要删除的小说数据主键集合
     * @return 结果
     */
    public int deleteGptFictionDataByIds(Long[] ids);

    /**
     * 删除小说数据信息
     * 
     * @param id 小说数据主键
     * @return 结果
     */
    public int deleteGptFictionDataById(Long id);
}
