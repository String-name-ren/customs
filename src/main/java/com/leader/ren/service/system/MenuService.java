package com.leader.ren.service.system;

import com.leader.ren.common.dto.PageBo;
import com.leader.ren.component.config.CacheAsyncService;
import com.leader.ren.common.constant.RestMsg;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.common.utils.ObjectUtils;
import com.leader.ren.common.utils.TreeUtil;
import com.leader.ren.common.dto.PageVo;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.mapper.system.SysCoreElementMapper;
import com.leader.ren.mapper.system.SysCoreMenuMapper;
import com.leader.ren.model.system.bo.MenuBo;
import com.leader.ren.model.system.entity.ElementEntity;
import com.leader.ren.model.system.entity.MenuEntity;
import com.leader.ren.model.system.vo.MenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 菜单服务
 *
 * @author 王泽志
 * date 2018/11/30 16:57
 */
@Slf4j
@Service("menuService")
public class MenuService {
    @Autowired(required = false)
    private SysCoreMenuMapper mapper;

    @Autowired
    private SysCoreElementMapper sysCoreElementMapper;

    private

    @Autowired
    CacheAsyncService cacheAsyncService;

    /**
     * 取得菜单树
     *
     * @param curUid
     *          当前登录用户
     * @param bo
     *          参数
     *
     * @return
     */
    public RestVo<List<MenuVo>> getTree(Long curUid, MenuBo bo){

        Map<String, Object> params = ObjectUtils.chgObj2Map(bo);

        List<MenuVo> menus = new ArrayList<>();
        List<MenuEntity> entities = mapper.selectSearchData(params, null, null);
        for(MenuEntity entity : entities){
            MenuVo vo = new MenuVo();
            BeanUtils.copyProperties(entity, vo);
            menus.add(vo);
        }

        List<MenuVo> tree = TreeUtil.bulid(menus, 0L);

        return RestVo.SUCCESS(tree);
    }

    /**
     * 取得菜单分页
     *
     * @param curUid
     *          当前登录用户
     * @param pageBo
     *          分页参数
     *
     * @return 分页结果
     */
    public RestVo<PageVo<MenuVo>> getPage(Long curUid, PageBo<MenuBo> pageBo){

        PageVo<MenuVo> pageVo = new PageVo<>();

        Map<String, Object> params = ObjectUtils.chgObj2Map(pageBo.getParam());

        long offset = (pageBo.getPage() - 1) * pageBo.getSize();
        int limit = pageBo.getSize();

        long total = mapper.selectSearchCount(params);

        List<MenuVo> datas = new ArrayList<>();
        List<MenuEntity> entities = mapper.selectSearchData(params, offset, limit);
        for(MenuEntity entity : entities){
            MenuVo vo = new MenuVo();
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
     * 取得菜单列表
     *
     * @param curUid
     *          当前登录用户
     *
     * @return
     */
    public RestVo<List<MenuVo>> getList(Long curUid){

        List<MenuVo> datas = new ArrayList<>();
        List<MenuEntity> menuEntities = mapper.selectSearchData(null, null, null);
        for(MenuEntity menuEntity : menuEntities){
            MenuVo vo = new MenuVo();
            BeanUtils.copyProperties(menuEntity, vo);
            datas.add(vo);
        }

        return RestVo.SUCCESS(datas);
    }

    /**
     * 创建菜单信息
     *
     * @param curUid
     *          当前登录用户
     *
     * @param bo
     *          用户信息
     *
     * @return
     */
    public RestVo<Boolean> create(Long curUid, MenuBo bo){

        MenuEntity entity = new MenuEntity();
        entity.setCode(bo.getCode());
        List<MenuEntity> list = mapper.selectDataBySelective(entity);
        if(!CommUtils.isNull(list)){
            return RestVo.FAIL(RestMsg.IS_EXIST.getCode(),"编码重复");
        }

        BeanUtils.copyProperties(bo, entity);
        Long pid = entity.getParentId();
        if(pid == null || pid <= 0L){
            entity.setParentId(0L);
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
     * 删除菜单信息
     *
     * @param curUid
     *          当前登录用户
     *
     * @param menuId
     *          目标菜单的ID
     *
     * @return
     */
    public RestVo<Boolean> remove(Long curUid, Long menuId){
        Boolean flag = false;
        Map<Object, Object> map = new HashMap<>();
        map.put("parentId",menuId);
        Long count = mapper.selectSearchCount(map);
        if (count>0){
            return RestVo.SUCCESS(flag);
        }
        int rs = mapper.deleteByPrimaryKey(menuId);
        if(rs > 0){
            List<ElementEntity> elementEntities = sysCoreElementMapper.selectMenuElementByMenuId(menuId);
            List<Long> list = new ArrayList<>();
            if (!CommUtils.isNull(elementEntities)){
                for (ElementEntity elementEntity : elementEntities) {
                    list.add(elementEntity.getId());
                }
                sysCoreElementMapper.deleteRelationByMenuId(list, "element");
            }
            List<Long> listMenu = new ArrayList<>();
            listMenu.add(menuId);
            sysCoreElementMapper.deleteRelationByMenuId(listMenu,"menu");
            sysCoreElementMapper.deleteByMenuId(menuId);
            flag = true;
        }
        return RestVo.SUCCESS(flag);
    }

    /**
     * 更新菜单信息
     *
     * @param curUid
     *          当前登录用户
     *
     * @param bo
     *          元素信息
     *
     * @return
     */
    public RestVo<Boolean> update(Long curUid, MenuBo bo){

        //如果code与数据库中的不同且已存在，则不允许修改
        MenuEntity obj = mapper.selectByPrimaryKey(bo.getId());
        MenuEntity entity = new MenuEntity();
        if(!obj.getCode().equals(bo.getCode())){
            entity.setCode(bo.getCode());
            List<MenuEntity> list = mapper.selectDataBySelective(entity);
            if(!CommUtils.isNull(list)){
                RestVo.FAIL(RestMsg.IS_EXIST.getCode(),"编码重复");
            }
        }

        BeanUtils.copyProperties(bo, entity);

        entity.setUpdateAt(new Date());
        entity.setUpdateBy(curUid);

        int rs = mapper.updateByPrimaryKeySelective(entity);

        Boolean flag = false;
        if(rs > 0){
            flag = true;
        }

        return RestVo.SUCCESS(flag);
    }

    /**
     * 取得菜单信息
     *
     * @param curUid
     *          当前登录用户
     *
     * @param selEid
     *          目标元素的ID
     *
     * @return
     */
    public RestVo<MenuVo> select(Long curUid, Long selEid) {

        MenuEntity entity = mapper.selectByPrimaryKey(selEid);

        MenuVo vo = new MenuVo();
        BeanUtils.copyProperties(entity, vo);

        return RestVo.SUCCESS(vo);
    }
}
