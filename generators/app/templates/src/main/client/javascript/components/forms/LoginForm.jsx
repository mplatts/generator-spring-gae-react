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

  state = { showPassword: false };

  render() {
    const { handleSubmit, submitting } = this.props;
    const { showPassword } = this.state;

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

        {showPassword && (
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
        )}

        <div className="actions">
          {showPassword ? (
            <Button
              variant="raised"
              color="primary"
              type="submit"
              disabled={submitting}
              fullWidth
            >
              {submitting ? 'Signing in...' : 'Sign in'}
            </Button>
          ) : (
            <div className="inline-btn-group">
              <Button
                variant="raised"
                color="primary"
                type="button"
                onClick={() => this.setState({ showPassword: true })}
              >
                Type password
              </Button>
              <Button
                variant="raised"
                type="submit"
                disabled={submitting}
              >
                {submitting ? 'Sending...' : 'Send magic link'}
              </Button>
            </div>
          )}
        </div>
      </form>
    );
  }
}

export default reduxForm({ form: 'login' })(LoginForm);
