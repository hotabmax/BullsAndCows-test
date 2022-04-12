const path = require('path');
module.exports = {
    mode: 'development',
    entry: {
        renderAutorizationAndRegistration:'./js/interfaceAutorizationAndRegistration/render.js',
        renderPlayer: './js/interfacePlayer/render.js',
    },
    output: {
        path: path.resolve(__dirname, 'js/bundles'),
        filename: '[name].js',
        library: '[name]'
    },
    module: {
        rules: [
            {
                test: /\.m?js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: "babel-loader"
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            }
        ]
    }
}