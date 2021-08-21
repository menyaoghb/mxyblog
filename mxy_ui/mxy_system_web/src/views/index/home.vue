<template>
  <el-container>
    <!--<el-header>Header</el-header>-->
    <el-main>
      <div class="d1">
        <el-image

          src="http://xyule.top/home_page.jpg"
          fit="fit"></el-image>
      </div>

    </el-main>
    <!--<el-footer>Footer</el-footer>-->
  </el-container>
</template>


<script>
  export default {
    data(){
      const staticData= []
      return{
        // 有两层数据，渲染表格
        months:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
        tableData:staticData,
      }
    }

  }
</script>
<style>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>
