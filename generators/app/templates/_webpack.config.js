/* eslint-disable comma-dangle */

const fs = require('fs');
const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const FaviconsWebpackPlugin = require('favicons-webpack-plugin');
const parseString = require('xml2js').parseString;

const sourceDir = path.resolve(__dirname, 'src/main/static');

const targetDir = (function getTargetDir() {
  const pomXml = fs.readFileSync('pom.xml', 'utf8');

  let artifactId;
  let version;
  parseString(pomXml, (err, result) => {
    artifactId = result.project.artifactId[0];
    version = result.project.version[0];
  });

  return path.resolve(__dirname, 'target/' + artifactId + '-' + version);
}());



// Environment flags
const environment = process.env.NODE_ENV || 'development';
const isDevelopment = environment === 'development';

/**
 * Base Webpack configuration.
 */
const webpackConfig = {
  entry: [
    './src/main/static/javascript/index.jsx',
  ],

  output: {
    filename: 'bundle.js',
    path: targetDir + '/static',
    publicPath: '/static/',
  },

  // Enable sourcemaps for debugging webpack's output.
  devtool: 'inline-source-map',

  resolve: {
    extensions: ['.webpack.js', '.web.js', '.js', '.jsx'],
  },

  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: JSON.stringify(process.env.NODE_ENV || 'development'),
      },
      DEVELOPMENT: isDevelopment,
    }),

    // Automatically generates index.html
    new HtmlWebpackPlugin({
      title: '<%= project %>',

      template: path.resolve(sourceDir, 'index.ejs'),

      filename: targetDir + '/index.html',
    }),

    new FaviconsWebpackPlugin({
      // The source icon
      logo: path.resolve(sourceDir, 'images/favicon/original.png'),

      // The prefix for all image files (might be a folder or a name)
      prefix: 'images/favicon-[hash]/',

      // Generate a cache file with control hashes and
      // don't rebuild the favicons until those hashes change
      persistentCache: true,

      // Inject the html into the html-webpack-plugin
      inject: true,
    }),
  ],

  module: {
    rules: [
      // All output '.js' files will have any sourcemaps re-processed by 'source-map-loader'.
      {
        enforce: 'pre',
        test: /\.js$/,
        use: 'source-map-loader',
      },

      // Load library CSS styles
      {
        test: /\.css$/,
        use: [
          'style-loader',
          { loader: 'css-loader', options: { sourceMap: true } },
        ],
        include: /node_modules/,
      },

      // Load Less files
      {
        test: /\.less$/,
        include: [
          path.resolve(sourceDir, 'less'),
        ],
        use: [
          'style-loader',
          { loader: 'css-loader', options: { sourceMap: true, importLoaders: 1 } },
          {
            loader: 'postcss-loader',
            options: {
              plugins() {
                return [require('autoprefixer')];  // eslint-disable-line global-require
              },
            },
          },
          { loader: 'less-loader', options: { sourceMap: true } },
        ],
      },

      // Image loading. Inlines small images as data URIs (i.e. < 10k).
      {
        test: /\.(png|jpg|jpeg|gif|svg)$/,
        loader: 'url-loader',
        options: {
          name: 'images/[name].[hash].[ext]',
          limit: 10000,
        },
      },

      // Inline small woff files and output them at fonts/.
      {
        test: /\.woff2?$/,
        loader: 'url-loader',
        options: {
          name: 'fonts/[hash].[ext]',
          limit: 50000,
          mimetype: 'application/font-woff',
        },
      },

      // Load other font file types at fonts/
      {
        test: /\.(ttf|svg|eot)$/,
        loader: 'file-loader',
        options: {
          name: 'fonts/[hash].[ext]',
        },
      },
    ],
  },
};

if (isDevelopment) {
  webpackConfig.entry.unshift(
    'react-hot-loader/patch', // activate HMR for React

    'webpack-dev-server/client?http://localhost:3000',  // bundle the client for webpack-dev-serve

    'webpack/hot/only-dev-server' // bundle the client for hot reloading
  );

  webpackConfig.plugins.push(
    // Enable HMR globally
    new webpack.HotModuleReplacementPlugin(),

    // Prints more readable module names in the browser console on HMR updates
    new webpack.NamedModulesPlugin()
  );

  webpackConfig.module.rules.push(
    {
      test: /\.jsx?$/,
      include: path.resolve(sourceDir, 'javascript'),
      use: [
        'react-hot-loader/webpack',  // Enable HMR support in loader chain
        'babel-loader'
      ],
    }
  );

  webpackConfig.devServer = {
    port: 3000,
    contentBase: targetDir,
    hot: true,
    historyApiFallback: true,
    proxy: {
      '/api': 'http://localhost:8080',
      '**/*.pdf': 'http://localhost:8080',
    },
  };
} else {
  webpackConfig.module.rules.push(
    {
      test: /\.jsx?$/,
      include: path.resolve(sourceDir, 'javascript'),
      use: [
        'babel-loader'
      ],
    }
  );

  webpackConfig.plugins.push(
    // Minify JS in non-development environments
    new webpack.optimize.UglifyJsPlugin()
  );
}

module.exports = webpackConfig;
