package com.lzj.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.mapper.GoodsMapper;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.service.CustomerReturnListGoodsService;
import com.lzj.admin.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.service.GoodsTypeService;
import com.lzj.admin.service.SaleListGoodsService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.PageResultUtil;
import com.lzj.admin.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 商品表实现类
 * @author TianTian
 * @date 2022/1/19 14:51
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private SaleListGoodsService saleListGoodsService;

    @Resource
    private CustomerReturnListGoodsService customerReturnListGoodsService;
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public Map<String, Object> queryGoodsByParams(GoodsQuery goodsQuery) {

        // 分页对象
        Page<Goods> page = new Page<>(goodsQuery.getPage(), goodsQuery.getLimit());

        // 查询条件
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();

        // 商品名称
        if (StringUtils.isNotBlank(goodsQuery.getGoodsName())) {
            wrapper.like("name", goodsQuery.getGoodsName());
        }

        // 商品类别
        if (goodsQuery.getTypeId() != null) {
            wrapper.eq("type_id", goodsQuery.getTypeId());
        }

        IPage<Goods> goodsPage = baseMapper.queryGoodsByParams(page, goodsQuery);
        System.out.println(goodsPage.getRecords());

        return PageResultUtil.setResult(goodsPage.getTotal(), goodsPage.getRecords());
    }
    
    @Override
    public Goods queryGoodsInfo(Integer id) {
        return goodsMapper.queryGoodsInfo(id);
    }
}
