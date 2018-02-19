import { asyncAction } from './actions';
import usersApi from '../services/api/users';

export const inviteUser = userInvitation => asyncAction(
  'INVITE_USER',
  usersApi.invite(userInvitation), // api call goes here
  {}, // Options
);

export const fetchUsers = () => asyncAction(
  'FETCH_USERS',
  usersApi.list(),
  {},
);

export const openInviteUserDialog = () => ({ type: 'OPEN_INVITE_USER_DIALOG' });

export const closeInviteUserDialog = () => ({ type: 'CLOSE_INVITE_USER_DIALOG' });

