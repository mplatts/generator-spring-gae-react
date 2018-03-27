import { combineReducers } from 'redux';
import { reducer as form } from 'redux-form';
import { routerReducer as routing } from 'react-router-redux';
import admin, * as fromAdmin from './admin';
import auth, * as fromAuth from './auth';
import users, * as fromUsers from './users';
import drawer, * as fromDrawer from './drawer';

/**
 * Root reducer for the app.
 */
const rootReducer = combineReducers({
  form,
  routing,
  auth,
  admin,
  users,
  drawer,
});

export const getLoggedInUser = state =>
  fromAuth.getUser(state.auth);

export const getIsAuthenticated = state =>
  fromAuth.getIsAuthenticated(state.auth);

export const isInviteUserDialogOpen = state => fromAdmin.isInviteUserDialogOpen(state.admin);
export const getUsers = state => fromUsers.getAll(state.users);

export const getOpenDrawer = state =>
  fromDrawer.getOpenDrawer(state.drawer);

export default rootReducer;
