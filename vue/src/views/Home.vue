<template>
    <div>
        <el-row style="margin-bottom: 20px">
            <el-col :span="6">
                <el-card style="color: #409EFF">
                    <div><i class="el-icon-user-solid"></i>用户总数</div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bold">
                        100
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #F56c6c">
                    <div><i class="el-icon-money"></i>销售总量</div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bold">
                        500000
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #67C23A">
                    <div><i class="el-icon-coin"></i>收益总额</div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bold">
                        ￥10000000
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #E6A23C">
                    <div><i class="el-icon-s-shop"></i>门店总数</div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bold">
                        15
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <div id="main" style="width: 500px;height: 450px;"></div>
            </el-col>

            <el-col :span="12">
                <div id="pie" style="width: 500px;height: 400px;"></div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import * as echarts from 'echarts';

    export default {
        name: "Home",
        data() {
            return {}
        },
        mounted() {  //页面元素渲染之后再触发
            var option;
            option = {
                title: {
                    text: "各季度会员数量统计",
                    subtext: "趋势图",
                    left: "center"
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                xAxis: {
                    type: 'category',
                    data: ["第一季度", "第二季度", "第三季度", "第四季度"]
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: [],
                        type: 'line'
                    },
                    {
                        data: [],
                        type: 'bar'
                    }
                ]
            };


            //饼图
            var pieOption = {
                title: {
                    text: '各季度会员数量统计',
                    subtext: '比例图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name:'会员数量',
                        type: 'pie',
                        radius: '55%',
                        label: {    //饼图图形上的文本标签
                            normal: {
                                show: true,
                                position: 'inner',  //标签的位置
                                textStyle: {
                                    fontWeight: 300,
                                    fontSize: 16,
                                    color: "#fff"
                                },
                                formatter: '{d}%'
                            }
                        },
                        data: [
                            {value: 1048, name: 'Search Engine'},
                            {value: 735, name: 'Direct'},
                            {value: 580, name: 'Email'},
                            {value: 484, name: 'Union Ads'},
                            {value: 300, name: 'Video Ads'}
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
            var chartDom = document.getElementById('main');
            var myChart = echarts.init(chartDom);

            var pieDom = document.getElementById('pie');
            var pieChart = echarts.init(pieDom);


            this.request.get("/echarts/members").then(res => {
                //对后台数据进行填空
                option.series[0].data = res.data
                option.series[1].data = res.data
                //数据准备好后set
                myChart.setOption(option)

                //饼图数据填充
                pieOption.series[0].data = [
                    {value: res.data[0], name: '第一季度'},
                    {value: res.data[1], name: '第二季度'},
                    {value: res.data[2], name: '第三季度'},
                    {value: res.data[3], name: '第四季度'}]
                //数据准备好后set
                pieChart.setOption(pieOption)
            })

        }


    }

</script>

<style scoped>
    i {
        margin-right: 5px;
    }
</style>