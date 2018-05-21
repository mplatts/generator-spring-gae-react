import React from 'react';
import PropTypes from 'prop-types';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import Button from 'material-ui/Button';
import { email, required } from 'redux-form-validators';

const LoginForm = ({ handleSubmit, submitting }) => (
  <form onSubmit={handleSubmit} noValidate>
    <Field
      name="username"
      component={TextField}
      placeholder="Your email address"
      label="Email"
      type="email"
      margin="dense"
      validate={[required({ msg: 'Email address is required' }), email({ msg: 'Enter a valid email' })]}
      fullWidth
    />

    <Field
      name="password"
      placeholder="Your password"
      label="Password"
      component={TextField}
      type="password"
      margin="dense"
      validate={required({ msg: 'Password is required' })}
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

LoginForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired,
  submitting: PropTypes.bool.isRequired,
};

export default reduxForm({ form: 'login' })(LoginForm);
