import React from 'react';
import { Provider } from 'react-redux';

import store from '../store';
import getRoutes from '../routes';

const App = () => (
  <Provider store={store}>
    { getRoutes(store.getState, store.dispatch) }
  </Provider>
);

export default App;
