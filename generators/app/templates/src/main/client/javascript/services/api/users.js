import { requestJSON } from './http';

const list = () =>
  requestJSON('/users');

const login = credentials =>
  requestJSON('/users/login', 'POST', credentials);

const logout = () =>
  requestJSON('/users/logout', 'POST');

const me = () =>
  requestJSON('/users/me', 'GET');

export default {
  list,
  login,
  logout,
  me,
};
