/* eslint-disable react/no-array-index-key */
import { withStyles } from 'material-ui';
import PropTypes from 'prop-types';
import React from 'react';
import * as model from '../../model';

const styles = ({ palette: { error } }) => ({
  formError: {
    color: error.main,
  },
});

const FormError = ({ value, classes }) => {
  const errors = (value && value.messages) || (value && [value]);
  console.log('errors array: ', errors);
  return (
    <div>{errors && errors.map((err, index) => <p key={index} className={classes.formError}>{err}</p>)}</div>
  );
};

FormError.propTypes = {
  classes: PropTypes.object.isRequired,
  value: PropTypes.oneOfType([PropTypes.string, model.error]),
};

FormError.defaultProps = {
  value: undefined,
};

export default withStyles(styles)(FormError);
