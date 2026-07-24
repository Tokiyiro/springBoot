layui.use(['element', 'laydate', 'table', 'layer', 'form'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        form = layui.form,
        table = layui.table;

    laydate.render({ elem: '#purchaseDate' });

    $.ajax({
        type: 'post',
        url: ctx + '/supplier/allGoodsSuppliers',
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#supplierId').append("<option value='" + item.id + "'>" + item.name + '</option>');
                });
            }
            form.render('select');
        }
    });

    table.render({
        elem: '#purchaseList',
        height: 'full-125',
        toolbar: '#toolbarDemo',
        id: 'purchaseListTable',
        cols: [[
            { field: 'id', hide: true },
            { field: 'code', title: '商品编码', minWidth: 50, align: 'center' },
            { field: 'name', title: '商品名称', minWidth: 100, align: 'center' },
            { field: 'model', title: '商品型号', minWidth: 100, align: 'center' },
            { field: 'price', title: '单价', minWidth: 100, align: 'center' },
            { field: 'num', title: '数量', minWidth: 100, align: 'center' },
            { field: 'unit', title: '单位', minWidth: 100, align: 'center' },
            { field: 'total', title: '总金额', minWidth: 100, align: 'center' },
            { title: '操作', minWidth: 150, templet: '#goodsListBar', fixed: 'right', align: 'center' }
        ]],
        data: []
    });

    table.on('toolbar(purchases)', function (obj) {
        if (obj.event === 'add') {
            layer.open({
                title: '进货入库商品选择',
                type: 2,
                area: ['950px', '600px'],
                maxmin: true,
                content: ctx + '/common/toSelectGoodsPage'
            });
        }
    });

    table.on('tool(purchases)', function (obj) {
        if (obj.event === 'edit') {
            openUpdateGoodsInfoDialog(obj.data);
        } else if (obj.event === 'del') {
            layer.confirm('确定移除当前商品？', { icon: 3, title: '商品选择' }, function (index) {
                removeGoods(obj.data.goodsId || obj.data.id);
                layer.close(index);
            });
        }
    });

    function openUpdateGoodsInfoDialog(goods) {
        var gid = goods.goodsId || goods.id;
        if (gid === undefined || gid === null || gid === '') {
            layer.msg('商品 ID 不存在');
            return;
        }
        var encodedGid = encodeURIComponent(gid);
        var url = ctx + '/common/toUpdateGoodsInfoPage?gid=' + encodedGid
            + '&id=' + encodedGid
            + '&price=' + encodeURIComponent(goods.price || 0)
            + '&num=' + encodeURIComponent(goods.num || 0)
            + '&total=' + encodeURIComponent(goods.total || 0);
        layer.open({
            title: '进货入库商品更新',
            type: 2,
            area: ['800px', '550px'],
            maxmin: true,
            content: url
        });
    }

    function removeGoods(gid) {
        datas = datas.filter(function (item) {
            return String(item.goodsId) !== String(gid);
        });
        reloadTableData();
    }

    form.on('submit(addPurchaseList)', function (data) {
        var index = top.layer.msg('数据提交中，请稍候', { icon: 16, time: false, shade: 0.8 });
        $.post(ctx + '/purchase/save', data.field, function (res) {
            if (res.code === 200) {
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg('操作成功');
                    layer.closeAll('iframe');
                    window.location.href = ctx + '/purchase/index';
                }, 500);
            } else {
                layer.msg(res.message, { icon: 5 });
            }
        });
        return false;
    });
});

var datas = [];

function getGoodsSelectInfo(gid, gname, code, price, num, model, unit, typeId, flag) {
    var unitPrice = Number(price), quantity = Number(num);
    unitPrice = isFinite(unitPrice) ? unitPrice : 0;
    quantity = isFinite(quantity) ? quantity : 0;

    if (flag) {
        var existing = datas.some(function (item) {
            return String(item.goodsId) === String(gid);
        });
        if (existing) {
            layui.layer.msg('该商品已添加，请使用编辑修改数量或单价');
            return;
        }
        datas.push({
            id: gid,
            goodsId: gid,
            code: code,
            name: gname,
            price: unitPrice,
            num: quantity,
            model: model,
            unit: unit,
            typeId: typeId,
            total: unitPrice * quantity
        });
    } else {
        datas.forEach(function (item) {
            if (String(item.goodsId) === String(gid)) {
                item.price = unitPrice;
                item.num = quantity;
                item.total = unitPrice * quantity;
            }
        });
    }
    reloadTableData();
}

function reloadTableData() {
    layui.table.reload('purchaseListTable', { data: datas });
    var total = datas.reduce(function (sum, item) {
        return sum + Number(item.total || 0);
    }, 0);
    layui.jquery('#amountPayable').val(total);
    layui.jquery('#amountPaid').val(total);
    layui.jquery("input[name='goodsJson']").val(JSON.stringify(datas));
}
