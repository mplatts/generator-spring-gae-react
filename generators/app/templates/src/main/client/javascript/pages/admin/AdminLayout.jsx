import React from 'react';
import { connect } from 'react-redux';
import Alert from 'react-s-alert';
import { arrayOf, node, oneOfType, func } from 'prop-types';
import { AppBar, Toolbar, IconButton, Typography } from 'material-ui';
import MenuIcon from '@material-ui/icons/Menu';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import MenuDrawer from './menu/MenuDrawer';
import { openDrawer as openDrawerAction } from '../../actions/drawer';

const AdminLayout = ({ children, openDrawer }) => (
  <div className="admin-layout">
    <Alert effect="slide" position="bottom-right" stack />

    <AppBar position="static">
      <Toolbar>
        <IconButton aria-label="Menu"color="inherit" onClick={openDrawer}>
          <MenuIcon />
        </IconButton>
        <Typography variant="title" color="inherit">
          Material UI Next
        </Typography>
      </Toolbar>
    </AppBar>

    <MenuDrawer />

    <div className="main">{children}</div>
  </div>);

AdminLayout.propTypes = {
  children: oneOfType([node, arrayOf(node)]).isRequired,
  openDrawer: func.isRequired,
};

export const mapDispatchToProps = dispatch => ({
  openDrawer: () => dispatch(openDrawerAction('admin')),
});

export default connect(null, mapDispatchToProps)(AdminLayout);
