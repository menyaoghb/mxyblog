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
      <el-radio-group v-model="tableName">
        <el-radio v-for="item in tableNameList" :key="item.key" :label="item.key" class="el-radio-role">{{
            item.name
          }}
        </el-radio>
      </el-radio-group>
    </div>

    <el-divider></el-divider>
    <div style="text-align: center">
      <el-button style="margin-top: 12px;" @click="next">{{ clicked ? "下载" : "下一步" }}</el-button>
    </div>
  </div>
</template>

<script>
import {getTableName, codeGenerate} from "@/api/assist/codeGenerator/codeGenerator";

export default {
  data() {
    return {
      tableNameList: [],
      tableName: '',
      active: 0,
      clicked: false
    };
  },
  created() {
    this.getTableName();
  },
  methods: {
    // 请求查询表名
    getTableName() {
      getTableName().then(response => {
        this.tableNameList = response.data
      })
    },
    // 发起生成请求
    codeGenerate() {
      codeGenerate({tableName: this.tableName}).then(response => {
        this.active++;
        this.active++;
        this.active++;
        this.clicked = true;
      })
    },
    // 步骤条
    next() {
      if (this.tableName === '') {
        this.$message({
          message: '请选择需要生成的表名',
          type: 'warning'
        });
        return;
      }
      if (this.active === 0) {
        this.codeGenerate();
      }
      if (this.active++ > 3) {
        this.active = 0;
        this.clicked = false;
      }
    }
  }
}
</script>
<style>
.el-radio-group {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  place-items: start left;
}

.el-radio-group > * {
  margin-bottom: 20px;
}

.el-radio__input.is-checked + .el-radio__label {
  color: #67c23a;
}


.el-radio__input.is-checked .el-radio__inner {
  border-color: #67c23a;
  background: #67c23a;
}

.el-radio__inner:hover {
  border-color: #67c23a;
}


</style>
