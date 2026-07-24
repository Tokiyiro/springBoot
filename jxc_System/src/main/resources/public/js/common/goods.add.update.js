layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;



    $("#select").click(function (event){
        event.preventDefault();
        var gid= $("input[name='id']").val();
        var gname = $("input[name='name']").val();
        var code = $("#code").val();
        var price=$("#price").val();
        var num =$("#num").val();
        var model=$("#model").val();
        var unit = $("#unitName").val();
        var typeId = $("input[name='typeId']").val();
        var isUpdate = $("input[name='flag']").val() == 1;
        var targetWindow = findGoodsSelectionTarget();
        if (!targetWindow) {
            layer.msg('未找到商品选择页面，请关闭窗口后重新选择商品');
            return false;
        }

        if(isUpdate){
            // 更新操作
            if(num==""||num==null) {
                num = 0;
            }
            targetWindow.getGoodsSelectInfo(gid,gname,code,price,num,model,unit,typeId,false);
        }else{
            if(num==""||num==null){
                num=0;
            }
            // 添加操作
            targetWindow.getGoodsSelectInfo(gid,gname,code,price,num,model,unit,typeId,true);
        }

        if (parent.layer && typeof parent.layer.closeAll === 'function') {
            parent.layer.closeAll();
        }
        if (!isUpdate && targetWindow.layer && typeof targetWindow.layer.closeAll === 'function') {
            targetWindow.layer.closeAll();
        }
        return false;
    });

    function findGoodsSelectionTarget() {
        var currentWindow = window.parent;
        while (currentWindow) {
            if (typeof currentWindow.getGoodsSelectInfo === 'function') {
                return currentWindow;
            }
            if (currentWindow === currentWindow.parent) {
                break;
            }
            currentWindow = currentWindow.parent;
        }
        return null;
    }

    $("#closeDlg").click(function (){
        // iframe 页面关闭 添加parent
        parent.layer.closeAll();
    })


});
