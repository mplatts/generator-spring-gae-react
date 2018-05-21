import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { SubmissionError } from 'redux-form';
import { func, object } from 'prop-types';
import CenteredPanelLayout from './CenteredPanelLayout';
import LoginForm from '../components/forms/LoginForm';
import * as authActions from '../actions/auth';
import './LoginPage.less';

class LoginPage extends Component {
  static propTypes = {
    location: object.isRequired,
    login: func.isRequired,
    navigateTo: func.isRequired,
  };

  handleSubmit = (values) => {
    const { location, login, navigateTo } = this.props;
    const next = location.query.next || '/';

    return login(values)
      .then(() => navigateTo(next))
      .catch((error) => {
        throw new SubmissionError({ _error: error.message });
      });
  };

  render() {
    return (
      <CenteredPanelLayout title="Sign in">
        <LoginForm onSubmit={this.handleSubmit}/>
      </CenteredPanelLayout>
    );
  }
}


const actions = { ...authActions, navigateTo: push };
export default connect(null, actions)(LoginPage);
