package com.leader.ren.common.utils;

import com.github.pagehelper.PageInfo;
import com.leader.ren.common.dto.PageVo;

import java.util.List;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-28 11:23
 **/
public class PageInfoUtil {

    public static <S, R> PageVo trans2PageVo(List<S> searchList, List<R> resultList){
        PageInfo<S> pageInfo = new PageInfo<>(searchList);

        PageVo<R> pageVo = new PageVo<>();
        pageVo.setPage(pageInfo.getPageNum());
        pageVo.setSize(pageInfo.getSize());
        pageVo.setTotal(pageInfo.getTotal());

        pageVo.setData(resultList);
        return pageVo;
    }

}
