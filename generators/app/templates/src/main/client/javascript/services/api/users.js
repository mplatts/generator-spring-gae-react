import { formEncode, request, requestJSON } from './http';

const invite = userDetails =>
  requestJSON('/users/invite', 'POST', userDetails);

const redeemInvite = (inviteCode, userDetails) =>
  requestJSON(`/users/invite/${inviteCode}`, 'POST', userDetails);

const list = () =>
  requestJSON('/users');

const login = credentials =>
  request('/login', 'POST', formEncode({ ...credentials, 'remember-me': true }), { 'Content-Type': 'application/x-www-form-urlencoded' });

const logout = () =>
  request('/logout', 'POST');

const me = () =>
  requestJSON('/users/me', 'GET');

const get = userId =>
  requestJSON(`/users/${userId}`);

const save = user =>
  requestJSON(`/users/${user.id}`, 'PUT', user);

export default {
  get,
  invite,
  list,
  login,
  logout,
  me,
  redeemInvite,
  save,
};
