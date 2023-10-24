module.exports = {
  publicPath: '/',
  devServer: {
    port: 8080,
    proxy: {
      '/api': {
        target: 'http://localhost/api/',
        changeOrigin: true
      }
    }
  },
  productionSourceMap: false
}