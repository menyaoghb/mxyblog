<template>
  <el-table :data="list" style="width: 100%;padding-top: 15px;">
    <el-table-column label="最新文章" min-width="200">
      <template slot-scope="scope">
        {{ scope.row.order_no | orderNoFilter }}
      </template>
    </el-table-column>
    <el-table-column label="阅读数" width="195" align="center">
      <template slot-scope="scope">
        {{ scope.row.price | toThousandFilter }}
      </template>
    </el-table-column>
    <el-table-column label="发布时间" width="200" align="center">
      <template slot-scope="{row}">
        <el-tag :type="row.status | statusFilter">
          {{ row.status }}
        </el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { transactionList } from '@/api/remote-search'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        success: 'success',
        pending: 'danger'
      }
      return statusMap[status]
    },
    orderNoFilter(str) {
      return str.substring(0, 30)
    }
  },
  data() {
    return {
      list: [{
        order_no:"云博客●第一版发布",
        price:"38972",
        status:"2021-07-12 20:56:09"
      }]
    }
  },
  created() {
    // this.fetchData()
  },
  methods: {
    fetchData() {
      transactionList().then(response => {
        this.list = response.data.items.slice(0, 8)
      })
    }
  }
}
</script>
