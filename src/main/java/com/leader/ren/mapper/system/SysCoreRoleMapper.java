package com.leader.ren.mapper.system;

import com.leader.ren.model.system.entity.SysCoreRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCoreRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysCoreRole record);

    int insertSelective(SysCoreRole record);

    /**
     * 根据主键取得数据
     *
     * @param id
     * @return
     */
    SysCoreRole selectByPrimaryKey(Long id);

    /**
     * 有条件的更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysCoreRole record);

    /**
     * 更新整个数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysCoreRole record);

    /**
     * 取得检索分页信息
     *
     * @param params
     * @param offset
     * @param limit
     * @return
     */
    List<SysCoreRole> selectSearchData(@Param("params") Map params, @Param("offset") Long offset, @Param("limit") Integer limit);

    /**
     * 取得检索信息条数
     *
     * @param params
     * @return
     */
    Long selectUserRoleSearchCount(@Param("params") Map params);

    /**
     * 取得检索分页信息
     *
     * @param params
     * @param offset
     * @param limit
     * @return
     */
    List<SysCoreRole> selectUserRoleSearchData(@Param("params") Map params, @Param("offset") Long offset, @Param("limit") Integer limit);
    /**
     * 取得检索信息条数
     *
     * @param params
     * @return
     */
    Long selectSearchCount(@Param("params") Map params);

    /**
     * 根据ID一览取得角色列表
     *
     * @param roles
     * @return
     */
    List<SysCoreRole> selectByIds(@Param("roles") List<Long> roles);

    /**
     * 取得用户已关联角色列表
     *
     * @param userId
     * @return
     */
    List<SysCoreRole> selectLinkedRoleByUserId(@Param("userId") Long userId);

    /**
     * 取得用户未关联角色列表
     *
     * @param userId
     * @return
     */
    List<SysCoreRole> selectUnlinkRoleByUserIdAndType(@Param("userId") Long userId, @Param("types") List<String> types);

    /**
     * 设定菜单关联到角色
     *
     * @param roleId
     * @param menuId
     * @return
     */
    int insertLinkRoleAndMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 从角色移除菜单关联
     *
     * @param roleId
     * @param menuId
     * @return
     */
    int deleteLinkRoleAndMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 设定菜单元素关联到角色
     *
     * @param roleId
     * @param elementId
     * @return
     */
    int insertLinkRoleAndElement(@Param("roleId") Long roleId, @Param("elementId") Long elementId);

    /**
     * 从角色移除菜单元素关联
     *
     * @param roleId
     * @param elementId
     * @return
     */
    int deleteLinkRoleAndElement(@Param("roleId") Long roleId, @Param("elementId") Long elementId);

    /**
     * 根据code取得数据
     * @param code
     * @return
     */
    SysCoreRole selectByCode(String code);

    List<SysCoreRole> getAllRole();

    List<Long> getRoleIdByUserId(@Param("userId") Long userId);

}