import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router';
import * as PropTypes from 'prop-types';
import { Chip, RaisedButton, Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui';
import SendIcon from 'material-ui/svg-icons/content/send';
import AppPropTypes from '../../components/AppPropTypes';
import theme from '../../theme';
import './ManageUsersPage.less';
import FormDialog from '../../components/forms/FormDialog';
import InviteUserForm from '../../components/forms/InviteUserForm';
import * as fromMainReducer from '../../reducers';
import { inviteUser, fetchUsers, closeInviteUserDialog, openInviteUserDialog } from '../../actions/manageUsers';

const UserTable = ({ users }) => (
  <Table className="user-table" multiSelectable>
    <TableHeader>
      <TableRow>
        <TableHeaderColumn>Email</TableHeaderColumn>
        <TableHeaderColumn>Name</TableHeaderColumn>
        <TableHeaderColumn>Roles</TableHeaderColumn>
        <TableHeaderColumn>Enabled</TableHeaderColumn>
      </TableRow>
    </TableHeader>
    <TableBody>
      {users.map(user => (
        <TableRow key={user.id}>
          <TableRowColumn>
            <Link to={`/admin/users/${user.id}`}>{user.email}</Link>
          </TableRowColumn>
          <TableRowColumn>{user.name}</TableRowColumn>
          <TableRowColumn>
            <div className="roles">
              {user.roles.map(role => <Chip key={role} className="role" backgroundColor={theme.palette.primary1Color} labelColor="white">{role}</Chip>)}
            </div>
          </TableRowColumn>
          <TableRowColumn>{`${user.enabled}`}</TableRowColumn>
        </TableRow>
      ))}
    </TableBody>
  </Table>
);

UserTable.propTypes = {
  users: PropTypes.arrayOf(AppPropTypes.user).isRequired,
};

const UsersPage = props => (
  <div className="manage-users-page">
    <h1 className="display-1">Manage users</h1>

    <RaisedButton
      className="invite-user-btn"
      label="Invite User"
      icon={<SendIcon />}
      onClick={props.openInviteUserDialog}
    />

    <UserTable users={props.users} />

    <FormDialog
      title="Invite user"
      submitButtonText="Invite"
      formComponent={InviteUserForm}
      formName="inviteUser"
      open={props.inviteUserDialogOpen}
      onCancel={props.closeInviteUserDialog}
      onSubmit={props.inviteUser}
    />
  </div>
);

UsersPage.propTypes = {
  openInviteUserDialog: PropTypes.func.isRequired,
  closeInviteUserDialog: PropTypes.func.isRequired,
  inviteUser: PropTypes.func.isRequired,
  inviteUserDialogOpen: PropTypes.bool.isRequired,
  users: PropTypes.arrayOf(AppPropTypes.user).isRequired,

};

class ManageUsersContainer extends Component {
  componentDidMount() {
    this.props.fetchUsers();
  }

  render() {
    return UsersPage(this.props);
  }
}

ManageUsersContainer.propTypes = {
  fetchUsers: PropTypes.func.isRequired,
};

const stateToProps = state => fromMainReducer.getUsers(state);

const mapDispatchToProps = {
  inviteUser,
  fetchUsers,
  closeInviteUserDialog,
  openInviteUserDialog,
};

export default connect(stateToProps, mapDispatchToProps)(ManageUsersContainer);
