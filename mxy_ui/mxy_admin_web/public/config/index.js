(function (window) {
  window.SITE_CONFIG = {
    version: 'v1.0.0',
  };

  function getLocalUrl() {
    return window.location.protocol + '//' + window.location.host
  }

  window.initSysUrl = function (env) {
    window.ENV = env || 'development'
    // 本地
    window.SITE_CONFIG['systemUrl'] = 'http://localhost:8001'
    // 生产
    // window.SITE_CONFIG['systemUrl'] = 'http://124.221.76.235:8088'
  }
})(window)
