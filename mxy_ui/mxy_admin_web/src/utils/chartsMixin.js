import * as echarts from 'echarts';

const chartsList = []
export default {
  computed: {
    sidebar() {
      return this.$store.state.settings.sidebar
    },
    layoutType() {
      return this.$store.state.settings.layoutType
    }
  },
  watch: {
    sidebar: {
      handler() {
        setTimeout(() => {
          this.chartsResize()
        }, 400)
      },
      deep: true
    },
    layoutType() {
      setTimeout(() => {
        this.chartsResize()
      }, 400)
    }
  },
  mounted() {
    window.onresize = () => {
      this.chartsResize()
    }
  },
  methods: {
    chartsResize() {
      for (const e of chartsList) {
        e.resize()
      }
    },
    renderCharts(id, option) {
      setTimeout(() => {
        const c = echarts.init(document.getElementById(id.toString()))
        c.setOption(option)
        chartsList.push(c)
        return {chart: c, option: option}
      })
    }
  }
}
