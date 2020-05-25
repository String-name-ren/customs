package com.leader.ren.service.system;
import com.leader.ren.common.dto.PageBo;
import com.leader.ren.common.utils.CommUtils;
import com.leader.ren.common.utils.ObjectUtils;
import com.leader.ren.common.dto.PageVo;
import com.leader.ren.common.dto.RestVo;
import com.leader.ren.mapper.system.SysCoreElementMapper;
import com.leader.ren.mapper.system.SysCoreMenuMapper;
import com.leader.ren.mapper.system.SysCoreRoleMapper;
import com.leader.ren.model.system.bo.RoleBo;
import com.leader.ren.model.system.entity.ElementEntity;
import com.leader.ren.model.system.entity.MenuEntity;
import com.leader.ren.model.system.entity.SysCoreRole;
import com.leader.ren.model.system.vo.ElementVo;
import com.leader.ren.model.system.vo.MenuVo;
import com.leader.ren.model.system.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色service
 * @Author: wangfang
 * date 2019/8/27
 */
@Slf4j
@Service
public class RoleService {

    @Autowired
    private SysCoreRoleMapper roleMapper;

    @Autowired
    private SysCoreMenuMapper menuMapper;

    @Autowired
    private SysCoreElementMapper elementMapper;

    /**
     * 取得用户分页
     * @param curUid 当前登录用户
     * @param pageBo 分页参数
     * @return 分页结果
     */
    public RestVo<PageVo<RoleVo>> getPage(Long curUid, PageBo<RoleBo> pageBo){
        PageVo<RoleVo> pageVo = new PageVo<>();
        Map<String, Object> params = ObjectUtils.chgObj2Map(pageBo.getParam());
        long offset = (pageBo.getPage() - 1) * pageBo.getSize();
        int limit = pageBo.getSize();
        long total = roleMapper.selectSearchCount(params);
        List<RoleVo> datas = new ArrayList<>();
        List<SysCoreRole> entities = roleMapper.selectSearchData(params, offset, limit);
        for(SysCoreRole entity : entities){
            RoleVo vo = new RoleVo();
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
     * 创建用户信息
     * @param curUid 当前登录用户
     * @param bo 用户信息
     * @return
     */
    public RestVo<Boolean> create(Long curUid, RoleBo bo){
        //验证code是否唯一
        if(this.codeExist(bo)){
            return RestVo.FAIL("角色编码已经存在");
        }
        SysCoreRole entity = new SysCoreRole();
        BeanUtils.copyProperties(bo, entity);
        entity.setCreateAt(new Date());
        entity.setCreateBy(curUid);
        int rs = roleMapper.insert(entity);
        Boolean flag = false;
        if(rs > 0){
            flag = true;
        }
        return RestVo.SUCCESS(flag);
    }

    public boolean codeExist(RoleBo bo){
        SysCoreRole role = roleMapper.selectByCode(bo.getCode());
        if(!CommUtils.isNull(role)){
            if(CommUtils.isNull(bo.getId())){
                return true;
            }else {
                if(bo.getId() != role.getId()){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 删除用户信息
     * @param curUid 当前登录用户
     * @param roleId 目标角色的ID
     * @return
     */
    public RestVo<Boolean> remove(Long curUid, Long roleId){
        int rs = roleMapper.deleteByPrimaryKey(roleId);
        Boolean flag = false;
        if(rs > 0){
            flag = true;
        }
        return RestVo.SUCCESS(flag);
    }

    /**
     * 更新用户信息
     * @param curUid  当前登录用户
     * @param bo 用户信息
     * @return
     */
    public RestVo<Boolean> update(Long curUid, RoleBo bo){
        //验证code是否唯一
        if(this.codeExist(bo)){
            return RestVo.FAIL("角色编码已经存在");
        }
        SysCoreRole entity = new SysCoreRole();
        BeanUtils.copyProperties(bo, entity);
        entity.setUpdateAt(new Date());
        entity.setUpdateBy(curUid);
        int rs = roleMapper.updateByPrimaryKeySelective(entity);
        Boolean flag = false;
        if(rs > 0){
            flag = true;
        }
        return RestVo.SUCCESS(flag);
    }

    /**
     * 取得用户信息
     * @param curUid     当前登录用户
     * @param selUid     目标用户的ID
     * @return
     */
    public RestVo<RoleVo> select(Long curUid, Long selUid) {
        SysCoreRole entity = roleMapper.selectByPrimaryKey(selUid);
        RoleVo vo = new RoleVo();
        BeanUtils.copyProperties(entity, vo);
        return RestVo.SUCCESS(vo);
    }

    /**
     * 取得角色已关联菜单列表
     * @param curUid 当前登录用户
     * @param roleId 目标角色的ID
     * @return
     */
    public RestVo<List<MenuVo>> selectLinkedMenu(Long curUid, Long roleId){
        List<MenuVo> datas = new ArrayList<>();
        List<MenuEntity> entities = menuMapper.selectLinkedMenuByRoleId(roleId);
        for(MenuEntity entity : entities){
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(entity, menuVo);
            datas.add(menuVo);
        }
        return RestVo.SUCCESS(datas);
    }

    /**
     * 批量设定菜单关联到角色
     * @param curUid     当前登录用户
     * @param roleId     目标角色的ID
     * @param menus     菜单ID列表
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized RestVo<Map<Long,String>> batchCreateLinkedMenu(Long curUid, Long roleId, List<Long> menus){
        Map<Long, String> resultMap = new HashMap<>();
        SysCoreRole roleEntity = roleMapper.selectByPrimaryKey(roleId);
        //删除原有角色关联的所有菜单
        roleMapper.deleteLinkRoleAndMenu(roleId, 0L);
        Map<Long, MenuEntity> menusMap = new HashMap<>();
        if(!CommUtils.isNull(menus)){
            List<MenuEntity> menuEntitys = menuMapper.selectByIds(menus);
            for(MenuEntity menuEntity : menuEntitys){
                menusMap.put(menuEntity.getId(), menuEntity);
//                if(!roleEntity.getType().equals(roleEntity.getType())){
//                    resultMap.put(roleEntity.getId(), "角色[" + roleEntity.getCode()
//                            + "]和菜单[" + menuEntity.getCode() + "]类型不一致。");
//                }
            }
        }
        for(Long menuId : menus){
            int rs = roleMapper.insertLinkRoleAndMenu(roleId, menuId);
            if(rs <= 0){
                MenuEntity menuEntity = menusMap.get(menuId);
                resultMap.put(roleEntity.getId(), "角色[" + roleEntity.getCode()
                        + "]和菜单[" + menuEntity.getCode() + "]设定关联失败2。");
            }
        }
        return RestVo.SUCCESS(resultMap);
    }

    /**
     * 取得角色对应元素列表，并添加是否选中标记
     * @return
     */
    public RestVo<PageVo<ElementVo>> selectElementAllMenu(PageBo<RoleBo> pageBo){
        PageVo<ElementVo> pageVo = new PageVo<>();
        Map<String, Object> params = ObjectUtils.chgObj2Map(pageBo.getParam());
        long offset = (pageBo.getPage() - 1) * pageBo.getSize();
        int limit = pageBo.getSize();
        long total = elementMapper.selectDataCount(params);
        List<ElementVo> datas = new ArrayList<>();
        // List<ElementEntity> entities = elementMapper.selectElementByRoleId(params, offset, limit);
        List<ElementEntity> entities = elementMapper.selectData(params, offset, limit);
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
     * 从角色移除菜单元素关联(不分菜单)
     * @param curUid     当前登录用户
     * @param elements     菜单元素ID列表
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized RestVo<Map<Long,String>> removeLinkedElementAllMenu(Long curUid, Long roleId, List<Long> elements){
        Map<Long, String> resultMap = new HashMap<>();
        SysCoreRole roleEntity = roleMapper.selectByPrimaryKey(roleId);
        List<ElementEntity> elementEntitys = elementMapper.selectByIds(elements);
        Map<Long, ElementEntity> elementsMap = new HashMap<>();
        for(ElementEntity elementEntity : elementEntitys){
            elementsMap.put(elementEntity.getId(), elementEntity);
        }
        for(Long elementId : elements){
            int rs = roleMapper.deleteLinkRoleAndElement(roleId, elementId);
            if(rs <= 0){
                ElementEntity elementEntity = elementsMap.get(elementId);
                resultMap.put(elementEntity.getId(), "角色[" + roleEntity.getCode()
                        + "]和元素[" + elementEntity.getCode() + "]移除关联失败。");
            }
        }
        return RestVo.SUCCESS(resultMap);
    }


    /**
     * 设定元素关联到角色(不分菜单)
     * @param curUid     当前登录用户
     * @param roleId     目标角色的ID
     * @param elements     元素ID列表
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestVo<Map<Long,String>> createLinkedElementAllMenu(Long curUid, Long roleId, List<Long> elements){
        Map<Long, String> resultMap = new HashMap<>();
        SysCoreRole roleEntity = roleMapper.selectByPrimaryKey(roleId);
        List<ElementEntity> elementEntitys = elementMapper.selectByIds(elements);
        Map<Long, ElementEntity> elementsMap = new HashMap<>();
        for(ElementEntity elementEntity : elementEntitys){
            elementsMap.put(elementEntity.getId(), elementEntity);
        }
        for(Long elementId : elements){
            int rs = roleMapper.insertLinkRoleAndElement(roleId, elementId);
            if(rs <= 0){
                ElementEntity elementEntity = elementsMap.get(roleId);
                resultMap.put(roleEntity.getId(), "角色[" + roleEntity.getCode()
                        + "]和元素[" + elementEntity.getCode() + "]设定关联失败3。");
            }
        }
        return RestVo.SUCCESS(resultMap);
    }

    /**
     * 取得角色已关联菜单元素列表
     * @param curUid     当前登录用户
     * @param roleId     目标角色的ID
     * @param menuId     所属菜单的ID
     * @return
     */
    public RestVo<List<ElementVo>> selectLinkedElement(Long curUid, Long roleId, Long menuId){
        List<ElementVo> datas = new ArrayList<>();
        //所有元素
        List<ElementEntity> allEntities = elementMapper.selectMenuElementByMenuId(menuId);
        //角色关联的元素
        List<ElementEntity> entities = elementMapper.selectLinkedElementByLinkId(roleId, menuId);
        for(ElementEntity allEntity : allEntities){
            ElementVo elementVo = new ElementVo();
            BeanUtils.copyProperties(allEntity, elementVo);
            //设置为已关联
            for(ElementEntity entity : entities){
                if(entity.getId().equals(allEntity.getId())){
                    elementVo.setIsRelation(1);
                    break;
                }
            }
            datas.add(elementVo);
        }
        return RestVo.SUCCESS(datas);
    }

    /**
     * 从角色移除菜单元素关联
     * @param curUid     当前登录用户
     * @param menuId     所属菜单的ID
     * @param elements     菜单元素ID列表
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized RestVo<Map<Long,String>> removeLinkedElement(Long curUid, Long roleId, Long menuId, List<Long> elements){
        Map<Long, String> resultMap = new HashMap<>();
        SysCoreRole roleEntity = roleMapper.selectByPrimaryKey(roleId);
        List<ElementEntity> elementEntitys = elementMapper.selectByIds(elements);
        Map<Long, ElementEntity> elementsMap = new HashMap<>();
        for(ElementEntity elementEntity : elementEntitys){
            elementsMap.put(elementEntity.getId(), elementEntity);
        }
        for(Long elementId : elements){
            int rs = roleMapper.deleteLinkRoleAndElement(roleId, elementId);
            if(rs <= 0){
                ElementEntity elementEntity = elementsMap.get(elementId);

                resultMap.put(elementEntity.getId(), "角色[" + roleEntity.getCode()
                        + "]和菜单元素[" + elementEntity.getCode() + "]移除关联失败。");
            }
        }
        return RestVo.SUCCESS(resultMap);
    }

    /**
     * 设定菜单元素关联到角色
     * @param curUid     当前登录用户
     * @param roleId     目标角色的ID
     * @param menuId     所属菜单的ID
     * @param elements     菜单元素ID列表
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestVo<Map<Long,String>> createLinkedElement(Long curUid, Long roleId, Long menuId, List<Long> elements){
        Map<Long, String> resultMap = new HashMap<>();
        SysCoreRole roleEntity = roleMapper.selectByPrimaryKey(roleId);
        List<ElementEntity> elementEntitys = elementMapper.selectByIds(elements);
        Map<Long, ElementEntity> elementsMap = new HashMap<>();
        for(ElementEntity elementEntity : elementEntitys){
            elementsMap.put(elementEntity.getId(), elementEntity);
        }
        for(Long elementId : elements){
            int rs = roleMapper.insertLinkRoleAndElement(roleId, elementId);
            if(rs <= 0){
                ElementEntity elementEntity = elementsMap.get(roleId);
                resultMap.put(roleEntity.getId(), "角色[" + roleEntity.getCode()
                        + "]和菜单元素[" + elementEntity.getCode() + "]设定关联失败1。");
            }
        }
        return RestVo.SUCCESS(resultMap);
    }

}
