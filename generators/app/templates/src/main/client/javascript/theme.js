import Color from 'color';
import lightBaseTheme from 'material-ui/styles/baseThemes/lightBaseTheme';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';

export default {
  ...lightBaseTheme,
  palette: {
    ...lightBaseTheme.palette,
    primary1Color: Color('#8bd1cc').darken(0.15).hex(),
    accent1Color: '#fdbe5a',
    accent2Color: '#EB4D5C',
  },
};
