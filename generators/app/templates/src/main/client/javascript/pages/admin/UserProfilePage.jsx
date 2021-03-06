/* eslint-disable react/no-array-index-key,react/jsx-indent,react/jsx-closing-tag-location */
import React, { Component } from 'react';
import Alert from 'react-s-alert';
import Avatar from 'react-avatar';
import { SubmissionError } from 'redux-form';
import { object } from 'prop-types';
import UserProfileForm from '../../components/forms/UserProfileForm';
import api from '../../services/api';
import LoadingIndicator from '../../components/LoadingIndicator';
import NotFoundPage from '../NotFoundPage';
import './UserProfilePage.less';

class UserProfilePage extends Component {
  static propTypes = {
    params: object.isRequired,
  };

  state = {
    isFetching: true,
    user: null,
  };

  componentDidMount() {
    const { params } = this.props;
    this.fetchUser(params.userId);
  }

  fetchUser(userId) {
    this.setState({ isFetching: true });

    api.users
      .get(userId)
      .then(user => this.setState({ isFetching: false, user }))
      .catch((error) => {
        this.setState({ isFetching: false });

        if (error.type !== 'NotFoundException') {
          Alert.error(error.message);
        }
      });
  }

  handleSubmit = values =>
    api.users
      .save(values)
      .then(() => Alert.success('User updated'))
      .catch((error) => {
        Alert.error(<div>
          { error.messages.map((err, index) => <p key={index}>{err}</p>) }
        </div>);
        throw new SubmissionError({ _error: error });
      });

  render() {
    const { isFetching, user } = this.state;

    if (isFetching) {
      return <LoadingIndicator size={60} />;
    }

    if (!isFetching && !user) {
      return <NotFoundPage />;
    }

    return (
      <div className="user-profile-page">
        <Avatar className="avatar" name={user.name} email={user.email} size={96} round />

        <UserProfileForm initialValues={user} onSubmit={this.handleSubmit} />
      </div>
    );
  }
}

export default UserProfilePage;
