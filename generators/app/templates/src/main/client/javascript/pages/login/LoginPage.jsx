import React from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { func } from 'prop-types';
import { Paper } from 'material-ui';
import LoginForm from '../../components/forms/LoginForm';
import api from '../../services/api';
import './LoginPage.less';

const LoginPage = ({ handleSubmit }) => (
  <div className="login-page">
    <div>
      <h1 className="display-3">Sign in</h1>

      <Paper className="login-panel" zDepth={5} >
        <LoginForm onSubmit={handleSubmit} />
      </Paper>
    </div>
  </div>
);

LoginPage.propTypes = {
  handleSubmit: func.isRequired,
};

const mapDispatchToProps = dispatch => ({
  handleSubmit: (values, next) =>
    api.users.login(values)
      .then(() => dispatch(push(next || '/'))),
});

const mergeProps = (stateProps, dispatchProps, ownProps) => ({
  handleSubmit: values =>
    dispatchProps.handleSubmit(values, ownProps.location.query.next),
});

export default connect(null, mapDispatchToProps, mergeProps)(LoginPage);
