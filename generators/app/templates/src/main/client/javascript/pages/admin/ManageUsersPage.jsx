import React, { Component } from 'react';
import { Link } from 'react-router';
import { arrayOf } from 'prop-types';
import { Chip, RaisedButton, Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui';
import SendIcon from 'material-ui/svg-icons/content/send';
import AppPropTypes from '../../components/AppPropTypes';
import api from '../../services/api';
import theme from '../../theme';
import './ManageUsersPage.less';

const UserTable = ({ users }) => (
  <Table className="user-table" multiSelectable>
    <TableHeader>
      <TableRow>
        <TableHeaderColumn>Email</TableHeaderColumn>
        <TableHeaderColumn>Name</TableHeaderColumn>
        <TableHeaderColumn>Roles</TableHeaderColumn>
        <TableHeaderColumn>Status</TableHeaderColumn>
      </TableRow>
    </TableHeader>
    <TableBody>
      {users.map(user => (
        <TableRow key={user.username}>
          <TableRowColumn>
            <Link to={`/admin/users/${user.username}`}>{user.email}</Link>
          </TableRowColumn>
          <TableRowColumn>{user.name}</TableRowColumn>
          <TableRowColumn>
            <div className="roles">
              {user.roles.map(role => <Chip key={role} className="role" backgroundColor={theme.palette.accent1Color}>{role}</Chip>)}
            </div>
          </TableRowColumn>
          <TableRowColumn>{user.status}</TableRowColumn>
        </TableRow>
      ))}
    </TableBody>
  </Table>
);

UserTable.propTypes = {
  users: arrayOf(AppPropTypes.user).isRequired,
};

class ManageUsersPage extends Component {
  state = { users: [] };

  componentDidMount() {
    api.users.list()
      .then(users => this.setState({ users }));
  }

  render() {
    const { users } = this.state;

    return (
      <div className="manage-users-page">
        <h1 className="display-1">Users</h1>

        <RaisedButton className="invite-user-btn" label="Invite User" icon={<SendIcon />} />

        <UserTable users={users} />
      </div>
    );
  }
}

export default ManageUsersPage;
