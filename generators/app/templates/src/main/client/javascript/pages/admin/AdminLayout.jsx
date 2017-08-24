import React from 'react';
import PropTypes from 'prop-types';
import { AppBar } from 'material-ui';

const AdminLayout = ({ children }) => (
  <div className="admin-layout">
    <AppBar
      title="<%= projectName %>"
    />
    <div className="main">
      { children }
    </div>
  </div>
);

AdminLayout.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.node,
    PropTypes.arrayOf(PropTypes.node),
  ]).isRequired,
};

export default AdminLayout;
