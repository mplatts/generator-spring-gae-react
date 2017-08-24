import Color from 'color';
import lightBaseTheme from 'material-ui/styles/baseThemes/lightBaseTheme';

export default {
  ...lightBaseTheme,
  palette: {
    ...lightBaseTheme.palette,
    primary1Color: Color('#8bd1cc').darken(0.15).hex(),
    accent1Color: '#EB4D5C',
    accent2Color: '#fdbe5a',
  },
};
