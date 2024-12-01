package com.ruoyi.hemerdinger.gpt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hemerdinger.gpt.domain.GptFiction;
import com.ruoyi.hemerdinger.gpt.domain.GptFictionData;
import com.ruoyi.hemerdinger.gpt.domain.bo.Agent;
import com.ruoyi.hemerdinger.gpt.manager.GptManager;
import com.ruoyi.hemerdinger.gpt.mapper.GptFictionMapper;
import com.ruoyi.hemerdinger.gpt.service.IGptFictionDataService;
import com.ruoyi.hemerdinger.gpt.service.IGptFictionService;
import com.ruoyi.hemerdinger.gpt.util.ChatUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.ruoyi.hemerdinger.gpt.constant.FictionConstant.ARCHITECT_PROMPT;

/**
 * 小说Service业务层处理
 * 
 * @author lijingxiang
 * @date 2024-05-27
 */
@Service
public class GptFictionServiceImpl implements IGptFictionService 
{
    @Autowired
    private GptFictionMapper gptFictionMapper;
    @Autowired
    private IGptFictionDataService gptFictionDataService;
    @Autowired
    private GptManager gptManager;

    private static final Logger log = LoggerFactory.getLogger(GptFictionServiceImpl.class);
    /**
     * 查询小说
     * 
     * @param id 小说主键
     * @return 小说
     */
    @Override
    public GptFiction selectGptFictionById(Long id)
    {
        return gptFictionMapper.selectGptFictionById(id);
    }

    /**
     * 查询小说列表
     * 
     * @param gptFiction 小说
     * @return 小说
     */
    @Override
    public List<GptFiction> selectGptFictionList(GptFiction gptFiction)
    {
        return gptFictionMapper.selectGptFictionList(gptFiction);
    }

    /**
     * 新增小说
     * 
     * @param gptFiction 小说
     * @return 结果
     */
    @Override
    public int insertGptFiction(GptFiction gptFiction)
    {
        gptFiction.setCreateTime(DateUtils.getNowDate());
        return gptFictionMapper.insertGptFiction(gptFiction);
    }

    /**
     * 修改小说
     * 
     * @param gptFiction 小说
     * @return 结果
     */
    @Override
    public int updateGptFiction(GptFiction gptFiction)
    {
        gptFiction.setUpdateTime(DateUtils.getNowDate());
        return gptFictionMapper.updateGptFiction(gptFiction);
    }

    /**
     * 批量删除小说
     * 
     * @param ids 需要删除的小说主键
     * @return 结果
     */
    @Override
    public int deleteGptFictionByIds(Long[] ids)
    {
        return gptFictionMapper.deleteGptFictionByIds(ids);
    }

    /**
     * 删除小说信息
     * 
     * @param id 小说主键
     * @return 结果
     */
    @Override
    public int deleteGptFictionById(Long id)
    {
        return gptFictionMapper.deleteGptFictionById(id);
    }

    /**
     * 创建小说
     * @param gptFiction
     * @return
     */
    @Override
    public int createGptFiction(GptFiction gptFiction) {
        //1. 生成小说架构, 并保存
        Agent architect = new Agent(ARCHITECT_PROMPT);
        JSONObject fictionBase = readJSON("classpath:fictionConfig/xiuxian/fictionFrame.json");
        JSONObject fictionFrame = fictionBase.getJSONObject("故事框架");

        String background = callGpt(architect, "设计一部修仙小说的故事背景。要求主角刚登场就处于一个的困境之中。故事的结局是开放的。");
        fictionFrame.put("故事背景", background);

        String roleSet = callGpt(architect, "设计几位主角身边的配角，并设定人物背景故事、人物性格、外貌特征、人物能力");
        fictionFrame.put("人物角色", roleSet);

        String mainStory = callGpt(architect, "设计小说的主线剧情，制造矛盾冲突，使剧情逐渐高潮");
        fictionFrame.put("主线剧情", mainStory);

        String sideStory = callGpt(architect, "请设计几条支线剧情，要求能够推动主线剧情，并制造矛盾冲突");
        fictionFrame.put("支线剧情", sideStory);

        String volumes = callGpt(architect, "融合全部主线以及支线剧情, 将剧情以目录的形式进行整理，返回10卷左右的目录，要求每一卷主角都处于某一个困境，并努力克服困境，以JSON的格式返回, 格式形如 {\"volumes\": [{\"volume\": 1,\"title\": \"xxx\",\"summary\": \"xxxx\"}]}");
        volumes =  ChatUtil.extractMarkDownJSONWithDelimiters(volumes);
        fictionFrame.put("目录", volumes);

        //获取书名
        gptFiction.setName(ChatUtil.extractTitle(background,"《","》"));
        int insert = gptFictionMapper.insertGptFiction(gptFiction);

        //保存小说框架
        GptFictionData fictionFrameData = new GptFictionData();
        fictionFrameData.setFictionId(gptFiction.getId());
        fictionFrameData.setContent(fictionBase.toJSONString());
        gptFictionDataService.insertGptFictionData(fictionFrameData);

        //生成第一卷框架
        JSONObject volumeFrame = createVolumeFrame(1, fictionBase);
        //生成第一卷第一章第一段
        createNextParagraph(gptFiction, 0, 0, 0);
        return insert;
    }

    /**
     * 生成卷故事框架
     * @param volumeNum
     * @param fictionBase
     * @return
     */
    private JSONObject createVolumeFrame(int volumeNum, JSONObject fictionBase) {
        JSONObject fictionFrame = fictionBase.getJSONObject("故事框架");
        String volumes = ChatUtil.extractMarkDownJSON(fictionFrame.getString("目录"));
        JSONArray volumesArray = JSONObject.parseObject(volumes).getJSONArray("volumes");

        for (int i = 0; i < volumesArray.size(); i++) {
            JSONObject volume = volumesArray.getJSONObject(i);
            if (volume.getInteger("volume") == volumeNum) {
                String volumeTitle = volume.getString("title");
                String volumeSummary = volume.getString("summary");

                JSONObject volumeFrame = new JSONObject();
                volumeFrame.put("volume", volumeNum);
                volumeFrame.put("title", volumeTitle);
                volumeFrame.put("summary", volumeSummary);
                //TODO
                volumeFrame.put("功法", volumeNum);
//                String volumeContent = callGpt()
                volumeFrame.put("宝物", volumeNum);
                volumeFrame.put("角色", volumeNum);
                volumeFrame.put("chapters", volumeNum);


            }
        }
        return null;
    }

    private void createNextParagraph(GptFiction gptFiction, int volumeNum, int chapterNum, int paragraphNum) {


    }

    private JSONObject readJSON(String path) {
        try {
            File file = ResourceUtils.getFile(path);
            String json = FileUtils.readFileToString(file, "UTF-8");
            return JSON.parseObject(json);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String callGpt(Agent agent, String message) {
        agent.appendUserMessage(message);
        String result = gptManager.call(agent.getMessages());
        agent.appendAssistantMessage(result);
        log.debug("gptresult:" + result);
        return result;
    }
}
