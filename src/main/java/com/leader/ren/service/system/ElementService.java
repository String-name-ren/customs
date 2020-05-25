package com.leader.ren.service.system;


import com.leader.ren.common.dto.PageBo;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.common.utils.ObjectUtils;
import com.leader.ren.common.dto.PageVo;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.mapper.system.SysCoreElementMapper;
import com.leader.ren.model.system.bo.ElementBo;
import com.leader.ren.model.system.entity.ElementEntity;
import com.leader.ren.model.system.vo.ElementVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 元素服务
 *
 * @author 王泽志
 * date 2018/11/30 16:57
 */
@Slf4j
@Service
public class ElementService {
    // 公钥
    @Value("${sys.security.salt}")
    private String salt;

    @Autowired(required = false)
    private SysCoreElementMapper mapper;

    /**
     * 取得用户分页
     *
     * @param curUid
     *          当前登录用户
     * @param pageBo
     *          分页参数
     *
     * @return 分页结果
     */
    public RestVo<PageVo<ElementVo>> getPage(Long curUid, PageBo<ElementBo> pageBo){

        PageVo<ElementVo> pageVo = new PageVo<>();

        Map<String, Object> params = ObjectUtils.chgObj2Map(pageBo.getParam());

        long offset = (pageBo.getPage() - 1) * pageBo.getSize();
        int limit = pageBo.getSize();

        long total = mapper.selectSearchCount(params);

        List<ElementVo> datas = new ArrayList<>();
        List<ElementEntity> entities = mapper.selectSearchData(params, offset, limit);
        for(ElementEntity entity : entities){
            ElementVo vo = new ElementVo();
            BeanUtils.copyProperties(entity, vo);
            datas.add(vo);
        }

        pageVo.setPage(pageBo.getPage());
        pageVo.setSize(pageBo.getSize());
        pageVo.setTotal(total);
        pageVo.setData(datas);

        return RestVo.SUCCESS(pageVo);
    }

    /**
     * 取得用户列表
     *
     * @param curUid
     *          当前登录用户
     *
     * @return
     */
    public RestVo<List<ElementVo>> getList(Long curUid){

        List<ElementVo> datas = new ArrayList<>();
        List<ElementEntity> entities = mapper.selectSearchData(null, null, null);

        for(ElementEntity entity : entities){
            ElementVo vo = new ElementVo();
            BeanUtils.copyProperties(entity, vo);
            datas.add(vo);
        }

        return RestVo.SUCCESS(datas);
    }

    /**
     * 创建用户信息
     *
     * @param curUid
     *          当前登录用户
     *
     * @param bo
     *          用户信息
     *
     * @return
     */
    public RestVo<Boolean> create(Long curUid, ElementBo bo){

        ElementEntity entity = new ElementEntity();
        BeanUtils.copyProperties(bo, entity);

        ElementEntity entityObj = new ElementEntity();
        entityObj.setCode(bo.getCode());
        entityObj.setStatus(1);
        List<ElementEntity> list = mapper.selectElementBySelective(entityObj);
        if(!CommUtils.isNull(list)){
            return RestVo.FAIL(RestMsg.IS_EXIST.getCode(),"编码已存在");
        }

        entity.setCreateAt(new Date());
        entity.setCreateBy(curUid);

        int rs = mapper.insert(entity);

        Boolean flag = false;
        if(rs > 0){
            flag = true;
        }

        return RestVo.SUCCESS(flag);
    }

    /**
     * 删除用户信息
     *
     * @param curUid
     *          当前登录用户
     *
     * @param elementId
     *          目标元素的ID
     *
     * @return
     */
    public RestVo<Boolean> remove(Long curUid, Long elementId){

        int rs = mapper.deleteByPrimaryKey(elementId);
        Boolean flag = false;
        if(rs > 0){
            List<Long> list = new ArrayList<>();
            list.add(elementId);
            mapper.deleteRelationByMenuId(list, "element");
            flag = true;
        }
        return RestVo.SUCCESS(flag);
    }

    /**
     * 更新用户信息
     *
     * @param curUid
     *          当前登录用户
     *
     * @param bo
     *          元素信息
     *
     * @return
     */
    public RestVo<Boolean> update(Long curUid, ElementBo bo){

        ElementEntity entity = new ElementEntity();
        BeanUtils.copyProperties(bo, entity);
        entity.setUpdateAt(new Date());
        entity.setUpdateBy(curUid);

        ElementEntity entityObj = new ElementEntity();
        entityObj.setId(bo.getId());
        entityObj.setStatus(null);
        List<ElementEntity> list = mapper.selectElementBySelective(entityObj);
        if(CommUtils.isNull(list)){
            return RestVo.FAIL(RestMsg.IS_EXIST.getCode(),"没有找到需要修改的数据");
        }

        ElementEntity obj = list.get(0);
        //置为无效时直接修改
        if(bo.getStatus() == 0 && !obj.getCode().equals(bo.getCode())){
            entityObj.setId(null);
            entityObj.setCode(bo.getCode());
            entityObj.setStatus(null);
            List<ElementEntity> listObj = mapper.selectElementBySelective(entityObj);
            if(!CommUtils.isNull(listObj)){
                return RestVo.FAIL(RestMsg.IS_EXIST.getCode(),RestMsg.IS_EXIST.getName());
            }
            int rs = mapper.updateByPrimaryKeySelective(entity);
            Boolean flag = false;
            if(rs > 0){
                flag = true;
            }
            return RestVo.SUCCESS(flag);
        }

        //与原code不相等
        if(!obj.getCode().equals(bo.getCode())){
            entityObj.setId(null);
            entityObj.setCode(bo.getCode());
            entityObj.setStatus(1);
            List<ElementEntity> listObj = mapper.selectElementBySelective(entityObj);
            if(!CommUtils.isNull(listObj)){
                return RestVo.FAIL(RestMsg.IS_EXIST.getCode(),RestMsg.IS_EXIST.getName());
            }
        }
        int rs = mapper.updateByPrimaryKeySelective(entity);
        Boolean flag = false;
        if(rs > 0){
            flag = true;
        }
        return RestVo.SUCCESS(flag);
    }

    /**
     * 取得用户信息
     *
     * @param curUid
     *          当前登录用户
     *
     * @param selEid
     *          目标元素的ID
     *
     * @return
     */
    public RestVo<ElementVo> select(Long curUid, Long selEid) {

        ElementEntity entity = mapper.selectByPrimaryKey(selEid);

        ElementVo vo = new ElementVo();
        BeanUtils.copyProperties(entity, vo);

        return RestVo.SUCCESS(vo);
    }
}
