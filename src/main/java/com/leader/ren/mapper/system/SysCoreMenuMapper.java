package com.leader.ren.mapper.system;

import com.leader.ren.model.system.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 菜单Mapper
 *
 * @author 王泽志
 * date 2018/11/30 16:57
 */
@Mapper
public interface SysCoreMenuMapper {
	/**
	 * 根据主键删除数据
	 *
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 插入整个数据
	 *
	 * @param record
	 * @return
	 */
	int insert(MenuEntity record);

	/**
	 * 有条件的插入数据
	 *
	 * @param record
	 * @return
	 */
	int insertSelective(MenuEntity record);

	/**
	 * 根据主键取得数据
	 *
	 * @param id
	 * @return
	 */
	MenuEntity selectByPrimaryKey(Long id);

	/**
	 * 有条件的更新数据
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(MenuEntity record);

	/**
	 * 更新整个数据
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(MenuEntity record);

	/**
	 * 取得检索分页信息
	 *
	 * @param params
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<MenuEntity> selectSearchData(@Param("params") Map params, @Param("offset") Long offset, @Param("limit") Integer limit);

	/**
	 * 取得检索信息条数
	 *
	 * @param params
	 * @return
	 */
	Long selectSearchCount(@Param("params") Map params);

	/**
	 *
	 * @param menus
	 */
	List<MenuEntity> selectByIds(@Param("menus") List<Long> menus);

	/**
	 * 根据角色列表取得数据
	 *
	 * @param roleIds
	 * @return
	 */
	List<MenuEntity> selectByRoleIds(@Param("roleIds") List<Long> roleIds);

	/**
	 * 根据用户取得关联数据
	 *
	 * @param userId
	 * @return
	 */
	List<MenuEntity> selectByUserId(@Param("params") Map<String, Object> params);

	/**
	 * 取得角色已关联菜单列表
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuEntity> selectLinkedMenuByRoleId(@Param("roleId") Long roleId);

	/**
	 * 取得角色未关联菜单列表
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuEntity> selectUnlinkMenuByRoleIdAndType(@Param("roleId") Long roleId, @Param("types") List<String> types);

	/**
	 * 根据条件筛选列表数据
	 *
	 * @param entity
	 * @return
	 */
	List<MenuEntity> selectDataBySelective(@Param("entity") MenuEntity entity);
}