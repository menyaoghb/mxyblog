<template>
  <div class="app-container">
    <iframe id="template-iframe" ref="iframe" src="http://localhost:8001/druid/login.html"></iframe>
  </div>
</template>

<script>

export default {
  data() {
    return {}
  },
  created() {
  },
  methods: {}
}
</script>
<style>
#template-iframe {
  width: 100%;
  height: 685px;
}
</style>
