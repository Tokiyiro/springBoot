package com.lzj.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzj.admin.model.CountResultModel;
import com.lzj.admin.pojo.PurchaseList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzj.admin.query.PurchaseListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 进货单接口
 * @author TianTian
 * @date 2022/1/21 18:27
 */
@Mapper
public interface PurchaseListMapper extends BaseMapper<PurchaseList> {

    String getNextPurchaseNumber();


    IPage<PurchaseList> queryPurchaseListByParams( IPage<PurchaseList> page, @Param("purchaseListQuery") PurchaseListQuery purchaseListQuery);

}
