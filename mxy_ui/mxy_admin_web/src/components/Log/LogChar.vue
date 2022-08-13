<template>
  <div id="logChar" class="chart-wrap"></div>
</template>

<script>
import ChartMixin from '@/utils/chartsMixin.js'
export default {
  mixins: [ChartMixin],
  props: {
    chartData: {
      type: Object
    }
  },
  data() {
    return {
      option:{
        grid: {
          top: '10%',
          left: '10%',
          right: '5%',
          bottom: '15%'
        },
        xAxis: [

        ],
        yAxis: [
          { type: 'value',
            z: 1,
            axisLine: {
              show: false,
              lineStyle: {
                color: '#dcdcdc'
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#f1f1f1'
              }
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              color: '#898989',
              interval: 0
            }}
        ],
        // dataZoom: [
        //   {
        //     type: "slider",
        //     realtime: true, //拖动滚动条时是否动态的更新图表数据
        //     height: 6, //滚动条高度
        //     start: 10, //滚动条开始位置（共100等份）
        //     end: 100, //结束位置（共100等份）
        //     bottom: 1
        //   }
        // ],
        series: []
      },
      xData: [],
      seriesItem:[]
    }
  },
  watch: {
    // 观察option的变化
    option: {
      handler(newVal, oldVal) {
        this.timer()
        this.destroyed()
      },
      deep: true   //对象内部属性的监听
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.drawChart()
    })
  },
  methods: {
    drawChart() {
      /*置空数据*/
      if (this.xData.length !== 0) {
        this.xData.splice(0, this.xData.length);
      }
      if (this.seriesItem.length !== 0) {
        this.seriesItem.splice(0, this.seriesItem.length);
      }
      let x = {
        type: 'category',
        data: this.chartData.xData,
        axisLine: {
          show: true,
          lineStyle: {
            color: '#dcdcdc'
          }
        },
        axisTick: {
          show: false
        },

        axisLabel: {
          color: '#898989',
          interval: 0
        }
      };
      this.xData.push(x);
      this.option.xAxis = this.xData;
      let item = {
        type: 'bar',
        data: this.chartData.data,
        barWidth: 32,
        itemStyle: {
          normal: {
            color: '#8aa9f9',
            shadowColor: 'rgba(0,160,221,1)',
            shadowBlur: 0
          }
        },
        label: {
          normal: {
            show: true,
            formatter: '{c}',
            position: 'top',
            textStyle: {
              color: '#898989'
              // fontSize: 20
            }

          }
        }
      };
      this.seriesItem.push(item);
      this.option.series = this.seriesItem;
      this.renderCharts('logChar', this.option)
    },
    /*定时器：每分钟刷新*/
    timer() {
      return setTimeout(() => {
        this.drawChart()
      }, 60000)
    },
    /*销毁定时器*/
    destroyed() {
      clearTimeout(this.timer())
    }
  }
}
</script>
