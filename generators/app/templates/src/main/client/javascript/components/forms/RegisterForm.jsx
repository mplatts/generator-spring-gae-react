import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { Button } from 'material-ui';
import PropTypes from 'prop-types';
import { required } from './validators';

const RegisterForm = ({ handleSubmit, submitting }) => (
  <form onSubmit={handleSubmit} noValidate>
    <Field
      name="name"
      margin="normal"
      component={TextField}
      placeholder="Your full name"
      label="Full name"
      validate={required('Your full name is required')}
      fullWidth
    />
    <Field
      name="password"
      margin="normal"
      placeholder="Choose your password"
      label="Choose Password"
      component={TextField}
      type="password"
      validate={required('Password is required')}
      fullWidth
    />

    <div className="actions">
      <Button
        variant="raised"
        type="submit"
        disabled={submitting}
        color="primary"
        fullWidth
      >
        {submitting ? 'Completing setup...' : 'Complete setup'}
      </Button>
    </div>
  </form>
);

RegisterForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired,
  submitting: PropTypes.bool.isRequired,
};

export default reduxForm({ form: 'register' })(RegisterForm);
