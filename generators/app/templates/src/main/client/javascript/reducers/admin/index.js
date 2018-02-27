import { combineReducers } from 'redux';

const inviteUsers = (state = { dialogOpen: false }, action) => {
  switch (action.type) {
    case 'OPEN_INVITE_USER_DIALOG':
      return {
        ...state,
        dialogOpen: true,
      };
    case 'CLOSE_INVITE_USER_DIALOG':
      return {
        ...state,
        dialogOpen: false,
      };
    case 'INVITE_USER_SUCCESS':
      return {
        ...state,
        dialogOpen: false,
      };
    default: return state;
  }
};

export const isInviteUserDialogOpen = state => state.inviteUsers.dialogOpen;

export default combineReducers({
  inviteUsers,
});
