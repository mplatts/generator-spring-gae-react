'use strict';
const Generator = require('yeoman-generator');
const chalk = require('chalk');
const yosay = require('yosay');
const mkdirp = require('mkdirp');
const _ = require('lodash');
const slugify = require('slugify');

module.exports = class extends Generator {
  prompting() {
    // Have Yeoman greet the user.
    this.log(yosay(
      'Welcome to the excellent ' + chalk.red('generator-thundr-gae-react') + ' generator!'
    ));

    const prompts = [
      {
        name: 'project',
        message: 'What is the name of this project?',
        store: true
      },
      {
        name: 'gitPath',
        message: 'What is the HTTPS git path for this project?',
        store: true
      }
    ];

    return this.prompt(prompts).then(props => {
      // To access props later use this.props.someAnswer;
      this.props = props;
    });
  }

  writing() {
    var context = Object.assign({
      _: _,
      slugify: function (value) {
        return slugify(value).toLowerCase();
      }
    }, this.props);

    this.fs.copy(
      this.templatePath('babelrc'),
      this.destinationPath('.babelrc')
    );
    this.fs.copy(
      this.templatePath('editorconfig'),
      this.destinationPath('.editorconfig')
    );
    this.fs.copy(
      this.templatePath('eslintrc'),
      this.destinationPath('.eslintrc')
    );
    this.fs.copy(
      this.templatePath('gitignore'),
      this.destinationPath('.gitignore')
    );
    this.fs.copy(
      this.templatePath('java-version'),
      this.destinationPath('.java-version')
    );
    this.fs.copy(
      this.templatePath('src'),
      this.destinationPath('src'),
      { globOptions: { ignore: '**/_*' } }
    );
    this.fs.copy(
      this.templatePath('etc'),
      this.destinationPath('etc')
    );

    this.fs.copyTpl(
      this.templatePath('_pom.xml'),
      this.destinationPath('pom.xml'),
      context
    );
    this.fs.copyTpl(
      this.templatePath('_package.json'),
      this.destinationPath('package.json'),
      context
    );
    this.fs.copyTpl(
      this.templatePath('_webpack.config.js'),
      this.destinationPath('webpack.config.js'),
      context
    );
    this.fs.copyTpl(
      this.templatePath('_README.md'),
      this.destinationPath('README.md'),
      context
    );
    this.fs.copyTpl(
      this.templatePath('src/main/static/javascript/components/_Layout.jsx'),
      this.destinationPath('src/main/static/javascript/components/Layout.jsx'),
      context
    );

    mkdirp('src/main/static/');
    mkdirp('src/main/static/fonts');
    mkdirp('src/main/static/images');
    mkdirp('src/test/java/');
  }

  install() {
    if (!this.options['skip-install']) {
      this.spawnCommand('mvn', ['install']);
    }
  }
};
