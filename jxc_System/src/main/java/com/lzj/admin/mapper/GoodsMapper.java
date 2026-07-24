package com.lzj.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper extends BaseMapper<Goods> {

	IPage<Goods> queryGoodsByParams(Page<Goods> page,@Param("query") GoodsQuery goodsQuery);
	
	/**
     * 根据id查询商品详细信息
     */
	Goods queryGoodsInfo(Integer id);
}
