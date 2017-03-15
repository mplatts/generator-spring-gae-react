'use strict';
var path = require('path');
var assert = require('yeoman-assert');
var helpers = require('yeoman-test');

describe('generator-react-thundr-gae:app', function () {
  before(function () {
    return helpers.run(path.join(__dirname, '../generators/app'))
      .withArguments(['skip-install'])
      .withPrompts({project: 'Test Project'})
      .toPromise();
  });

  it('creates files', function () {
    assert.file([
      'package.json',
      'pom.xml',
      'webpack.config.js',
      'README.md',
      '.editorconfig',
      '.gitignore',
      '.java-version',
      '.eslintrc',
      '.babelrc',
      'src/main/java/threewks/ApplicationModule.java',
      'src/main/java/threewks/controller/Controller.java',
      'src/main/java/threewks/controller/Routes.java',
      'src/main/resources/application.properties',
      'src/main/static/images/favicon/original.png',
      'src/main/static/index.ejs',
      'src/main/static/less/mixins/mixins.less',
      'src/main/static/less/mixins/variables.less',
      'src/main/static/less/styles/main.less',
      'src/main/static/javascript/index.jsx',
      'src/main/static/javascript/store.js',
      'src/main/static/javascript/routes.jsx',
      'src/main/static/javascript/components/App.jsx',
      'src/main/static/javascript/components/Layout.jsx',
      'src/main/static/javascript/components/pages/HomePage.jsx',
      'src/main/static/javascript/components/pages/NotFoundPage.jsx',
      'src/main/static/javascript/components/pages/index.jsx',
      'src/main/static/javascript/reducers/index.js',
      'src/main/webapp/WEB-INF/appengine-web.xml',
      'src/main/webapp/WEB-INF/datastore-indexes.xml',
      'src/main/webapp/WEB-INF/logging.properties',
      'src/main/webapp/WEB-INF/web.xml',
      'etc/3wks-eclipse-formatter.xml',
      'etc/3wks-eclipse.importorder',
      'etc/3wks-intellij-formatter.xml'
    ]);
  });
});
