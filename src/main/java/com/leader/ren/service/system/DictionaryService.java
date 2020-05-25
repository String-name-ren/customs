package com.leader.ren.service.system;

import com.leader.ren.mapper.system.SysDictionaryMapper;
import com.leader.ren.model.system.entity.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService {

    @Autowired(required = false)
    private SysDictionaryMapper dictionaryMapper;

    /**
     * 根据dicCode获取字典组
     * @param dicCode 字典组标识
     * @return
     */
    public List<SysDictionary> getList(String dicCode){
        return dictionaryMapper.getList(dicCode);
    }

    /**
     * 设置各字典组的默认值
     * @param timeDiff
     * @param activeIp
     * @param clickIp
     */
    public void setting(Long timeDiff, Long activeIp, Long clickIp) {
        setActive(timeDiff);
        setActive(activeIp);
        setActive(clickIp);
    }

    private void setActive(Long dicId){
        //1、获取字典项详情；
        SysDictionary dictionary = dictionaryMapper.selectByPrimaryKey(dicId);
        //2、如果字典项不是激活状态，则需要把这个字典项设置为激活状态；
        if(!dictionary.getActive()){
            //3、根据dicCode把本组所有的项都置为“未激活”状态；
            dictionaryMapper.updateActive(dictionary.getDicCode());
            //4、设置需要激活的字典项；
            SysDictionary update = new SysDictionary();
            update.setDicId(dictionary.getDicId());
            update.setActive(true);
            dictionaryMapper.updateByPrimaryKeySelective(update);
        }
    }
}
