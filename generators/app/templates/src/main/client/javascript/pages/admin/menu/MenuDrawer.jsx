/* eslint-disable jsx-a11y/no-static-element-interactions, jsx-a11y/click-events-have-key-events */
import React from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { func, object, bool } from 'prop-types';
import { Divider, Drawer, ListSubheader, List, ListItem, ListItemIcon, ListItemText } from 'material-ui';
import ExitIcon from '@material-ui/icons/ExitToApp';
import DashboardIcon from '@material-ui/icons/Dashboard';
import PersonIcon from '@material-ui/icons/Person';
import PeopleIcon from '@material-ui/icons/People';
import ProfileCard from './ProfileCard';
import * as authActions from '../../../actions/auth';
import { getLoggedInUser, getOpenDrawer } from '../../../reducers';
import { closeDrawer as closeDrawerAction } from '../../../actions/drawer';

const MenuDrawer = ({
  loggedInUser,
  logout,
  navigateTo,
  closeDrawer,
  isOpen,
  ...rest
}) => (
  <Drawer onClose={closeDrawer} open={isOpen} {...rest}>
    <div onClick={closeDrawer}>
      <ProfileCard user={loggedInUser}/>

      <Divider/>
      <List>
        <ListItem
          button
          onClick={() => navigateTo('/admin')}
        >
          <ListItemIcon><DashboardIcon/></ListItemIcon>
          <ListItemText primary="Dashboard"/>
        </ListItem>
        <ListItem
          button
          onClick={() => navigateTo('/admin/users')}
        >
          <ListItemIcon><PeopleIcon/></ListItemIcon>
          <ListItemText primary="Manage users"/>
        </ListItem>
      </List>

      <Divider/>
      <ListSubheader>My account</ListSubheader>

      <List>
        <ListItem
          button
          onClick={() => navigateTo(`/admin/users/${loggedInUser.id}`)}
        >
          <ListItemIcon><PersonIcon/></ListItemIcon>
          <ListItemText primary="Profile"/>
        </ListItem>
        <ListItem
          button
          onClick={() => logout()}
        >
          <ListItemIcon><ExitIcon/></ListItemIcon>
          <ListItemText primary="Sign out"/>
        </ListItem>
      </List>
    </div>
  </Drawer>
);

MenuDrawer.propTypes = {
  loggedInUser: object,
  logout: func.isRequired,
  navigateTo: func.isRequired,
  closeDrawer: func.isRequired,
  isOpen: bool.isRequired,
};

MenuDrawer.defaultProps = {
  loggedInUser: {},
};

const mapStateToProps = state => ({
  loggedInUser: getLoggedInUser(state),
  isOpen: getOpenDrawer(state) === 'admin',
});

const mapDispatchToProps = dispatch => ({
  logout: () => dispatch(authActions.logout()).then(() => dispatch(push('/'))),
  navigateTo: path => dispatch(push(path)),
  closeDrawer: () => dispatch(closeDrawerAction()),
});

const mergeProps = (stateProps, dispatchProps, ownProps) => ({
  ...stateProps,
  ...dispatchProps,
  ...ownProps,
  navigateTo: (path) => {
    dispatchProps.navigateTo(path);
  },
});

export default connect(mapStateToProps, mapDispatchToProps, mergeProps)(MenuDrawer);
