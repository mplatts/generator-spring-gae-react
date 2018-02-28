'use strict';
const Generator = require('yeoman-generator');
const chalk = require('chalk');
const yosay = require('yosay');
const mkdirp = require('mkdirp');
const slugify = value => require('slugify')(value).toLowerCase();
const _ = require('lodash');
const randomString = require('random-string');

const makeKey = () => randomString({
  length: 64,
  special: true,
});

module.exports = class extends Generator {
  prompting() {
    // Have Yeoman greet the user.
    this.log(yosay(
      'Welcome to the excellent ' + chalk.red('generator-spring-gae-react') + ' generator!'
    ));

    const prompts = [
      {
        name: 'project',
        message: 'What is the ID of this project?',
        store: true,
        default: slugify(this.appname)
      },
      {
        name: 'projectName',
        message: 'What is the name of this project?',
        store: true,
        default: _.startCase(this.appname)
      },
      {
        name: 'gitPath',
        message: 'What is the HTTPS git path for this project?',
        store: true
      },
      {
        name: 'adminEmail',
        message: 'What email address should we use to create the initial admin login?',
        store: true
      },
      {
        name: 'emailFromAddress',
        message: 'What email address would you like system emails to appear to come from?',
        store: true,
        default: `${slugify(this.appname)}@email.org`
      }
    ];

    return this.prompt(prompts).then(props => {
      // To access props later use this.props.someAnswer;
      this.props = props;
    });
  }

  writing() {
    const context = Object.assign({}, this.props, {
      project: slugify(this.props.project),
      slugify,
      localKey: makeKey(),
      devKey: makeKey(),
      uatKey: makeKey(),
      prodKey: makeKey(),
      _
    });

    const copyTpl = (src, dest) => {
      const destName = dest || src;
      const srcParam = !_.isArray(src) ?
        this.templatePath(src) :
        _.map(src, entry => _.startsWith(entry, '!') ? entry : this.templatePath(entry));

      return this.fs.copyTpl(srcParam, this.destinationPath(destName), context);
    };

    const copy = (src, dest) => {
      const destName = dest || src;
      return this.fs.copy(this.templatePath(src), this.destinationPath(destName));
    };

    copy('babelrc', '.babelrc');
    copy('editorconfig', '.editorconfig');
    copy('eslintrc', '.eslintrc');
    copy('gitignore', '.gitignore');
    copy('java-version', '.java-version');
    copy('etc');
    copy('src');

    copyTpl('pom.xml');
    copyTpl('package.json');
    copyTpl('webpack.config.js');
    copyTpl('README.md');
    copyTpl('src/main/client/javascript/pages/admin/AdminLayout.jsx');
    copyTpl('src/main/resources');

    mkdirp('src/main/client/fonts');
  }

  install() {
    if (!this.options['skip-install']) {
      this.spawnCommand('mvn', ['install']);
    }
  }
};
