import { createStore, applyMiddleware } from 'redux';
import { browserHistory } from 'react-router';
import thunk from 'redux-thunk';
import reduxCatch from 'redux-catch';
import { routerMiddleware, syncHistoryWithStore } from 'react-router-redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import rootReducer from './reducers';
import { logReduxError } from './util/errors';

const middleware = [routerMiddleware(browserHistory), thunk, reduxCatch(logReduxError)];

const store = createStore(rootReducer, composeWithDevTools(applyMiddleware(...middleware)));

// Create an enhanced history that syncs navigation events with the store
export const history = syncHistoryWithStore(browserHistory, store);

export default store;
