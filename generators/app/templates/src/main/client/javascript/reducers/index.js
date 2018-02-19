import { combineReducers } from 'redux';
import { reducer as form } from 'redux-form';
import { routerReducer as routing } from 'react-router-redux';
import admin, * as fromAdmin from './admin';
import auth, * as fromAuth from './auth';

/**
 * Root reducer for the app.
 */
const rootReducer = combineReducers({
  form,
  routing,
  auth,
  admin,
});

export const getLoggedInUser = state =>
  fromAuth.getUser(state.auth);

export const getIsAuthenticated = state =>
  fromAuth.getIsAuthenticated(state.auth);

export const getUsers = state =>
  fromAdmin.getUsers(state.admin);

export default rootReducer;
