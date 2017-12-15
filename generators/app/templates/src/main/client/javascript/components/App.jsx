import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import React from 'react';
import { AppContainer } from 'react-hot-loader';
import { IntlProvider } from 'react-intl';
import { Provider } from 'react-redux';
import getRoutes from '../routes';
import store from '../store';
import theme from '../theme';

const App = () => (
  <AppContainer>
    <Provider store={store}>
      <IntlProvider locale="en-AU">
        <MuiThemeProvider muiTheme={getMuiTheme(theme)}>
          {getRoutes(store.getState, store.dispatch)}
        </MuiThemeProvider>
      </IntlProvider>
    </Provider>
  </AppContainer>
);

export default App;
