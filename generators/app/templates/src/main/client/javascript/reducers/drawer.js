const initialState = {};

export default (state = initialState, action) => {
  switch (action.type) {
    case 'OPEN_DRAWER':
      return {
        name: action.name,
      };
    case 'CLOSE_DRAWER':
      return {};
    default:
      return state;
  }
};

export const getOpenDrawer = state => state.name;
