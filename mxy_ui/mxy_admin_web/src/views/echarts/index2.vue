<template>
  <div class="app-container">
    <div>
      <h2>vue中插入Echarts示例</h2>
      <div id="imageTableBox"></div>
    </div>

  </div>
</template>

<script>
import * as echarts from 'echarts/core';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from 'echarts/components';
import {PieChart} from 'echarts/charts';
import {LabelLayout} from 'echarts/features';
import {CanvasRenderer} from 'echarts/renderers';

echarts.use([
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  PieChart,
  CanvasRenderer,
  LabelLayout
]);

export default {
  name: "Advertising",
  mounted() {
    const chartDom = document.getElementById('imageTableBox');
    const myChart = echarts.init(chartDom);
    let option;

    option = {
      title: {
        text: 'Referer of a Website',
        subtext: 'Fake Data',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '50%',
          data: [ // 根据业务替换成动态数据
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
    option && myChart.setOption(option);
  }
}
</script>

<style scoped>
#imageTableBox {
  width: 100vw;
  height: 100vh;
}
h2 {
  text-align: center;
  padding: 30px;
  font-size: 18px;
}

#chart_example {
  width: 50%;
  height: 500px;
  border: 1px solid red;
  margin: 0 auto;
}
</style>

