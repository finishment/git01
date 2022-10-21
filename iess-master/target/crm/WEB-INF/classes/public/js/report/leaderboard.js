layui.use(['layer','echarts'], function () {
    var $ = layui.jquery,
        echarts = layui.echarts;

    /**
     * 通过月份查询对应的云记数量
     */
    $.ajax({
        type: "get",
        url: ctx + "/report/name",
        data: {},
        success: function (total) {
            console.log(total);

                var userName = total[2];
                // 得到月份对应的云记数量 （得到Y轴的数据）
                var num = total[0];
                console.log(userName);
                console.log(num);
                // 加载柱状图
                loadMonthChart(userName, num);
        }
    });

    /**
     * 加载柱状图
     */
    function loadMonthChart(userName, num) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('numChart'));

        // 指定图表的配置项和数据
        // X轴显示名称
        var dataAxis = userName;
        // Y轴的数据
        var data = num;
        var yMax = 30;
        var dataShadow = [];

        for (var i = 0; i < data.length; i++) {
            dataShadow.push(yMax);
        }

        var option = {
            // 标题
            title: {
                text: '最强同学', // 主标题
                subtext: '最活跃前五名的同学分享经验的数量', // 副标题
                left: 'center' // 标题对齐方式，center表示居中
            },
            // 提示框
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    type: 'pie',
                    radius: '50%',
                    data: [
                        { value: data[0], name: dataAxis[0] },
                        { value: data[1], name: dataAxis[1] },
                        { value: data[2], name: dataAxis[2] },
                        { value: data[3], name: dataAxis[3] },
                        { value: data[4], name: dataAxis[4] },
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]


        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }




});



