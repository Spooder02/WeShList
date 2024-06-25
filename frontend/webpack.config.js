const path = require('path');

module.exports = {
  // 기타 설정...
  resolve: {
    fallback: {
      url: require.resolve('url/'),
      fs: false,
      "https": false,
      util: require.resolve("util/")
    },
    extensions: ['.ts', '.tsx', '.js', '.jsx', '...']
  },
  plugins: [
    new webpack.ProvidePlugin({
      fs: 'empty'
    })
  ],
  "compilerOptions": {
    "allowJs": true
  }
};