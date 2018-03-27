import React, { Component } from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import Button from 'material-ui/Button';
import PropTypes from 'prop-types';
import { email, required } from './validators';

class LoginForm extends Component {
  static propTypes = {
    handleSubmit: PropTypes.func.isRequired,
    submitting: PropTypes.bool.isRequired,
  };

  render() {
    const { handleSubmit, submitting } = this.props;

    return (
      <form onSubmit={handleSubmit} noValidate>
        <Field
          name="username"
          component={TextField}
          placeholder="Your email address"
          label="Email"
          type="email"
          margin="dense"
          validate={[required('Email address is required'), email('Enter a valid email')]}
          fullWidth
        />


        <Field
          name="password"
          placeholder="Your password"
          label="Password"
          component={TextField}
          type="password"
          margin="dense"
          validate={required('Password is required')}
          fullWidth
        />


        <div className="actions">
          <Button
            variant="raised"
            color="primary"
            type="submit"
            disabled={submitting}
            fullWidth
          >
            {submitting ? 'Signing in...' : 'Sign in'}
          </Button>
        </div>
      </form>
    );
  }
}

export default reduxForm({ form: 'login' })(LoginForm);
