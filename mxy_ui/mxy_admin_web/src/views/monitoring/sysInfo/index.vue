<template>
  <div class="app-container">
    <el-descriptions class="margin-top" title="Cpu" :column="4" size="medium" border>
      <el-descriptions-item label="CPU核心数">{{SysInfoJson.cpu.cpuNum}}</el-descriptions-item>
      <el-descriptions-item label="CPU总的使用率">{{SysInfoJson.cpu.totalUsed}}%</el-descriptions-item>
      <el-descriptions-item label="CPU系统使用率">{{SysInfoJson.cpu.sysUsed}}%</el-descriptions-item>
      <el-descriptions-item label="CPU用户使用率">{{SysInfoJson.cpu.userUsed}}%</el-descriptions-item>
      <el-descriptions-item label="CPU当前等待率">{{SysInfoJson.cpu.wait}}%</el-descriptions-item>
      <el-descriptions-item label="CPU当前空闲率">{{SysInfoJson.cpu.free}}%</el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
    <el-descriptions class="margin-top" title="Jvm" :column="4" size="medium" border>
      <el-descriptions-item label="JDK版本">{{SysInfoJson.jvm.jdkVersion}}</el-descriptions-item>
      <el-descriptions-item label="JRE路径">{{SysInfoJson.jvm.jreHome}}</el-descriptions-item>
      <el-descriptions-item label="启动时间">{{SysInfoJson.jvm.startTime}}</el-descriptions-item>
      <el-descriptions-item label="运行时长">{{SysInfoJson.jvm.runTime}}</el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
    <el-descriptions class="margin-top" title="內存" :column="4" size="medium" border>
      <el-descriptions-item label="内存总量">{{SysInfoJson.mem.total}}G</el-descriptions-item>
      <el-descriptions-item label="已用内存">{{SysInfoJson.mem.used}}G</el-descriptions-item>
      <el-descriptions-item label="剩余内存">{{SysInfoJson.mem.free}}G</el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
    <el-descriptions class="margin-top" title="系统" :column="4" size="medium" border>
      <el-descriptions-item label="服务器名称">{{SysInfoJson.sys.computerName}}</el-descriptions-item>
      <el-descriptions-item label="服务器IP">{{SysInfoJson.sys.computerIp}}</el-descriptions-item>
      <el-descriptions-item label="项目路径">{{SysInfoJson.sys.userDir}}</el-descriptions-item>
      <el-descriptions-item label="操作系统">{{SysInfoJson.sys.osName}}</el-descriptions-item>
      <el-descriptions-item label="系统架构">{{SysInfoJson.sys.osArch}}</el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
    <el-descriptions class="margin-top" title="网络" :column="4" size="medium" border>
      <el-descriptions-item label="网络接口名称">{{SysInfoJson.network.name}}</el-descriptions-item>
      <el-descriptions-item label="MAC地址">{{SysInfoJson.network.mac}}</el-descriptions-item>
      <el-descriptions-item label="IP地址">{{SysInfoJson.network.ip}}</el-descriptions-item>
      <el-descriptions-item label="数据包接收量">{{SysInfoJson.network.packetsReceiveAmount}}</el-descriptions-item>
      <el-descriptions-item label="字节接收量">{{SysInfoJson.network.bytesReceiveAmount}}</el-descriptions-item>
      <el-descriptions-item label="错误数据包接收量">{{SysInfoJson.network.errorPacketsReceiveAmount}}</el-descriptions-item>
      <el-descriptions-item label="数据包发送量">{{SysInfoJson.network.packetsSendAmount}}</el-descriptions-item>
      <el-descriptions-item label="字节发送量">{{SysInfoJson.network.bytesSendAmount}}</el-descriptions-item>
      <el-descriptions-item label="错误数据包发送量">{{SysInfoJson.network.errorPacketsSendAmount}}</el-descriptions-item>
      <el-descriptions-item label="上行bps">{{SysInfoJson.network.uploadBps}}</el-descriptions-item>
      <el-descriptions-item label="下行bps">{{SysInfoJson.network.downloadBps}}</el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
    <el-table
      :data="SysInfoJson.sysFiles"
      style="width: 100%">
      <el-table-column prop="dirName" label="盘符路径" width="180"></el-table-column>
      <el-table-column prop="sysTypeName" label="盘符类型" width="180"></el-table-column>
      <el-table-column prop="typeName" label="盘符名称" width="180"></el-table-column>
      <el-table-column prop="total" label="总磁盘大小" width="180"></el-table-column>
      <el-table-column prop="free" label="剩余磁盘大小" width="180"></el-table-column>
      <el-table-column prop="used" label="已经使用磁盘量" width="180"></el-table-column>
      <el-table-column prop="usage" label="磁盘使用率" width="180"></el-table-column>
    </el-table>


  </div>
</template>

<script>
import {sysInfoDetail} from "@/api/monitoring/sysInfo";

export default {
  name: 'SysInfo',
  data() {
    return {
      SysInfoJson: undefined
    }
  },
  created() {
    this.sysInfoDetail();
  },
  methods: {
    /*输入建议查询*/
    sysInfoDetail() {
      sysInfoDetail().then(response => {
        this.SysInfoJson = response.data;
      })
    }
  }
}
</script>
<style>
.my-label {
  background: aliceblue;
}
</style>
