module.exports = {
    devServer: {
        proxy: 'http://localhost:8001',
        port: 7998,
        open: true,
        hot: true// 热更新
    },
    lintOnSave: false
}
