import { normalize } from 'normalizr';
import schemas from '../schemas';
import { asyncAction } from './actions';
import usersApi from '../services/api/users';

export const inviteUser = userInvitation => asyncAction(
  'INVITE_USER',
  usersApi.invite(userInvitation),
  {
    responseTransformer: user => normalize(user, schemas.user),
  },
);

export const fetchUsers = () => asyncAction(
  'FETCH_USERS',
  usersApi.list(),
  {
    responseTransformer: users => normalize(users, schemas.arrayOfUsers),
  },
);

export const openInviteUserDialog = () => ({ type: 'OPEN_INVITE_USER_DIALOG' });

export const closeInviteUserDialog = () => ({ type: 'CLOSE_INVITE_USER_DIALOG' });

