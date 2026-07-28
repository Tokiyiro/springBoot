layui.use(['table','laydate','layer',"form"],function(){

    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table;


    laydate.render({
        elem:'#startDate',
        type:'month',
        value:'2016-01'
    });


    laydate.render({
        elem:'#endDate',
        type:'month',
        value:getBeforeMonth(0)
    });



    var tableIns = table.render({

        elem:'#monthSale',

        url:ctx+"/sale/countSaleByMonth",

        cellMinWidth:95,

        height:"auto",

        toolbar:"#toolbarDemo",

        totalRow:true,

        page:false,

        id:"monthSaleTable",


        where:{
            begin:'2016-01',
            end:getBeforeMonth(0)
        },


        cols:[[
        	{
        	    field:"date",
        	    title:"销售月份",
        	    align:"center",
        	    totalRowText:"合计（￥）"
        	},
            {
                field:"amountCost",
                title:"成本金额(￥)",
                align:"center",
                totalRow:true
            },
            {
                field:"amountSale",
                title:"销售金额(￥)",
                align:"center",
                totalRow:true
            },
            {
                field:"amountProfit",
                title:"盈利金额(￥)",
                align:"center",
                totalRow:true
            }
        ]]
    });



    $(".search_btn").on("click",function(){

        var startDate=$("input[name='startDate']").val();

        var endDate=$("input[name='endDate']").val();


        table.reload("monthSaleTable",{

            where:{
                begin:startDate,
                end:endDate
            }

        });

    });



    function getBeforeMonth(n){

        var date=new Date();

        date.setMonth(date.getMonth()+n);

        var year=date.getFullYear();

        var month=date.getMonth()+1;

        return year+"-"+(month<10?"0"+month:month);

    }


});