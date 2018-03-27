import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { bool, func } from 'prop-types';
import { Button } from 'material-ui';
import { email, required } from 'redux-form-validators';
import ChipSelectField from '../common/ChipSelectField';

const roles = [{
  label: 'Admin',
  value: 'admin',
}, {
  label: 'Member',
  value: 'member',
}, {
  label: 'User',
  value: 'user',
}];

const UserProfileForm = ({ handleSubmit, submitting }) => (
  <form onSubmit={handleSubmit}>
    <Field
      name="name"
      component={TextField}
      placeholder="Your full name"
      label="Full name"
      margin="normal"
      validate={required('Your full name is required')}
      fullWidth
    />
    <Field
      name="email"
      component={TextField}
      placeholder="Your email address"
      label="Email"
      type="email"
      margin="normal"
      validate={[
        required('Email address is required'),
        email('Enter a valid email'),
      ]}
      fullWidth
    />
    <Field
      name="roles"
      component={ChipSelectField}
      options={roles}
      label="Roles"
      margin="normal"
      multi
      placeholder="Start typing roles..."
      fullWidth
      removeSelected
      clearable={false}
    />

    <div className="actions">
      <Button
        variant="raised"
        type="submit"
        disabled={submitting}
        color="primary"
      >
        {submitting ? 'Saving...' : 'Save'}
      </Button>
    </div>
  </form>
);

UserProfileForm.propTypes = {
  handleSubmit: func.isRequired,
  submitting: bool.isRequired,
};

export default reduxForm({ form: 'userProfile' })(UserProfileForm);
