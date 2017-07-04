import React from 'react';
import { AppContainer } from 'react-hot-loader';
import { Provider } from 'react-redux';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

import store from '../store';
import getRoutes from '../routes';

const App = () => (
  <AppContainer>
    <Provider store={store}>
      <MuiThemeProvider>
        { getRoutes(store.getState, store.dispatch) }
      </MuiThemeProvider>
    </Provider>
  </AppContainer>
);

export default App;
