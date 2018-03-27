import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Reboot from 'material-ui/CssBaseline';
import React, { Fragment } from 'react';
import { AppContainer } from 'react-hot-loader';
import { IntlProvider } from 'react-intl';
import { Provider } from 'react-redux';
import getRoutes from '../routes';
import store from '../store';
import theme from '../theme';

const App = () => (
  <Fragment>
    <Reboot />
    <AppContainer>
      <Provider store={store}>
        <IntlProvider locale="en-AU">
          <MuiThemeProvider theme={theme}>
            {getRoutes(store.getState, store.dispatch)}
          </MuiThemeProvider>
        </IntlProvider>
      </Provider>
    </AppContainer>
  </Fragment>);

export default App;
