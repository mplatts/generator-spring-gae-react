/* eslint-disable jsx-a11y/label-has-for */
import * as React from 'react';
import Select from 'react-select';
import * as PropTypes from 'prop-types';
import './MaterialSelect.less';

const MaterialSelect = props => (
  <div className="MaterialSelector">
    <Select
      placeholder=""
      {...props}
    />
    <div className={`bar ${props.errorText && 'errorState'}`}/>
    <label className={props.errorText && 'errorState'}>{props.labelText}</label>
    <div className="errorText">{props.errorText}</div>
  </div>
);

MaterialSelect.propTypes = {
  ...MaterialSelect.propTypes,
  errorText: PropTypes.string,
  labelText: PropTypes.string,
};

MaterialSelect.defaultProps = {
  errorText: undefined,
  labelText: undefined,
};

export default MaterialSelect;
