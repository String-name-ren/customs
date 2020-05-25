package com.leader.ren.mapper.system;

import com.leader.ren.model.system.entity.ElementEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 元素Mapper
 *
 * @author 王泽志
 * date 2018/11/30 16:57
 */
@Mapper
public interface SysCoreElementMapper {

    /**
     * 根据主键删除数据
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    int deleteByMenuId(Long menuId);

    int deleteRelationByMenuId(@Param("menuIds") List<Long> menuIds, @Param("type") String type);

    /**
     * 插入整个数据
     *
     * @param record
     * @return
     */
    int insert(ElementEntity record);

    /**
     * 有条件的插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(ElementEntity record);

    /**
     * 根据主键取得数据
     *
     * @param id
     * @return
     */
    ElementEntity selectByPrimaryKey(Long id);

    /**
     * 有条件的更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ElementEntity record);

    /**
     * 更新整个数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(ElementEntity record);

    /**
     * 取得检索分页信息
     *
     * @param params
     * @param offset
     * @param limit
     * @return
     */
    List<ElementEntity> selectSearchData(@Param("params") Map params, @Param("offset") Long offset, @Param("limit") Integer limit);

    /**
     * 取得检索信息条数
     *
     * @param params
     * @return
     */
    Long selectSearchCount(@Param("params") Map params);

    /**
     * 取得检索信息条数
     *
     * @param params
     * @return
     */
    Long selectDataCount(@Param("params") Map params);
    /**
     * 根据ID一览取得元素列表
     *
     * @param elements
     * @return
     */
    List<ElementEntity> selectByIds(@Param("elements") List<Long> elements);

    /**
     * 根据角色列表取得数据
     *
     * @param roleIds
     * @return
     */
    List<ElementEntity> selectByRoleIds(List<Long> roleIds);

    /**
     * 根据用户取得关联数据
     *
     * @param userId
     * @return
     */
    List<ElementEntity> selectByUserId(Long userId);

    /**
     * 获取所有元素信息
     * @return
     */
    List<ElementEntity> selectAllElement(String type);

    /**
     * 取得角色已关联菜单元素列表
     *
     * @param roleId
     * @param menuId
     * @return
     */
    List<ElementEntity> selectLinkedElementByLinkId(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 取得角色未关联菜单元素列表
     *
     * @param roleId
     * @param menuId
     * @return
     */
    List<ElementEntity> selectUnlinkElementByLinkId(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 取得菜单关联元素列表
     *
     * @param menuId
     * @return
     */
    List<ElementEntity> selectMenuElementByMenuId(@Param("menuId") Long menuId);

    /**
     * 取得角色已关联元素列表
     *
     * @param roleId
     * @return
     */
    List<ElementEntity> selectLinkedElementByRoleId(@Param("roleId") Long roleId);

    /**
     * 取得角色未关联元素列表
     *
     * @param roleId
     * @return
     */
    List<ElementEntity> selectUnlinkElementByRoleId(@Param("roleId") Long roleId);

    /**
     * 取得角色对应元素列表，并添加是否选中标记
     * @param params
     * @param offset
     * @param limit
     * @return
     */
    List<ElementEntity> selectData(@Param("params") Map params, @Param("offset") Long offset, @Param("limit") Integer limit);
    /**
     * 根据条件精确筛选元素数据
     *
     * @param entity
     * @return
     */
    List<ElementEntity> selectElementBySelective(@Param("entity") ElementEntity entity);


    List<ElementEntity>  getElementByCode(@Param("elementCode") List<String> elementCode);


    /**
     * 取得角色已关联菜单元素列表
     * @return
     */
    int existRoleIdAndElementId(@Param("roleIdList") List<Long> roleIdList, @Param("resourceId") Long resourceId);
}