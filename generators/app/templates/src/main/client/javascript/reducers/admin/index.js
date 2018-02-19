import { combineReducers } from "redux";

const users = (state = { inviteUserDialogOpen: false, users: [] }, action) => {
  switch (action.type) {
    case 'OPEN_INVITE_USER_DIALOG':
      return {
        ...state,
        inviteUserDialogOpen: true,
      };
    case 'CLOSE_INVITE_USER_DIALOG':
      return {
        ...state,
        inviteUserDialogOpen: false,
      };
    case 'FETCH_USERS_SUCCESS':
      return {
        ...state,
        users: action.response,
      };
    case 'INVITE_USER_SUCCESS':
      return {
        ...state,
        inviteUserDialogOpen: false,
      };
    default: return state;
  }
};

export const getUsers = state =>
  state.users;

export default combineReducers({
  users,
});
