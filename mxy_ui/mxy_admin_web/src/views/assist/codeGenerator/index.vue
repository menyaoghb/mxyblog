<template>
  <div class="app-container">
    <div style="text-align: center;color: #67C23A">
      <h1>代码生成功能</h1>
    </div>
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="选择需要生成的表名"></el-step>
      <el-step title="开始生成..."></el-step>
      <el-step title="生成中..."></el-step>
      <el-step title="生成完毕..."></el-step>
    </el-steps>
    <el-divider></el-divider>
    <div style="text-align: center">
      <el-select v-model="value" filterable placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </div>

    <el-divider></el-divider>
    <div style="text-align: center">
      <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
    </div>
  </div>
</template>

<script>
import {getTableName, codeGenerate} from "@/api/assist/codeGenerator/codeGenerator";

export default {
  data() {
    return {
      options: [],
      value: '',
      active: 0
    };
  },
  created() {
    this.getTableName();
  },
  methods: {
    // 请求查询表名
    getTableName() {
      getTableName().then(response => {
        this.options = response.data
      })
    },
    // 发起生成请求
    codeGenerate() {
      codeGenerate({tableName:this.value}).then(response => {
        this.active++;
      })
    },
    // 步骤条
    next() {
      if (this.active === 0) {
        this.codeGenerate();
      }
      if (this.active++ > 3) this.active = 0;
    }
  }
}
</script>
<style>

</style>
