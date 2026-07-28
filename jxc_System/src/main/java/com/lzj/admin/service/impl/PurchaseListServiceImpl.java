package com.lzj.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzj.admin.model.CountResultModel;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.pojo.PurchaseList;
import com.lzj.admin.mapper.PurchaseListMapper;
import com.lzj.admin.pojo.PurchaseListGoods;
import com.lzj.admin.query.PurchaseListQuery;
import com.lzj.admin.service.GoodsService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.service.GoodsTypeService;
import com.lzj.admin.service.PurchaseListGoodsService;
import com.lzj.admin.service.PurchaseListService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.DateUtil;
import com.lzj.admin.utils.PageResultUtil;
import com.lzj.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 进货单 服务实现类
 * </p>
 *
 * @author 老李
 */
@Service
public class PurchaseListServiceImpl extends ServiceImpl<PurchaseListMapper, PurchaseList> implements PurchaseListService {
	
	@Override
	public String getNextPurchaseNumber() {
		 // JH20210101000X
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("JH");
            stringBuffer.append(DateUtil.getCurrentDateStr());
            String purchaseNumber = this.baseMapper.getNextPurchaseNumber();
            if (null != purchaseNumber) {
                stringBuffer.append(StringUtil.formatCode(purchaseNumber));
            } else {
                stringBuffer.append("0001");
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
	}

    @Resource
    private PurchaseListGoodsService purchaseListGoodsService;

    @Resource
    private GoodsService goodsService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @Override
    public Map<String,Object> queryPurchaseListByParams(PurchaseListQuery purchaseListQuery) {


        Page<PurchaseList> page = new Page<>(purchaseListQuery.getPage(),purchaseListQuery.getLimit());


        IPage<PurchaseList> iPage = baseMapper.queryPurchaseListByParams(page,purchaseListQuery);


        return PageResultUtil.setResult(iPage.getTotal(),iPage.getRecords());
    }

    @Override
    @Transactional
    public void deletePurchaseList(Integer id) {

        //删除进货单商品明细
        QueryWrapper<PurchaseListGoods> wrapper = new QueryWrapper<>();

        wrapper.eq("purchase_list_id", id);

        purchaseListGoodsService.remove(wrapper);

        //删除进货单
        this.removeById(id);

    }

	

}
