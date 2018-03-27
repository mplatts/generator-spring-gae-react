import React, { Component } from 'react';
import Alert from 'react-s-alert';
import { Link } from 'react-router';
import { SubmissionError } from 'redux-form';
import { arrayOf } from 'prop-types';
import { Button, Chip, Table, TableBody, TableCell, TableHead, TableRow } from 'material-ui';
import SendIcon from 'material-ui-icons/Send';
import AppPropTypes from '../../components/AppPropTypes';
import api from '../../services/api';
import './ManageUsersPage.less';
import FormDialog from '../../components/forms/FormDialog';
import InviteUserForm from '../../components/forms/InviteUserForm';

const UserTable = ({ users }) => (
  <Table className="user-table">
    <TableHead>
      <TableRow>
        <TableCell>Email</TableCell>
        <TableCell>Name</TableCell>
        <TableCell>Roles</TableCell>
        <TableCell>Status</TableCell>
      </TableRow>
    </TableHead>
    <TableBody>
      {users.map(user => (
        <TableRow key={user.id}>
          <TableCell>
            <Link to={`/admin/users/${user.id}`}>{user.email}</Link>
          </TableCell>
          <TableCell>{user.name}</TableCell>
          <TableCell>
            <div className="roles">
              {user.roles.map(role => <Chip key={role} className="role" label={role} />)}
            </div>
          </TableCell>
          <TableCell>{user.status}</TableCell>
        </TableRow>
      ))}
    </TableBody>
  </Table>
);

UserTable.propTypes = {
  users: arrayOf(AppPropTypes.user).isRequired,
};

class ManageUsersPage extends Component {
  state = { inviteUserDialogOpen: false, users: [] };

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers() {
    api.users.list()
      .then(users => this.setState({ users }))
      .catch(error => Alert.error(`Error fetching users: ${error.message}`));
  }

  handleInviteUser = values =>
    api.users.invite(values)
      .then(() => {
        Alert.success('User invite sent!');
        this.closeInviteUserDialog();
      })
      .catch((error) => {
        throw new SubmissionError({ _error: error.message });
      });

  openInviteUserDialog = () => {
    this.setState({ inviteUserDialogOpen: true });
  };

  closeInviteUserDialog = () => {
    this.setState({ inviteUserDialogOpen: false });
  };

  render() {
    const { inviteUserDialogOpen, users } = this.state;

    return (
      <div className="manage-users-page">
        <h1 className="display-1">Manage users</h1>

        <Button
          variant="raised"
          className="invite-user-btn"
          onClick={this.openInviteUserDialog}
        >
          Invite User
          <SendIcon className="invite-user-btn-icon"/>
        </Button>

        <UserTable users={users} />`

        <FormDialog
          title="Invite user"
          submitButtonText="Invite"
          formComponent={InviteUserForm}
          formName="inviteUser"
          open={inviteUserDialogOpen}
          onCancel={this.closeInviteUserDialog}
          onSubmit={this.handleInviteUser}
        />
      </div>
    );
  }
}

export default ManageUsersPage;
