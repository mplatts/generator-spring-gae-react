import React from 'react';
import Avatar from 'react-avatar';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { arrayOf, func, node, object, oneOfType } from 'prop-types';
import { AppBar, IconButton, IconMenu, MenuItem } from 'material-ui';
import { getLoggedInUser } from '../../reducers';
import api from '../../services/api';

const AdminLayout = ({ children, loggedInUser, logout }) => (
  <div className="admin-layout">
    <AppBar
      title="<%= projectName %>"
      iconElementRight={
        <IconMenu
          iconButtonElement={
            <IconButton tooltip={loggedInUser.name || loggedInUser.email}>
              <Avatar name={loggedInUser.name} email={loggedInUser.email} round size={32} />
            </IconButton>
          }
          anchorOrigin={{ horizontal: 'right', vertical: 'top' }}
          targetOrigin={{ horizontal: 'right', vertical: 'top' }}
        >
          <MenuItem primaryText="Sign out" onClick={logout} />
        </IconMenu>
      }
    />
    <div className="main">
      { children }
    </div>
  </div>
);

AdminLayout.propTypes = {
  children: oneOfType([node, arrayOf(node)]).isRequired,
  loggedInUser: object,
  logout: func.isRequired,
};

AdminLayout.defaultProps = {
  loggedInUser: {},
};

const mapStateToProps = state => ({
  loggedInUser: getLoggedInUser(state),
});

const mapDispatchToProps = dispatch => ({
  logout: () => api.users.logout().then(() => dispatch(push('/'))),
});

export default connect(mapStateToProps, mapDispatchToProps)(AdminLayout);
