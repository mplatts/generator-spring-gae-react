import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { bool, func } from 'prop-types';
import { RaisedButton } from 'material-ui';
import { MultiselectField } from './fields';
import { email, required } from './validators';

const UserProfileForm = ({ handleSubmit, submitting }) => (
  <form onSubmit={handleSubmit}>
    <Field
      name="name"
      autoComplete="name"
      component={TextField}
      hintText="Your full name"
      floatingLabelText="Full name"
      validate={required('Your full name is required')}
      fullWidth
    />
    <Field
      name="email"
      autoComplete="email"
      component={TextField}
      hintText="Your email address"
      floatingLabelText="Email"
      type="email"
      validate={[
        required('Email address is required'),
        email('Enter a valid email'),
      ]}
      fullWidth
    />
    <Field
      name="roles"
      component={MultiselectField}
      dataSource={['ADMIN', 'USER']}
      format={value => (value === '' ? null : value)}
      floatingLabelText="Roles"
      hintText="Start typing roles..."
      newChipKeyCodes={[]}
      fullWidth
    />

    <div className="actions">
      <RaisedButton
        label={submitting ? 'Saving...' : 'Save'}
        type="submit"
        disabled={submitting}
        primary
      />
    </div>
  </form>
);

UserProfileForm.propTypes = {
  handleSubmit: func.isRequired,
  submitting: bool.isRequired,
};

export default reduxForm({ form: 'userProfile' })(UserProfileForm);
