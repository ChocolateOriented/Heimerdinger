package com.ruoyi.hemerdinger.gpt.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hemerdinger.gpt.mapper.GptFictionDataMapper;
import com.ruoyi.hemerdinger.gpt.domain.GptFictionData;
import com.ruoyi.hemerdinger.gpt.service.IGptFictionDataService;

/**
 * 小说数据Service业务层处理
 * 
 * @author lijingxiang
 * @date 2024-05-27
 */
@Service
public class GptFictionDataServiceImpl implements IGptFictionDataService 
{
    @Autowired
    private GptFictionDataMapper gptFictionDataMapper;

    /**
     * 查询小说数据
     * 
     * @param id 小说数据主键
     * @return 小说数据
     */
    @Override
    public GptFictionData selectGptFictionDataById(Long id)
    {
        return gptFictionDataMapper.selectGptFictionDataById(id);
    }

    /**
     * 查询小说数据列表
     * 
     * @param gptFictionData 小说数据
     * @return 小说数据
     */
    @Override
    public List<GptFictionData> selectGptFictionDataList(GptFictionData gptFictionData)
    {
        return gptFictionDataMapper.selectGptFictionDataList(gptFictionData);
    }

    /**
     * 新增小说数据
     * 
     * @param gptFictionData 小说数据
     * @return 结果
     */
    @Override
    public int insertGptFictionData(GptFictionData gptFictionData)
    {
        gptFictionData.setCreateTime(DateUtils.getNowDate());
        return gptFictionDataMapper.insertGptFictionData(gptFictionData);
    }

    /**
     * 修改小说数据
     * 
     * @param gptFictionData 小说数据
     * @return 结果
     */
    @Override
    public int updateGptFictionData(GptFictionData gptFictionData)
    {
        gptFictionData.setUpdateTime(DateUtils.getNowDate());
        return gptFictionDataMapper.updateGptFictionData(gptFictionData);
    }

    /**
     * 批量删除小说数据
     * 
     * @param ids 需要删除的小说数据主键
     * @return 结果
     */
    @Override
    public int deleteGptFictionDataByIds(Long[] ids)
    {
        return gptFictionDataMapper.deleteGptFictionDataByIds(ids);
    }

    /**
     * 删除小说数据信息
     * 
     * @param id 小说数据主键
     * @return 结果
     */
    @Override
    public int deleteGptFictionDataById(Long id)
    {
        return gptFictionDataMapper.deleteGptFictionDataById(id);
    }
}
