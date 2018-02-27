import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router';
import * as PropTypes from 'prop-types';
import { Chip, RaisedButton, Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui';
import SendIcon from 'material-ui/svg-icons/content/send';
import { user } from '../../model';
import theme from '../../theme';
import './ManageUsersPage.less';
import FormDialog from '../../components/forms/FormDialog';
import InviteUserForm from '../../components/forms/InviteUserForm';
import * as fromMainReducer from '../../reducers';
import { inviteUser, fetchUsers, closeInviteUserDialog, openInviteUserDialog } from '../../actions/users';

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
      {users.map(usr => (
        <TableRow key={usr.id}>
          <TableRowColumn>
            <Link to={`/admin/users/${usr.id}`}>{usr.email}</Link>
          </TableRowColumn>
          <TableRowColumn>{usr.name}</TableRowColumn>
          <TableRowColumn>
            <div className="roles">
              {usr.roles.map(role => <Chip key={role} className="role" backgroundColor={theme.palette.primary1Color} labelColor="white">{role}</Chip>)}
            </div>
          </TableRowColumn>
          <TableRowColumn>{`${usr.enabled}`}</TableRowColumn>
        </TableRow>
      ))}
    </TableBody>
  </Table>
);

UserTable.propTypes = {
  users: PropTypes.arrayOf(user).isRequired,
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
      open={props.dialogOpen}
      onCancel={props.closeInviteUserDialog}
      onSubmit={props.inviteUser}
    />
  </div>
);

UsersPage.propTypes = {
  openInviteUserDialog: PropTypes.func.isRequired,
  closeInviteUserDialog: PropTypes.func.isRequired,
  inviteUser: PropTypes.func.isRequired,
  dialogOpen: PropTypes.bool.isRequired,
  users: PropTypes.arrayOf(user).isRequired,

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

const mapStateToProps = state => ({
  users: fromMainReducer.getUsers(state),
  dialogOpen: fromMainReducer.isInviteUserDialogOpen(state),
});

const mapDispatchToProps = {
  inviteUser,
  fetchUsers,
  closeInviteUserDialog,
  openInviteUserDialog,
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageUsersContainer);
